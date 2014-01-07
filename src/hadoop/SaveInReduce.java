package hadoop;

import hbase.util.HBaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SaveInReduce extends Configured implements Tool{
	private static Log log =  LogFactory.getLog(SaveInReduce.class);
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		try {
			log.info("v0.1");
			log.info("mapReduce start");
			int res = ToolRunner.run(new Configuration(), new SaveInReduce(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 Job job = new Job(getConf());
		 job.getConfiguration().setLong("dfs.block.size", 256000000L);
		 job.getConfiguration().setLong("dfs.datanode.max.xcievers", 2047L);
	     job.setJarByClass(SaveInReduce.class);
	     job.setJobName("Save_In_Reduce");
	
	     job.setOutputKeyClass(IntWritable.class);
	     job.setOutputValueClass(IntWritable.class);
	     job.setMapOutputKeyClass(IntWritable.class);
         job.setMapOutputValueClass(Text.class);
	
	     job.setMapperClass(ReadDataMapper.class);
	     job.setReducerClass(SaveToHBaseReducer.class);
	     job.setNumReduceTasks(18);
	     // Note that these are the default.
	     job.setInputFormatClass(TextInputFormat.class);
	     job.setOutputFormatClass(TextOutputFormat.class);
	
//	     FileInputFormat.setInputPaths(job, new Path("/user/hive/warehouse/penglin_test.db/visitor_id"));
//	     FileOutputFormat.setOutputPath(job, new Path("hdfs://a6-5:9000/penglin/visitorid2"));
	     
	     FileInputFormat.setInputPaths(job, new Path("/penglin/visitorId"));
	     FileOutputFormat.setOutputPath(job, new Path("/penglin/visitorid2"));
	
	     boolean success = job.waitForCompletion(true);
	     return success ? 0 : 1;
	}
	
	
	public static class ReadDataMapper extends Mapper<Object, Text, IntWritable, Text> {
		private static Random r = new Random();
		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			try {
//				log.info("info:"+value.toString());
				context.write(new IntWritable(getRandom()),value);
			} catch (Exception e) {
				log.error("Mapper Exception", e);
				if(e instanceof IOException)
					throw new IOException(e);
				else if(e instanceof InterruptedException)
					throw (InterruptedException)e;
					
			}
		}

		private int getRandom(){
			int num = r.nextInt(18);
			while(num<0){
				num = r.nextInt(18);
			}
			return num;
		}
	}

	public static class SaveToHBaseReducer extends Reducer<IntWritable, Text, IntWritable, IntWritable>{
		private static HTable table;
		private static Configuration conf;
		private static String tablename;
		private static List<Put> puts = new ArrayList<Put>();
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			log.info("execute setup....");
			super.setup(context);
			try {
//				conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
				conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
				tablename = "Test_Visitor";
				table = new HTable(conf,tablename);
				table.setAutoFlush(false);
				table.setWriteBufferSize(5 * 1024 * 1024);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			log.info("execute cleanup....");
			super.cleanup(context);
			if(table!=null){
				table.flushCommits();
				table.close();
			}
		}

		@Override
		protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int i = 0;
			try {
				Iterator<Text> it = values.iterator();
				while(it.hasNext()){
					String[] split = it.next().toString().split(",");
					String rowKey = split[0];
					long currTime = System.currentTimeMillis();
					Put p = new Put(Bytes.toBytes(rowKey));
					int j = 0;
					/*p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));*/
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_"+j++), Bytes.toBytes(split[1]));
					puts.add(p);
					
					if(puts.size()>300){
						currTime = System.currentTimeMillis();
						HBaseUtil.putData(conf, table, puts);
						log.info("Put Data To HTable Cost Time:"+(System.currentTimeMillis() - currTime));
						puts.clear();
					}
					i++;
				}
				long currTime = System.currentTimeMillis();
				HBaseUtil.putData(conf, table, puts);
				log.info("Put Data To HTable Cost Time:"+(System.currentTimeMillis() - currTime));
				puts.clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Reduce Exception", e);
				throw new InterruptedException(e.getMessage());
			}
			String num = key.toString();
			context.write(new IntWritable(Integer.parseInt(num)), new IntWritable(i));
		}
		
	}
}
