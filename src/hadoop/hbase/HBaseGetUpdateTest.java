package hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import hbase.util.HBaseUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
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

public class HBaseGetUpdateTest  extends Configured implements Tool{
	private static Log log =  LogFactory.getLog(HBaseGetUpdateTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log.info("v0.1");
			log.info("mapReduce start");
			int res = ToolRunner.run(new Configuration(), new HBaseGetUpdateTest(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	@Override
	public int run(String[] paramArrayOfString) throws Exception {
		Job job = new Job(getConf());
	     job.setJarByClass(HBaseGetUpdateTest.class);
	     job.setJobName("HBaseGetUpdate");
	
	     job.setOutputKeyClass(IntWritable.class);
	     job.setOutputValueClass(IntWritable.class);
	     job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
	
	     job.setMapperClass(QueryMapper.class);
	     job.setReducerClass(UpdateReducer.class);
	     job.setNumReduceTasks(18);
	     // Note that these are the default.
	     job.setInputFormatClass(TextInputFormat.class);
	     job.setOutputFormatClass(TextOutputFormat.class);
	
//	     FileInputFormat.setInputPaths(job, new Path("/user/hive/warehouse/penglin_test.db/visitor_id"));
//	     FileOutputFormat.setOutputPath(job, new Path("hdfs://a6-5:9000/penglin/visitorid2"));
	     
	     FileInputFormat.setInputPaths(job, new Path("/user/hive/warehouse/penglin_test.db/visitor_id_360"));
	     FileOutputFormat.setOutputPath(job, new Path("hdfs://RS143:9000/penglin/visitorid4"));
	
	     boolean success = job.waitForCompletion(true);
	     return success ? 0 : 1;
	}
	
	public static class QueryMapper extends Mapper<Object, Text, IntWritable, Text> {
		private static Random r = new Random();
		private static HTable table;
		private static String tablename;
		private static Configuration conf;
		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			try {
				if(value==null)
					return ;
				String tmp = value.toString().trim();
				if(tmp.length()==0)
					return;
				tmp = (tmp.split(",")[0]).trim();
//				System.out.println(tmp);
//				query(tmp);
//				update(tmp);
				context.write(new IntWritable(getRandom()),new Text(tmp));
			} catch (Exception e) {
				log.error("Mapper Exception", e);
				if(e instanceof InterruptedException)
					throw (InterruptedException)e;
				else 
					throw new IOException(e.getMessage());
			}
		}
		
		private void query(String rowkey) throws IOException{
			Get get = new Get(rowkey.getBytes());
			long currTime = System.currentTimeMillis();
			Result result = table.get(get);
			if(result==null)
				return;
			long time1 = System.currentTimeMillis() - currTime;
			currTime = System.currentTimeMillis();
			NavigableMap<byte[], byte[]> maps = result.getFamilyMap("visitor".getBytes());
			if(maps==null)
				return;
			Set<Entry<byte[], byte[]>> sets = maps.entrySet();
			Iterator<Entry<byte[], byte[]>> it = sets.iterator();
			while(it.hasNext()){
				Entry<byte[], byte[]> entry = it.next();
//				System.out.println("colunm Key:"+new String(entry.getKey())+"\tcolumn Value:"+new String(entry.getValue()));
			}
			long time2 = System.currentTimeMillis() - currTime;
//			log.info("Query RowKey["+rowkey+"] Cost Time:"+(time1)+",Iterator Cost Time:"+time2);
		}
		
		private void update(String rowKey) throws IOException{
			long currTime = System.currentTimeMillis();
			Put p = new Put(Bytes.toBytes(rowKey));
			p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_0"), Bytes.toBytes("我是老大我怕谁"));
			table.put(p);
			table.flushCommits();
//			log.info("Update RowKey["+rowKey+"] Cost Time:"+(System.currentTimeMillis()-currTime));
		}
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			log.info("execute setup....");
			super.setup(context);
			try {
				conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
				tablename = "Test_Visitor2";
				table = new HTable(conf,tablename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			log.info("execute cleanup....");
			super.cleanup(context);
			if(table!=null)
				table.close();
		}

		private int getRandom(){
			int num = r.nextInt(18);
			while(num<0){
				num = r.nextInt(18);
			}
			return num;
		}
	}

	public static class UpdateReducer extends Reducer<IntWritable, Text, IntWritable, IntWritable>{
		private static HTable table;
		private static String tablename;
		private static Configuration conf;
		private static List<Put> puts = new ArrayList<Put>();
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			log.info("execute setup....");
			super.setup(context);
			try {
				tablename = "Test_Visitor2";
				conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
				table = new HTable(conf,tablename);
				table.setScannerCaching(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			log.info("execute cleanup....");
			super.cleanup(context);
			if(table!=null)
				table.close();
		}

		@Override
		protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			int i = 0;
			try {
				Iterator<Text> it = values.iterator();
				while(it.hasNext()){
					String rowKey = it.next().toString();
					long currTime = System.currentTimeMillis();
					Put p = update(rowKey);
					puts.add(p);
					if(puts.size()>300){
						currTime = System.currentTimeMillis();
						HBaseUtil.putData(conf, table, puts);
						log.info("Update To HTable Cost Time:"+(System.currentTimeMillis() - currTime));
						puts.clear();
					}
//					update(rowKey);
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
		
		private Put update(String rowKey) throws IOException{
			Put p = new Put(Bytes.toBytes(rowKey));
//			log.info("Update RowKey:"+rowKey);
			p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_1"), Bytes.toBytes("123456789fds87654321"));
//			p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_2"), Bytes.toBytes("12345678987654fdfd321"));
//			p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_3"), Bytes.toBytes("12345678987fd654321"));
//			p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_4"), Bytes.toBytes("12345678sds987654321"));
//			p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_5"), Bytes.toBytes("1234567fds8987654321"));
			return p;
		}
		
		private void query(String rowkey) throws IOException{
			Get get = new Get(rowkey.getBytes());
			long currTime = System.currentTimeMillis();
			Result result = table.get(get);
			if(result==null)
				return;
			long time1 = System.currentTimeMillis() - currTime;
			currTime = System.currentTimeMillis();
			NavigableMap<byte[], byte[]> maps = result.getFamilyMap("visitor".getBytes());
			if(maps==null)
				return;
			Set<Entry<byte[], byte[]>> sets = maps.entrySet();
			Iterator<Entry<byte[], byte[]>> it = sets.iterator();
			while(it.hasNext()){
				Entry<byte[], byte[]> entry = it.next();
//				System.out.println("colunm Key:"+new String(entry.getKey())+"\tcolumn Value:"+new String(entry.getValue()));
			}
			long time2 = System.currentTimeMillis() - currTime;
//			log.info("Query RowKey["+rowkey+"] Cost Time:"+(time1)+",Iterator Cost Time:"+time2);
		}
	}

}
