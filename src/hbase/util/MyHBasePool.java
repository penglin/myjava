package hbase.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;

public class MyHBasePool {
	private static HTablePool hTablePool;
	
	private static Map<HTable,Boolean> htableMap  = new HashMap<HTable,Boolean>();
	
	static {
		
	}
	
	public MyHBasePool(Configuration conf, String tableName, int maxSize){
		try {
			HTableInterfaceFactory factory = new HTableFactory(); 
			factory.createHTableInterface(conf, tableName.getBytes());
			hTablePool = new HTablePool(conf,40,factory);
			for(int i=0;i<maxSize;i++){
				htableMap.put((HTable)hTablePool.getTable(tableName), Boolean.TRUE);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MyHBasePool(Configuration conf, String tableName){
		try {
			HTableInterfaceFactory factory = new HTableFactory(); 
			factory.createHTableInterface(conf, tableName.getBytes());
			hTablePool = new HTablePool(conf,40,factory);
			for(int i=0;i<40;i++){
				htableMap.put((HTable)hTablePool.getTable(tableName), Boolean.TRUE);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取一个HTable连接
	 * @return
	 */
	public HTable getHTable(){
		HTable htable = getUseableHTable();
		if(htable==null){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			htable = getUseableHTable();
		}
		return htable;
	}
	
	/**
	 * 获取到一个标志是可用的HTable连接
	 * @return
	 */
	private HTable getUseableHTable(){
		Set<HTable> keys = htableMap.keySet();
		Iterator<HTable> it = keys.iterator();
		while(it.hasNext()){
			HTable htable = it.next();
			if(htableMap.get(htable)){
				htableMap.put(htable, Boolean.FALSE);
				return htable;
			}
		}
		return null;
	}
	
	/**
	 * 将HTable返回到连接池中
	 * @param htable
	 */
	public void closeTable(HTable htable){
		if(htable==null)
			return ;
		htableMap.put(htable, Boolean.TRUE);
	}
	
	/**
	 * 将连接池中的所有HTable连接关闭
	 */
	public void close(){
		Set<HTable> keys = htableMap.keySet();
		Iterator<HTable> it = keys.iterator();
		while(it.hasNext()){
			HTable htable = it.next();
			try {
				htable.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		htableMap.clear();
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		String tablename = "Test_Visitor";
		MyHBasePool pool = new MyHBasePool(conf, tablename);
		System.out.println(htableMap.size());
		HTable htable = pool.getHTable();
		System.out.println(new String(htable.getTableName()));
	}

}
