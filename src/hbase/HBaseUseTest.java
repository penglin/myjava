package hbase;

import hbase.util.HBaseUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.PropertyConfigurator;


public class HBaseUseTest {
	private static Log log =  LogFactory.getLog(HBaseUseTest.class);
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 加载Log4j配置文件,如果文件不存在，退出
//		String log4jConfigurePath = "/test/penglin/hbase_test/log4j.properties";
		String log4jConfigurePath = "log4j.properties";
		File file = new File(log4jConfigurePath);
		if (!file.exists())
			throw new Exception("日志输出Log4j配置文件指定位置<" + log4jConfigurePath + ">不存在.");
		PropertyConfigurator.configure(log4jConfigurePath);
		log.info("Log4j装载配置文件及初始化成功.");
		Configuration conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
		String tablename = "Test_Visitor";
		
		HTable table = new HTable(conf, tablename);
//		table.setScannerCaching(50);
		// 单条查询
//		Get g = new Get(Bytes.toBytes("penglin"));
//		Result result = table.get(g);
//		System.out.println("uid1 :" + new String(result.getValue(Bytes.toBytes("visitor"), Bytes.toBytes("visitor"))));
		// 遍历查询
//		List<String> rowKeys = getRowKeyFromFile("/test/penglin/rowKey.txt");
//		List<String> rowKeys = getRowKeyFromFile("rowKey.txt");
		
		try {
			List<String> rowKeys = new ArrayList<String>();
			rowKeys.add("705e2e819ea541d6aa0efb2d69fe87a8");
//			rowKeys.add("612cba09b689499299ea2b8875054084");
			/*for(String rowKey : rowKeys){
				update(rowKey,table);
			}*/
			for(String rowkey : rowKeys){
				query(rowkey, table);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.close();
	}
	
	private static void query(String rowkey, HTable table) throws IOException{
		Get get = new Get(rowkey.getBytes());
		long currTime = System.currentTimeMillis();
		Result result = table.get(get);
		if(result==null)
			return;
		long time1 = System.currentTimeMillis() - currTime;
		currTime = System.currentTimeMillis();
		
		NavigableMap<byte[], byte[]> maps = result.getFamilyMap("visitor".getBytes());
		if(maps==null)
			return;
		
//		KeyValue keyValue = result.getColumnLatest("visitor".getBytes(), "visitor_0".getBytes());
//		log.info("Key:"+new String(keyValue.getKey()));
//		log.info("Value:"+new String(keyValue.getValue(),"utf-8"));
		
		Set<Entry<byte[], byte[]>> sets = maps.entrySet();
		Iterator<Entry<byte[], byte[]>> it = sets.iterator();
		while(it.hasNext()){
			Entry<byte[], byte[]> entry = it.next();
			System.out.println("colunm Key:"+new String(entry.getKey())+"\tcolumn Value:"+new String(entry.getValue(),"UTF-8"));
		}
		long time2 = System.currentTimeMillis() - currTime;
		log.info("Query RowKey["+rowkey+"] Cost Time:"+(time1)+",Iterator Cost Time:"+time2);
	}
	
	private static void update(String rowKey, HTable table) throws IOException{
		long currTime = System.currentTimeMillis();
		Put p = new Put(Bytes.toBytes(rowKey));
		p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_0"), Bytes.toBytes("abcdefghijkmnopqrstuvwxyz"));
		p.add(Bytes.toBytes("visitor"), Bytes.toBytes("visitor_1"), Bytes.toBytes("我是老大我怕谁"));
		table.put(p);
		table.flushCommits();
		log.info("Update RowKey["+rowKey+"] Cost Time:"+(System.currentTimeMillis()-currTime));
	}
	
	private static List<String> getRowKeyFromFile(String filePath) throws UnsupportedEncodingException, FileNotFoundException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"),1024000);
		String line = null;
		List<String> rowKeys = new ArrayList<String>();
		try {
			while((line=br.readLine())!=null){
				if(line==null || line.trim().length()==0)
					continue;
				line = line.trim();
				rowKeys.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowKeys;
	}
}
