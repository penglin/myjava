package hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * mapred.max.split.size <br/>
 * mapred.min.split.size.per.node <br/>
 * mapred.min.split.size.per.rack <br/>
 * 通过设置上面的值来控制产生的map个数
 * <br/>具体设置查看CombineFileInputFormat源码
 * @author 彭霖
 *
 */
public class AdistCombineFileInputFormat extends CombineFileInputFormat<LongWritable, Text> {
	
	private static final Log log = LogFactory.getLog(AdistCombineFileInputFormat.class);

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException {
		CombineFileSplit cfSplit = (CombineFileSplit) split;
		return new CombineFileRecordReader(cfSplit, context, AdsitCombineFileRecordReader.class);
	}
	
	public static class AdsitCombineFileRecordReader<K extends WritableComparable, V extends Writable> extends RecordReader<K, V> {
		private RecordReader<K, V> recordReader;

		public AdsitCombineFileRecordReader(CombineFileSplit hsplit, TaskAttemptContext context, Integer partition) throws IOException, InterruptedException {
			InputFormat<K, V> inputFormat = (InputFormat<K, V>) ReflectionUtils.newInstance(TextInputFormat.class, context.getConfiguration());

			FileSplit fsplit = new FileSplit(hsplit.getPaths()[partition], hsplit.getStartOffsets()[partition], hsplit.getLengths()[partition], hsplit
					.getLocations());

			this.recordReader = inputFormat.createRecordReader(fsplit, context);
			this.recordReader.initialize(fsplit, context);
			log.info("recordReader Class:"+recordReader.getClass().getName());
			log.info("inputFormat Class:"+inputFormat.getClass().getName());
		}

		public void close() throws IOException {
			recordReader.close();
		}

		public K getCurrentKey() throws IOException, InterruptedException {
			return (K) recordReader.getCurrentKey();
		}

		public V getCurrentValue() throws IOException, InterruptedException {
			return (V) recordReader.getCurrentValue();
		}

		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		}

		public boolean nextKeyValue() throws IOException, InterruptedException {
			return recordReader.nextKeyValue();
		}

		public float getProgress() throws IOException, InterruptedException {
			return recordReader.getProgress();
		}
	}
}
