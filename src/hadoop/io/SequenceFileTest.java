package hadoop.io;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

public class SequenceFileTest {
	public static final String[] DATA = new String[]{"one,ddddddddddd","two,ffffffffffff","three,kkkkkkkkkkkk","four,jjjjjjjjjjjjjjjjj","five,yyyyyyyyyyyyyyyy","six,uuuuuuuuuuuuuuu"};
	public static final Path output = new Path("/penglin/seq");
	public static final Configuration conf = new Configuration();
	
	@Before
	public void init(){
		conf.set("fs.default.name", "hdfs://A6-5:9000");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
	}
	
	@Test
	public void testWriteSequencyFile() throws IOException{
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(output)){
			fs.delete(output, true);
		}
		SequenceFile.Writer writer = null;
		Text key = new Text();
		Text value = new Text();
		writer = SequenceFile.createWriter(fs, conf, output, key.getClass(), value.getClass());
		for(String d : DATA){
			String[] splits = d.split(",");
			key.set(splits[0]);
			value.set(splits[1]);
			writer.append(key, value);
		}
		writer.close();
	}
	
	@Test
	public void testReadSequenceFile() throws IOException{
		SequenceFile.Reader read = null;
		FileSystem fs = FileSystem.get(conf);
		read = new SequenceFile.Reader(fs, output, conf);
		Writable key = (Writable) ReflectionUtils.newInstance(read.getKeyClass(), conf);
		Writable value = (Writable) ReflectionUtils.newInstance(read.getValueClass(), conf);
		
		long position = read.getPosition();
		while(read.next(key, value)){
			System.out.println(key+","+value);
			read.getPosition();
		}
		read.close();
	}
}
