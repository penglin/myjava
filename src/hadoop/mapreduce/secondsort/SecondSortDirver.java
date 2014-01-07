package hadoop.mapreduce.secondsort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SecondSortDirver extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job();
		job.setJobName("secondsort");
		job.setJarByClass(SecondSortDirver.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapOutputKeyClass(TextPair.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setGroupingComparatorClass(SecondSort.SecondSortGroupingComparator.class);
		job.setPartitionerClass(SecondSort.SecondSortPartitioner.class);
		job.setSortComparatorClass(SecondSort.SecondSortSortComparator.class);
		
		job.setMapperClass(SecondSortMapper.class);
		job.setReducerClass(SecondSortReducer.class);
		
		FileInputFormat.setInputPaths(job, "/penglin/secondsort/input");
		Path out = new Path("/penglin/secondsort/output");
		FileSystem fs = FileSystem.get(getConf());
		if(fs.exists(out)){
			fs.delete(out, true);
		}
		FileOutputFormat.setOutputPath(job, out);
		
		boolean success = job.waitForCompletion(true);
		
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new SecondSortDirver(), args);
	}
}
