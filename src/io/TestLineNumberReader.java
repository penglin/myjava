









package io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URI;

public class TestLineNumberReader {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestLineNumberReader.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// /precise/upload/demand/201304091501/192.168.1.35_demand_20130409151618_bc3ffc6c3dcf7bf0013deda705b40b56.log.zip_172.16.1.102
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://A6-5:9000");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
		conf.set("hadoop.tmp.dir", "/home/hdfs/tmp");
		conf.set("ipc.client.connect.max.retries", "10");
		String inputPath = "/precise/upload/demand/201304091501/192.168.1.30_demand_20130409152157_4028819e3dd7b2be013dedac302c0800.log";
		FileSystem fs = FileSystem.get(conf);
		
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(fs.open(new Path(inputPath)),"utf8"));
		String line = null;
		int count = 0;
		while((line=lnr.readLine())!=null){
			if (logger.isInfoEnabled()) {
				logger.info("main(String[]) - String line=" + line); //$NON-NLS-1$
			}
			count++;
		}

		lnr.close();
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - line num="+lnr.getLineNumber()); //$NON-NLS-1$
		}

		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - int count=" + count); //$NON-NLS-1$
		}
	}

}
