package hbase;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;

public class Conf {
	public static Configuration configuration = null;
	
	public static Configuration configuration2 = null;

	public static boolean isTest = true;	
	
	static{
		if (File.separator.equals("\\"))
			Conf.isTest = true;
		else
			Conf.isTest = false;
	}

	public static Configuration getConfiguration() {
		
		if (configuration == null)
			configuration = HBaseConfiguration.create();
		if (!isTest) {
			configuration.set("hbase.master", "127.0.0.1:9000");
			configuration.set("hbase.zookeeper.quorum", "127.0.0.1");
			configuration.setBoolean("mapred.output.compress", false);
			configuration.setInt("io.sort.mb", 100);//
			configuration.set("hbase.zookeeper.property.clientPort", "2181");
			configuration.setLong("hbase.client.write.buffer", 0);
		} else {
			configuration.set("hbase.master", "192.168.6.5:60010");
			configuration.set("hbase.zookeeper.quorum", "192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14");
			configuration.setBoolean("mapred.output.compress", true);
			configuration.setInt("io.sort.mb", 1000);//
			configuration.set("hbase.zookeeper.property.clientPort", "2181");
			configuration.setLong("hbase.client.write.buffer", 1024 * 1024 * 5);
		}

		if (isTest)
			configuration.set("mapred.child.java.opts", "-Xmx400m");
		//		else
		//			configuration.set("mapred.child.java.opts", "-Xmx1500m -verbose:gc -Xloggc:/home/log/hadoop/@taskid@.gc");
		configuration.setLong(HConstants.HBASE_REGIONSERVER_LEASE_PERIOD_KEY, 6000000L);//100min
		configuration.setInt("hbase.client.retries.number", 50);
		configuration.setInt("hbase.client.pause", 30 * 1000);
		configuration.setLong("mapred.local.dir.minspacestart", 200L * 1000L * 1000L * 1000L);
		configuration.setLong("mapred.local.dir.minspacekill", 100L * 1000L * 1000L * 1000L);
		configuration.setInt("mapred.reduce.parallel.copies", 10);
		configuration.setInt("mapreduce.reduce.shuffle.parallelcopies", 10);
		configuration.setInt("hive.exec.parallel.thread.number", 10);
		configuration.setInt("mapred.reduce.copy.backoff", 360 * 2);
		configuration.setInt("hbase.client.scanner.caching", 10000);
		configuration.setInt("mapred.map.max.attempts", 400);
		configuration.setInt("mapred.skip.attempts.to.start.skipping", 10);
		configuration.setInt("tasktracker.http.threads", 10);
		//		configuration.setInt("dfs.balance.bandwidthPerSec", 104857);
		configuration.setInt("mapred.reduce.max.attempts", 400);
		//		configuration.setInt("mapred.tasktracker.reduce.tasks.maximum", 1);
		//		configuration.setInt("hive.exec.reducers.max", 4);
		//		configuration.setInt("mapred.job.reduce.capacity", 6);//一个job同时运行的最大reduce数
		//		configuration.setInt("mapred.reduce.tasks", 6);//一个job同时运行的最大reduce数
		//		configuration.setInt("mapred.jobtracker.maxtasks.per.job", 50);//The maximum number of tasks for a single job.
		configuration.setInt("mapred.task.timeout", 30 * 60 * 1000);// 3*24*60min

		configuration.setFloat("mapred.job.shuffle.merge.percent", 0.5f);
		configuration.setFloat("mapred.job.shuffle.input.buffer.percent", 0.5f);
		configuration.setFloat("io.sort.spill.percent", 0.8f);
		configuration.setFloat("io.sort.record.percent", 0.5f);

		configuration.setBoolean("mapred.compress.map.output", true);
		configuration.setBoolean("mapred.reduce.tasks.speculative.execution", false);
		configuration.setBoolean("mapred.map.tasks.speculative.execution", false);
		configuration.setBoolean("hive.mapred.reduce.tasks.speculative.execution", false);
		configuration.setStrings("mapred.hosts.exclude", "/home/hdfs/exclude");
		return configuration;
	}
	
public static Configuration getConfiguration2() {
		
		if (configuration2 == null)
			configuration2 = HBaseConfiguration.create();
		if (isTest) {
			configuration2.set("hbase.master", "127.0.0.1:9000");
			configuration2.set("hbase.zookeeper.quorum", "127.0.0.1");
			configuration2.setBoolean("mapred.output.compress", false);
			configuration2.setInt("io.sort.mb", 100);//
			configuration2.set("hbase.zookeeper.property.clientPort", "2181");
			configuration2.setLong("hbase.client.write.buffer", 0);
		} else {
			configuration2.set("hbase.master", "10.10.10.119:60010");
			configuration2.set("hbase.zookeeper.quorum", "10.10.10.119");
			configuration2.setBoolean("mapred.output.compress", true);
			configuration2.setBoolean("mapred.compress.map.output", true);
			configuration2.setInt("io.sort.mb", 512);//
			configuration2.set("hbase.zookeeper.property.clientPort", "2181");
			configuration2.setLong("hbase.client.write.buffer", 1024 * 1024 * 5);
		}

		if (isTest)
			configuration2.set("mapred.child.java.opts", "-Xmx400m");
		else
			configuration2.set("mapred.child.java.opts", "-Xmx1024m -Xms1024m");
		//		else
		//			configuration2.set("mapred.child.java.opts", "-Xmx1500m -verbose:gc -Xloggc:/home/log/hadoop/@taskid@.gc");
		configuration2.set("mapred.fairscheduler.pool", "wbanalyzer6");
		configuration2.setLong(HConstants.HBASE_REGIONSERVER_LEASE_PERIOD_KEY, 60 * 100 * 1000L);//100min
		configuration2.setInt("hbase.client.retries.number", 50);
		configuration2.setInt("hbase.rpc.timeout", 60000 * 5);
		configuration2.setInt(HConstants.HBASE_RPC_TIMEOUT_KEY, 60000 * 5);
		configuration2.setInt("hbase.client.rpc.maxattempts", 3);
		
		configuration2.setInt("hbase.client.pause", 30 * 1000);
		configuration2.setLong("mapred.local.dir.minspacestart", 200L * 1000L * 1000L * 1000L);
		configuration2.setLong("mapred.local.dir.minspacekill", 100L * 1000L * 1000L * 1000L);
		configuration2.setInt("mapred.reduce.parallel.copies", 10);
		configuration2.setInt("mapreduce.reduce.shuffle.parallelcopies", 10);
		configuration2.setInt("hive.exec.parallel.thread.number", 10);
		configuration2.setInt("mapred.reduce.copy.backoff", 360 * 2);
		configuration2.setInt("hbase.client.scanner.caching", 10000);
		configuration2.setInt("mapred.map.max.attempts", 400);
		configuration2.setInt("mapred.skip.attempts.to.start.skipping", 10);
		configuration2.setInt("tasktracker.http.threads", 10);
		//		configuration2.setInt("dfs.balance.bandwidthPerSec", 104857);
		configuration2.setInt("mapred.reduce.max.attempts", 400);
		//		configuration2.setInt("mapred.tasktracker.reduce.tasks.maximum", 1);
		//		configuration2.setInt("hive.exec.reducers.max", 4);
		//		configuration2.setInt("mapred.job.reduce.capacity", 6);//一个job同时运行的最大reduce数
		//		configuration2.setInt("mapred.reduce.tasks", 6);//一个job同时运行的最大reduce数
		//		configuration2.setInt("mapred.jobtracker.maxtasks.per.job", 50);//The maximum number of tasks for a single job.
		configuration2.setInt("mapred.task.timeout", 30 * 60 * 1000);// 3*24*60min

		configuration2.setFloat("mapred.job.shuffle.merge.percent", 0.5f);
		configuration2.setFloat("mapred.job.shuffle.input.buffer.percent", 0.5f);
		configuration2.setFloat("io.sort.spill.percent", 0.8f);
		configuration2.setFloat("io.sort.record.percent", 0.5f);
		configuration2.setFloat("mapred.reduce.slowstart.completed.maps", 0.01f);

		configuration2.setBoolean("mapred.reduce.tasks.speculative.execution", false);
		configuration2.setBoolean("mapred.map.tasks.speculative.execution", false);
		configuration2.setBoolean("hive.mapred.reduce.tasks.speculative.execution", false);
		configuration2.setStrings("mapred.hosts.exclude", "/home/hdfs/exclude");
		configuration2.setStrings("mapred.output.compression.type", "BLOCK");
		return configuration2;
	}

	public static void main(String[] args) {
		try {
			FileSystem.get(getConfiguration()).setReplication(new Path("table/Intersect"), (short) 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
