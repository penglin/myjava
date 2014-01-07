package hadoop.mapreduce.secondsort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Partitioner;

public class SecondSort {
	public static class SecondSortPartitioner extends Partitioner<TextPair, Text>{

		@Override
		public int getPartition(TextPair key, Text value, int numPartitions) {
			return Math.abs(key.getUserId().hashCode() * 127) % numPartitions; 
		}
		
	}
	
	public static class SecondSortGroupingComparator extends WritableComparator{

		protected SecondSortGroupingComparator() {
			super(TextPair.class,true);
		}

		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			TextPair tp1 = (TextPair) a;
			TextPair tp2 = (TextPair) b;
			
			return tp1.getUserId().compareTo(tp2.getUserId());
		}
	}
	
	public static class SecondSortSortComparator extends WritableComparator{

		protected SecondSortSortComparator() {
			super(TextPair.class,true);
		}

		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			TextPair tp1 = (TextPair) a;
			TextPair tp2 = (TextPair) b;
			int compare = tp1.getUserId().compareTo(tp2.getUserId());
			if(compare!=0)
				return compare;
			
			return tp1.getUserIp().compareTo(tp2.getUserIp());
		}
	}
}
