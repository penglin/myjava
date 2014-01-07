package hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class MySimpleTask //extends Configured implements Tool
{
	public static void main(String[] args) throws Exception
	{
		JobConf conf = new JobConf(MySimpleTask.class);
		conf.set("fs.default.name", "hdfs://A6-5:9000/");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
		conf.setJobName("SimpleTask_penglin");
		conf.setJarByClass(MySimpleTask.class);
		//conf.set("user.name", "hdfs");
		conf.set("hadoop.job.ugi", "hdfs,hdfs");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(MySimpleMap.class);
		conf.setCombinerClass(MySimpleReduce.class);
		conf.setReducerClass(MySimpleReduce.class);

		FileInputFormat.addInputPath(conf, new Path("/user/hive/warehouse/penglin_test.db/visitor_id"));
		FileOutputFormat.setOutputPath(conf, new Path("hdfs://a6-5:9000/penglin/visitorid"));

		JobClient.runJob(conf);
	}


}
