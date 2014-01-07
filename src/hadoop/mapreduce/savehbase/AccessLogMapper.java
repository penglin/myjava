package hadoop.mapreduce.savehbase;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AccessLogMapper extends Mapper<Object, Text, Text, Text> {
    public void map(Object key, Text value, Context context)
            throws IOException {
    	String temp[]=value.toString().split(" ");
    	String k=temp[0];
    	String v=temp[1];
    	System.out.println("mapper:  key "+k+ "value "+v);
	try {
        	context.write(new Text(k ), new Text(v));
	} catch (InterruptedException e) {
              
        }
}
}