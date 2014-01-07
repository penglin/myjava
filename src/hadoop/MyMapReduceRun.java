package hadoop;

import hadoop.util.HadoopUtil;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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

public class MyMapReduceRun extends Configured implements Tool {
	private static Log log = LogFactory.getLog(MyMapReduceRun.class);

	private static Random r = new Random(100);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			log.info("v0.5");
			log.info("mapReduce start");
			int res = ToolRunner.run(new Configuration(), new MyMapReduceRun(), args);
			System.exit(res);
		} catch (Exception e) {
			log.error("Run Exception", e);
		}
	}

	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Job job = new Job(getConf());
		Configuration conf = job.getConfiguration();
		conf.setLong("mapred.max.split.size", 64000000L);// ¿ØÖÆmap¸öÊý
		HadoopUtil.addTmpJar("", job.getConfiguration());
		job.setJarByClass(MyMapReduceRun.class);
		job.setJobName("MyMapReduceRun_Penglin");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(MySimpleMap.class);
		job.setCombinerClass(MySimpleReduce.class);
		job.setReducerClass(MySimpleReduce.class);
		// job.setNumReduceTasks(30);
		// Note that these are the default.
		job.setInputFormatClass(AdistCombineFileInputFormat.class);
//		 job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		Path path = new Path("/panel/user_log/source_id=tracking/visit_date=20120101");
		FileSystem fs = FileSystem.get(URI.create("/panel/user_log/source_id=tracking/visit_date=20120101"), conf);
		Set<Path> paths = new HashSet<Path>();
		scanFile(fs, path, paths);
		Path[] pathtmp = new Path[paths.size()];
		FileInputFormat.setInputPaths(job, paths.toArray(pathtmp));
		FileOutputFormat.setOutputPath(job, new Path("/penglin/combine_out"));

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	public static class MySimpleMap extends Mapper<LongWritable, Text, Text, IntWritable> {
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// String[] split = value.toString().split(",");
			try {
				// String rowKey = split[0];

				context.write(new Text(r.nextInt(100) + ""), new IntWritable(1));
				 log.info("Mapper:"+value.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Mapper Exception", e);
			}
		}

	}

	public static class MySimpleReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int i = 0;
			Iterator<IntWritable> it = values.iterator();
			while (it.hasNext()) {
				IntWritable temp = it.next();
				i += temp.get();
			}
			log.info("MySimpleReduce:" + key.toString() + ":" + i);
			context.write(key, new IntWritable(i));
		}

	}

	private static void scanFile(FileSystem fs, Path path, Set<Path> logPaths) throws IOException {

		FileStatus[] listStatus = fs.listStatus(path);

		if (listStatus != null) {
			for (FileStatus fileStatus : listStatus) {
				if (fileStatus.isDir()) {
					logPaths.add(fileStatus.getPath());
				}
			}
		}
	}

}
