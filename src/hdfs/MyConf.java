package hdfs;

import org.apache.hadoop.conf.Configuration;

public class MyConf {
	private static Configuration conf;
	private MyConf(){
		
	}
	
	public synchronized static Configuration getConfiguration(){
		if(conf==null){
			conf = new Configuration();
			conf.set("fs.default.name", "hdfs://A6-5:9000");
			conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
			conf.set("hadoop.tmp.dir", "/home/hdfs/tmp");
		}
		return conf;
	}
}
