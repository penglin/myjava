package hadoop;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MySimpleReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
	private static final Log log = LogFactory.getLog(MySimpleReduce.class);
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int i = 0;
		Iterator<IntWritable> it = values.iterator();
		while(it.hasNext()){
			IntWritable temp = it.next();
			i += temp.get();
		}
//		log.info("MySimpleReduce:"+key.toString()+":"+i);
		context.write(key, new IntWritable(i));
	}
	
}
