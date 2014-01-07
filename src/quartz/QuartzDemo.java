package quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;  
import static org.quartz.JobBuilder.newJob;  
import static org.quartz.TriggerBuilder.newTrigger;



import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzDemo {
	public static void main(String[] args) throws SchedulerException {

		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();

		JobDetail job = newJob(MyJob.class).withIdentity("job1", Scheduler.DEFAULT_GROUP).build();

		try {
			Trigger trigger = newTrigger().withIdentity("trigger1", Scheduler.DEFAULT_GROUP).withSchedule(cronSchedule("* * * * * ?"))
					.build();
			scheduler.scheduleJob(job, trigger);
		}  catch (SchedulerException e) {
			e.printStackTrace();
		}

		scheduler.start();
	}
}

