package hdfs;

import org.apache.log4j.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class HDFSList {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HDFSList.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = MyConf.getConfiguration();
		FileSystem fs = FileSystem.get(conf);
		FileStatus[] fileStatus = fs.listStatus(new Path("/precise/datelog/20130405"));
		Path[] paths = FileUtil.stat2Paths(fileStatus);
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - Path[] paths=" + paths.length); //$NON-NLS-1$
		}
		
	}

}
