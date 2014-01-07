package hadoop.mapreduce.secondsort;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SecondSortMapper extends Mapper<LongWritable, Text, TextPair, Text> {
	public static final String SPLIT_CHAR = "\t";
	protected TextPair tp = new TextPair();
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if(value.toString().trim().isEmpty())
			return ;
		String[] splits = value.toString().split(SPLIT_CHAR);
		String userId = splits[0];
		String userIp = splits[1];
		tp.set(userId, userIp);
		context.write(tp, value);
	}

}
