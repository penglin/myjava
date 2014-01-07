package hadoop.mapreduce.secondsort;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SecondSortReducer extends Reducer<TextPair, Text, Text, IntWritable> {
	protected IntWritable iw = new IntWritable();
	protected Text t = new Text();
	@Override
	protected void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
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
