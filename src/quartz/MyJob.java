package quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {

	Logger logger = Logger.getLogger(MyJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.error(arg0.getJobDetail().getKey() + "调用计划任务在：" + new Date());
	}

}
