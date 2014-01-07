package hadoop.client;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.JobTracker;
import org.apache.hadoop.mapred.RunningJob;

public class HadoopClientControl {
	private static Log Log = LogFactory.getLog(HadoopClientControl.class);
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://A6-5:9000");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
		
		JobConf jobConf = new JobConf(conf);
		JobClient client = new JobClient(jobConf);
		JobStatus[] jobStatuses = client.getAllJobs();
		for(JobStatus jobStatus : jobStatuses){
			if(jobStatus.getRunState()!=JobStatus.RUNNING){
				continue;
			}
			long jobStartTime = jobStatus.getStartTime();
			System.out.println("Job Excute Time:"+(System.currentTimeMillis() - jobStartTime));
			JobID jobID = jobStatus.getJobID();
			RunningJob runningJob = client.getJob(jobID);
			runningJob.killJob();
		}
		System.out.println(jobStatuses.length);
		ArrayList<JobID> runningJobs = getRunningJobList(client);
		for(JobID job : runningJobs){
			System.out.println(job.toString());
			RunningJob runningJob = client.getJob(job);
			System.out.println(runningJob.getJobName());
		}
		
		JobTracker jobTrakcer = JobTracker.startTracker(jobConf);
		String hostName = jobTrakcer.getHostname();
		System.out.println(hostName);
	}
	
	/**
     * 根据JobName获取JobID
     *
     * @param jobName_str
     * @return JobID
     */
    public static JobID getJobIDByJobName(JobClient jobClient, JobStatus[] jobStatus, String jobName_str) {
       JobID jobID = null;
       try {
           for (int i = 0; i < jobStatus.length; i++) {
              RunningJob rj = jobClient.getJob(jobStatus[i].getJobID());
              if (rj.getJobName().trim().equals(jobName_str)) {
                  jobID = jobStatus[i].getJobID();
                  break;
              }
           }
       } catch (IOException e) {
    	   Log.error("Exception:", e);
       }
       return jobID;
    }

    /**
     * 根据JobID获取Job状态
     *
     * @param jobClient
     * @param jobStatus
     * @param jobID
     * @return RUNNING = 1,SUCCEEDED = 2,FAILED = 3,PREP = 4,KILLED = 5
     * @throws IOException
     */
    public static String getStatusByJobID(JobClient jobClient, JobStatus[] jobStatus, JobID jobID) throws IOException {
       int status_int = 0;
       jobStatus = jobClient.getAllJobs();
       for (int i = 0; i < jobStatus.length; i++) {
           if (jobStatus[i].getJobID().getId() == jobID.getId()) {
              status_int = jobStatus[i].getRunState();
              break;
           }
       }
 
       String desc_str = "";
       switch (status_int) {
       case 1:
           desc_str = "RUNNING";
           break;
       case 2:
           desc_str = "SUCCEEDED";
           break;
       case 3:
           desc_str = "FAILED";
           break;
       case 4:
           desc_str = "PREP";
           break;
       case 5:
           desc_str = "KILLED";
           break;
       default:
           break;
       }
       return desc_str;
    }
    
    /**
     * 获取正在运行的JobID的列表
     *
     * @param jobClient
     * @return ArrayList<JobID>
     */
    public static ArrayList<JobID> getRunningJobList(JobClient jobClient) {
       ArrayList<JobID> runningJob_list = new ArrayList<JobID>();
       JobStatus[] js;
       try {
           js = jobClient.getAllJobs();
           for (int i = 0; i < js.length; i++) {
              if (js[i].getRunState() == JobStatus.RUNNING || js[i].getRunState() == JobStatus.PREP) {
                  runningJob_list.add(js[i].getJobID());
              }
           }
       } catch (IOException e) {
    	   Log.error("Exception:", e);
       }
       return runningJob_list;
    }
}
