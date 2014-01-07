package hadoop.writable;

import java.io.IOException;
import java.util.Iterator;

import hadoop.util.MyWritable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
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

public class MyWritableMapRuduce extends Configured implements Tool{
	private static Log log =  LogFactory.getLog(MyWritableMapRuduce.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			log.info("mapReduce start");
			int res = ToolRunner.run(new Configuration(), new MyWritableMapRuduce(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Job job = new Job(getConf());
		 job.getConfiguration().setLong("dfs.block.size", 256000000L);
		 job.getConfiguration().setLong("dfs.datanode.max.xcievers", 2047L);
	     job.setJarByClass(MyWritableMapRuduce.class);
	     job.setJobName("MyWritableMapReduceRun_Penglin");
	
	     job.setOutputKeyClass(Text.class);
	     job.setOutputValueClass(MyWritable.class);
	
	     job.setMapperClass(MyWritableMapper.class);
	     job.setCombinerClass(MyWritableReducer.class);
	     job.setReducerClass(MyWritableReducer.class);
	     job.setNumReduceTasks(30);
	     job.setInputFormatClass(TextInputFormat.class);
	     job.setOutputFormatClass(TextOutputFormat.class);
	
	     FileInputFormat.setInputPaths(job, new Path("/user/hive/warehouse/penglin_test.db/visitor_id"));
	     FileOutputFormat.setOutputPath(job, new Path("hdfs://a6-5:9000/penglin/visitorid3"));
	
	     boolean success = job.waitForCompletion(true);
	     return success ? 0 : 1;
	}
	
	public static class MyWritableMapper  extends Mapper<LongWritable, Text, Text, MyWritable>{
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			String[] split = value.toString().split(",");
			MyWritable myWritable = new MyWritable(split[1],1L);
			context.write(new Text(split[1]), myWritable);
		}
		
	}

	public static class MyWritableReducer extends Reducer<Text, MyWritable, Text, MyWritable>{
		
		@Override
		protected void reduce(Text key, Iterable<MyWritable> values, Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			MyWritable myWritable = new MyWritable(key,new LongWritable(0L));
			Iterator<MyWritable> it = values.iterator();
			while(it.hasNext()){
				MyWritable tmp = it.next();
				myWritable.setPv(new LongWritable(myWritable.getPv().get() + tmp.getPv().get()));
			}
			context.write(key, myWritable);
		}
	}
}
