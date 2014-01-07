package hadoop;

import hbase.util.HBaseUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MyHBaseMapReduceRun extends Configured implements Tool {
	private static final Log log = LogFactory.getLog(MyHBaseMapReduceRun.class);

	public static void main(String[] args) {
		try {
			log.info("v0.1");
			log.info("mapReduce start");
			int res = ToolRunner.run(new Configuration(), new MyHBaseMapReduceRun(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration config = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
		Job job = new Job(config, "ReadHBaseRecoder");
		job.setJarByClass(MyHBaseMapReduceRun.class); // class that contains
														// mapper and reducer
		job.getConfiguration().setLong("mapred.min.split.size", 512000000L);

		Scan scan = new Scan();
		scan.setCaching(50); // 1 is the default in Scan, which will be bad
								// for MapReduce jobs
		scan.setCacheBlocks(false); // don't set to true for MR jobs
		// set other scan attrs
		scan.addFamily("visitor".getBytes());

		TableMapReduceUtil.initTableMapperJob("Test_Visitor", // input table
				scan, // Scan instance to control CF and attribute selection
				MyHBaseMap.class, // mapper class
				ImmutableBytesWritable.class, // mapper output key
				Result.class, // mapper output value
				job);
		/*
		 * TableMapReduceUtil.initTableReducerJob( targetTable, // output table
		 * MyTableReducer.class, // reducer class job);
		 */
		job.setReducerClass(MyHBaseOutReducer.class); // reducer class
		job.setNumReduceTasks(18); // at least one, adjust as required

		job.setOutputKeyClass(ImmutableBytesWritable.class);
	    job.setOutputValueClass(Result.class);

		FileOutputFormat.setOutputPath(job, new Path("hdfs://RS143:9000/penglin/visitorid3"));
		boolean success = job.waitForCompletion(true);
		/*
		 * if (!success) { throw new IOException("error with job!"); }
		 */
		return success ? 0 : 1;
	}

	public static class MyHBaseMap extends TableMapper<ImmutableBytesWritable, Result> {
		// private HTable htable ;
		private IntWritable ONE = new IntWritable(1);
		private static Random r = new Random();
		private static enum Counters { INPUT_WORDS }
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			// if(htable!=null)
			// htable.close();
			log.info("Mapper end.");
		}

		@Override
		protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
			long currTime = System.currentTimeMillis();
			/*NavigableMap<byte[], byte[]> maps = value.getFamilyMap("visitor".getBytes());
			Set<Entry<byte[], byte[]>> sets = maps.entrySet();
			Iterator<Entry<byte[], byte[]>> it = sets.iterator();
			while(it.hasNext()){
				Entry<byte[], byte[]> entry = it.next();
//				System.out.println("colunm Key:"+new String(entry.getKey())+"\tcolumn Value:"+new String(entry.getValue()));
			}*/
//			log.info("Get RowKey["+key.get()+"] Cost Time:"+(System.currentTimeMillis()-currTime));
//			log.info("-------------------------------------------");
			context.getCounter(Counters.INPUT_WORDS).increment(1L);
			
			context.write(key, value);
		}

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			// htable = new HTable("Test_Visitor");
			log.info("Mapper starting...");
			super.setup(context);
		}

		private int getRandom(){
			int num = r.nextInt(20);
			while(num<0){
				num = r.nextInt(20);
			}
			return num;
		}
	}

	public static class MyHBaseOutReducer extends Reducer<ImmutableBytesWritable, Result, Text, Text> {
		private static HTable table;
		private static Configuration conf;
		private static String tablename;
		@Override
		protected void cleanup(Context context) throws IOException, InterruptedException {
			log.info("execute setup....");
			super.setup(context);
			try {
				conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
				tablename = "Test_Visitor";
				table = new HTable(conf,tablename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			log.info("execute cleanup....");
			super.cleanup(context);
			if(table!=null)
				table.close();
		}

		public void reduce(ImmutableBytesWritable key, Iterable<Result> values, Context context) throws IOException, InterruptedException {
			int i = 0;
			for (Result value : values) {
				NavigableMap<byte[], byte[]> maps = value.getFamilyMap("visitor".getBytes());
				Set<Entry<byte[], byte[]>> sets = maps.entrySet();
				Iterator<Entry<byte[], byte[]>> it = sets.iterator();
				StringBuffer sb = new StringBuffer();
				while(it.hasNext()){
					Entry<byte[], byte[]> entry = it.next();
					sb.append(new String(entry.getValue())+"\t");
				}
				context.write(new Text(key.get()), new Text(sb.toString().getBytes()));
			}
		}
		
		private void getRecord(IntWritable key, Iterable<Text> values) throws IOException{
			for (Text val : values) {
				String rowKey = new String(val.getBytes());
				Get get = new Get(val.getBytes());
				long currTime = System.currentTimeMillis();
				Result result = table.get(get);
				long time1 = System.currentTimeMillis() - currTime;
				if(result==null)
					continue;
				currTime = System.currentTimeMillis();
				NavigableMap<byte[], byte[]> maps = result.getFamilyMap("visitor".getBytes());
				Set<Entry<byte[], byte[]>> sets = maps.entrySet();
				Iterator<Entry<byte[], byte[]>> it = sets.iterator();
				while(it.hasNext()){
					Entry<byte[], byte[]> entry = it.next();
					System.out.println("colunm Key:"+new String(entry.getKey())+"\tcolumn Value:"+new String(entry.getValue()));
				}
				long time2 = System.currentTimeMillis() - currTime;
				log.info("Get RowKey["+rowKey+"] Cost Time:"+(time1)+","+time2);
			}
		}
	}
}
