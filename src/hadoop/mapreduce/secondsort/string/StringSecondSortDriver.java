package hadoop.mapreduce.secondsort.string;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StringSecondSortDriver extends Configured implements Tool {
	public static final String SPLIT_CHAR = "\t";
	@Override
	public int run(String[] arg0) throws Exception {
		Job job = new Job();
		job.setJarByClass(StringSecondSortDriver.class);
		job.setJobName("stringsecondsort");
		
		job.setMapperClass(StringSecondSortMapper.class);
		job.setReducerClass(StringSecondReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		
		job.setPartitionerClass(StringSecondSortDriver.StringPartitioner.class);
		job.setSortComparatorClass(StringSecondSortDriver.StringSortComparator.class);
		job.setGroupingComparatorClass(StringSecondSortDriver.StringGroupingComparator.class);
		
		FileInputFormat.setInputPaths(job, "/penglin/secondsort/input");
		Path out = new Path("/penglin/secondsort/string_output");
		FileSystem fs = FileSystem.get(getConf());
		if(fs.exists(out)){
			fs.delete(out, true);
		}
		FileOutputFormat.setOutputPath(job, out);
		
		boolean success = job.waitForCompletion(true);
		
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new StringSecondSortDriver(), args);
	}
	
	public static class StringPartitioner extends Partitioner<Text, Text>{

		@Override
		public int getPartition(Text key, Text value, int numPartitions) {
			return Math.abs(key.toString().split(SPLIT_CHAR).hashCode())%numPartitions;
		}
		
	}
	
	public static class StringSortComparator extends WritableComparator{

		protected StringSortComparator() {
			super(Text.class,true);
		}

		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			Text t1 = (Text) a;
			Text t2 = (Text) b;
			String[] splits_a = t1.toString().split(SPLIT_CHAR);
			String[] splits_b = t2.toString().split(SPLIT_CHAR);
			
			int compare = splits_a[0].compareTo(splits_b[0]);
			if(compare!=0)
				return compare;
			return splits_a[1].compareTo(splits_b[1]);
		}
	}
	
	public static class StringGroupingComparator extends WritableComparator{

		protected StringGroupingComparator() {
			super(Text.class,true);
		}
		
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			Text t1 = (Text) a;
			Text t2 = (Text) b;
			String[] splits_a = t1.toString().split(SPLIT_CHAR);
			String[] splits_b = t2.toString().split(SPLIT_CHAR);
			
			return splits_a[0].compareTo(splits_b[0]);
		}
	}
}
