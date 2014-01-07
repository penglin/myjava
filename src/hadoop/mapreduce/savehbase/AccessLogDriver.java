package hadoop.mapreduce.savehbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;

public class AccessLogDriver extends Configured implements Tool
{
	public int run(String[] arg0) throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14");
		conf.set("hadoop.job.ugi", "hdfs,hdfs");
		conf.set("fs.default.name", "hdfs://A6-5:9000/");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
		conf.set("mapred.child.tmp", "/home/hdfs/mapred/temp");
		conf.set("mapred.system.dir", "/mapred/system");
		
		Job job = new Job(conf, "Sample MR Application");
		job.setJarByClass(AccessLogSampleApplication.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(AccessLogMapper.class);
		job.setReducerClass(AccessLogReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path("hdfs:/user/hive/warehouse/panel.db/tag_log_month_summary/"));
		TableMapReduceUtil.initTableReducerJob("tab1", AccessLogReducer.class, job);
		job.waitForCompletion(true);
		return 0;

	}
}
