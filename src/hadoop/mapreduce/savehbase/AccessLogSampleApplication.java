package hadoop.mapreduce.savehbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class AccessLogSampleApplication
{
	public static void main(String[] args) throws Exception
	{
		int m_rc = 0;
		m_rc = ToolRunner.run(new Configuration(), new AccessLogDriver(), args);
		System.exit(m_rc);
	}

}
