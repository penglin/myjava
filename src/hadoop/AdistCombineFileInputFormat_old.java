package hadoop;

/* myComineFileInputFormat.java */

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.CombineFileInputFormat;
import org.apache.hadoop.mapred.lib.CombineFileRecordReader;
import org.apache.hadoop.mapred.lib.CombineFileSplit;

public class AdistCombineFileInputFormat_old extends CombineFileInputFormat<LongWritable, Text>
{

	@Override
	public RecordReader<LongWritable, Text> getRecordReader(InputSplit split, JobConf conf, Reporter reporter) throws IOException
	{
		return new CombineFileRecordReader(conf, (CombineFileSplit) split, reporter, (Class) AdsitCombineFileRecordReader.class);
	}

	public static class AdsitCombineFileRecordReader implements RecordReader<LongWritable, Text>
	{
		private final LineRecordReader linerecord;

		public AdsitCombineFileRecordReader(CombineFileSplit split, Configuration conf, Reporter reporter, Integer index) throws IOException
		{
			FileSplit filesplit = new FileSplit(split.getPath(index), split.getOffset(index), split.getLength(index), split.getLocations());
			linerecord = new LineRecordReader(conf, filesplit);
		}

		public void close() throws IOException
		{
			linerecord.close();

		}

		public LongWritable createKey()
		{
			return linerecord.createKey();
		}

		public Text createValue()
		{
			return linerecord.createValue();
		}

		public long getPos() throws IOException
		{
			return linerecord.getPos();
		}

		public float getProgress() throws IOException
		{
			return linerecord.getProgress();
		}

		public boolean next(LongWritable key, Text value) throws IOException
		{
			return linerecord.next(key, value);
		}

	}
}

