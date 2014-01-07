package hdfs;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.util.Bytes;

public class TestAppend {
	public static void main(String[] args) throws Exception {
		FileSystem fs =  FileSystem.get(URI.create("hdfs://a6-5:9000/"), new Configuration());
		Path path = new Path("/penglin/testAppend");
		FSDataOutputStream out = fs.create(path);
		out.write(Bytes.toBytes("da jia hao cai shi zhen de hao "));
		out.flush();
		out.close();
		
		FSDataOutputStream appendOut = fs.append(path);
		appendOut.write(Bytes.toBytes("i am append content"));
		appendOut.flush();
		appendOut.close();
		
	}
}
