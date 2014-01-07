package hdfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.util.ReflectionUtils;

public class TestDeCompress {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		FileInputStream in = new FileInputStream("flow-r-00013.snappy");
		Class<?> codeClass = Class.forName("org.apache.hadoop.io.compress.SnappyCodec");
		Configuration conf = new Configuration();
		CompressionCodec compress = (CompressionCodec) ReflectionUtils.newInstance(codeClass, conf);
		CompressionInputStream  compressIn = compress.createInputStream(in);
	}

}
