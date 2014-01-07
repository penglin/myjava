package hadoop.mapreduce;

import org.apache.log4j.Logger;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TestMRRun extends Configured implements Tool {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestMRRun.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String loaderPath = "file:/usr/local/adsit/precise/classes/cn/adsit/precise/analysis/mapred/date/";
		System.out.println(loaderPath.substring(0, loaderPath.lastIndexOf("classes")));
		ToolRunner.run(new TestMRRun(), args);
	}

	@Override
	public int run(String[] arg0) throws Exception {
		String loaderPath = this.getClass().getResource("").toString();
		logger.info("run(String[]) - String path=" + loaderPath); //$NON-NLS-1$
		
		loaderPath = loaderPath.substring(0, loaderPath.lastIndexOf("classes"));
		if (logger.isInfoEnabled()) {
			logger.info("run(String[]) - String path=" + loaderPath); //$NON-NLS-1$
		}

		return 0;
	}
	
}
