package hadoop;

import hbase.util.HBaseUtil;
import hbase.util.MyHBasePool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MySimpleMapBak extends Mapper<LongWritable, Text, Text, IntWritable> {
	private static final Log log = LogFactory.getLog(MySimpleMapBak.class);
	private static HTable table;
	private static Configuration conf;
	private static MyHBasePool hbasePool;
	
	private static HTablePool pool;
	private static String tablename;
	static {
		try {
			conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
			tablename = "Test_Visitor";
			hbasePool = new MyHBasePool(conf,tablename,40);
			table = new HTable(conf,tablename);
			
			HTableInterfaceFactory factory = new HTableFactory(); 
			factory.createHTableInterface(conf, tablename.getBytes());
			pool = new HTablePool(conf,40,factory);
//			Configuration.addDefaultResource("mapred-site.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//		System.out.println("map " + value.toString());
		String[] split = value.toString().split(",");
		// output.collect(key, value);
		try {
			Map<String, Map<String, String>> values = new HashMap<String, Map<String, String>>();
//			log.info("value:"+value);
			String rowKey = split[0];
			Map<String, String> columnMap = new HashMap<String, String>();
			columnMap.put("visitor", split[1]);
			long currTime = System.currentTimeMillis();
//			HTable htable = (HTable)pool.getTable(tablename);
//			log.info("Get a HTable Connection Cost Time:"+(System.currentTimeMillis() - currTime));
			values.put(rowKey, columnMap);
			
			currTime = System.currentTimeMillis();
			HBaseUtil.putData(conf, table, "visitor", values);
			log.info("Put Data To HTable Cost Time:"+(System.currentTimeMillis() - currTime));
			
//			hbasePool.closeTable(htable);
			context.write(new Text(split[1]), new IntWritable(1));
//			log.info("Mapper:"+rowKey+":"+split[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Mapper Exception", e);
		}
	}

}
