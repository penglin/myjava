package hadoop.writable;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

public class UserPartioner<K, V> implements Partitioner<K, V> {

	@Override
	public void configure(JobConf arg0) {
		
	}

	@Override
	public int getPartition(K k, V v, int arg2) {
		
		return 0;
	}

}
