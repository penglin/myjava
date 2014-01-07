package hadoop.client;

import java.io.IOException;
import java.util.Collection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TaskAttemptID;
import org.apache.hadoop.mapred.TaskCompletionEvent;
import org.apache.hadoop.mapred.TaskReport;

public class TestJobControl {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String jobId = "job_201303111827_2062";
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://A6-5:9000");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
		
		JobConf jobConf = new JobConf(conf);
		JobClient client = new JobClient(jobConf);
		JobID jobid = new JobID("201303111827", 6145);
		RunningJob rj = client.getJob(jobid);
		System.out.println(rj.getJobName());
		System.out.println(rj.isComplete());
		TaskReport[] taskReports = client.getReduceTaskReports(jobid);
		RunningJob runningJob = client.getJob(jobid);
		for(TaskReport taskReport:taskReports){
			TaskAttemptID taskAttemptID = taskReport.getSuccessfulTaskAttempt();
			Collection<TaskAttemptID> attempList = taskReport.getRunningTaskAttempts();
			for(TaskAttemptID attemp : attempList){
				String reduceTaskAttempId = attemp.toString();
				String tmpId = reduceTaskAttempId +",+";
				System.out.println(tmpId);
			}
		}
		
		TaskCompletionEvent[] envents = runningJob.getTaskCompletionEvents(10);
		System.out.println(envents.length);
	}

}
