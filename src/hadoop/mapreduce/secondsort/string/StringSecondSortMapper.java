package hadoop.mapreduce.secondsort.string;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class StringSecondSortMapper extends Mapper<LongWritable, Text, Text, Text> {
	public static final String SPLIT_CHAR = "\t";
	protected Text outputKey = new Text();
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if(value.toString().trim().isEmpty())
			return ;
		String[] splits = value.toString().split(SPLIT_CHAR);
//		String userId = splits[0];
//		String userIp = splits[1];
		outputKey.set(value.toString());
		context.write(outputKey, value);
	}

}


class StringSecondReducer extends Reducer<Text, Text, Text, IntWritable>{
	protected IntWritable iw = new IntWritable();
	protected Text t = new Text();
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		int i=0;
		for(Text t : values){
			i++;
			System.out.println(t.toString());
		}
		t.set(key.toString());
		iw.set(i);
		context.write(t, iw);
	}
	
}
