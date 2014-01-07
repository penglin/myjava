package hadoop.mapreduce.savehbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class AccessLogReducer extends TableReducer<Text, Text, ImmutableBytesWritable>
{
	public void reduce(Text arg0, Iterable<Text> arg1, Context context)
	{

		String v = arg1.iterator().next().toString();
		String k = arg0.toString();

		ImmutableBytesWritable key = new ImmutableBytesWritable(Bytes.toBytes(k));
		Put put = new Put(Bytes.toBytes(k));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier"), Bytes.toBytes(v));

		System.out.println("reduce key:" + k + "  value:" + v);

		try
		{
			context.write(key, put);
		}
		catch (Exception e)
		{

		}
	}
}
