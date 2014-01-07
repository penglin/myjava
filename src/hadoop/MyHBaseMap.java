package hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class MyHBaseMap extends TableMapper<Text,Text>{
	private static final Log log = LogFactory.getLog(MyHBaseMap.class);
	private HTable htable ;
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.cleanup(context);
	}

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
		log.info("Key Name:"+new String(key.get()));
		log.info("uid1 :" + new String(value.getValue(Bytes.toBytes("visitor"), Bytes.toBytes("visitor"))));
		super.map(key, value, context);
	}

	@Override
	public void run(Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.run(context);
	}

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		htable = new HTable("Test_Visitor");
		super.setup(context);
	}

}
