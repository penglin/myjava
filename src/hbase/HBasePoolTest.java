package hbase;

import java.io.IOException;

import hbase.util.HBaseUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;

/**
 * HBase¡¨Ω”≥ÿ≤‚ ‘
 * @author ≈Ì¡ÿ
 *
 */
public class HBasePoolTest {
	private static final Log log = LogFactory.getLog(HBasePoolTest.class);	
	/**
	 * @param args
	 */
	
	private static HTablePool hTablePool;
	private static String tablename ;
	static {
		try {
			Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
			tablename = "Test_Visitor";
			HTableInterfaceFactory factory = new HTableFactory(); 
			factory.createHTableInterface(conf, tablename.getBytes());
			hTablePool = new HTablePool(conf,40,factory);
//			Configuration.addDefaultResource("mapred-site.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		testHTablePool();
//		testHTable();
	}

	private static void testHTable() throws Exception{
		log.info("testHTable start....");
		long currTime = System.currentTimeMillis();
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		for(int i=0;i<1000;i++){
			HTable table = new HTable(conf, tablename);
			table.close();
		}
		log.info("Get HTable cost time:"+(System.currentTimeMillis() - currTime));
		log.info("testHTable end....");
	}
	
	private static void testHTablePool() throws IOException{
		long currTime = System.currentTimeMillis();
		log.info("");
		HTableInterface hTableinterface = hTablePool.getTable(tablename);
		for(int i=0;i<1000;i++){
			log.info("tableName:"+hTableinterface.getTableName());
//			hTablePool.closeTablePool(tablename);
			hTableinterface.close();
		}
		log.info("Get HTable cost time:"+(System.currentTimeMillis() - currTime));
	}
}
