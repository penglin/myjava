package snappy;

import hdfs.MyConf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.io.compress.snappy.SnappyDecompressor;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xerial.snappy.SnappyInputStream;

public class TestHadoopSnappy {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestHadoopSnappy.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		SnappyDecompressor sd = new SnappyDecompressor();
		SnappyCodec sc = new SnappyCodec();
		Configuration conf = new Configuration();
		sc.setConf(conf);
		File file = new File("flow-r-00013.snappy");
		FileInputStream in = new FileInputStream(file);
		CompressionInputStream cin = sc.createInputStream(in);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cin),1024000);
		String line = null;
		int count = 0;
		while((line=br.readLine())!=null){
			System.out.println(line);
			count++;
		}
		br.close();

		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - count : =" + count); //$NON-NLS-1$
		}
	}

	
	@Test
	public void testWindowDeCompress() throws IOException{
		Configuration conf = MyConf.getConfiguration();
		FileSystem fs = FileSystem.get(conf);
//		CompressionInputStream cin = null;
//		SnappyCodec sc = new SnappyCodec();
//		cin = sc.createInputStream(fs.open(new Path("/precise/log/201305151501/click-r-00028.snappy")));
		
		SnappyInputStream in = new SnappyInputStream(fs.open(new Path("/precise/log/201305151501/click-r-00028.snappy")));
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(in,"utf8"),10000);
		String line = null;
		while((line=lnr.readLine())!=null){
			//convert line info to sql
			System.out.println(line);
		}
		
		lnr.close();
	}
}
