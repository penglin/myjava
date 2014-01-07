package hbase.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {
	private static Log log = log = LogFactory.getLog(HBaseUtil.class);
	/**
	 * 创建与HBase配置连接
	 * 
	 * @return 配置对象
	 * @throws Exception 处理异常
	 */
	public static Configuration buildConnection(String ips,String port) throws Exception{
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", ips);
		configuration.set("hbase.zookeeper.property.clientPort", port);
		return configuration;
	}
	
	
	/**
	 * 创建表
	 * @param cfg
	 * @param tablename 表名
	 * @param reCreate 如果表已经存在，是否删除已有的表，重新创建
	 * @throws Exception
	 */
	public static void creatTable(Configuration cfg, String tablename, List<String> hColumnDescriptorList, boolean reCreate) throws Exception
	{
		if(hColumnDescriptorList==null||hColumnDescriptorList.isEmpty())
			throw new Exception("没有指定列族");
		HBaseAdmin admin = new HBaseAdmin(cfg);
		if (admin.tableExists(tablename))
		{
			if(reCreate){
				admin.disableTable(tablename);
				admin.deleteTable(tablename);
			}else{
				return;
			}
		}
		HTableDescriptor tableDescriptor = new HTableDescriptor(tablename);
		for(String hColumnDescriptor:hColumnDescriptorList){
			tableDescriptor.addFamily(new HColumnDescriptor(hColumnDescriptor));
		}
		admin.createTable(tableDescriptor);
	}
	
	/**
	 * 添加数据
	 */
	public static void putData(Configuration cfg, String tablename,String hColumnDescriptor,Map<String,Map<String,String>> values) throws Exception
	{
		if(values==null||values.isEmpty())
			return ;
		HTable table = new HTable(cfg, tablename);
		// 提交
		ArrayList<Put> puts = new ArrayList<Put>();
		Iterator<String> it = values.keySet().iterator();
		while(it.hasNext()){
			String rowKey = it.next();
			Map<String,String> subValues = values.get(rowKey);
			if(subValues==null||subValues.isEmpty())
				continue;
			Iterator<String> subIt = subValues.keySet().iterator();
			while(subIt.hasNext()){
				String columnKey = subIt.next();
				String value = subValues.get(columnKey);
				Put p = new Put(Bytes.toBytes(rowKey));
				p.add(Bytes.toBytes(hColumnDescriptor), Bytes.toBytes(columnKey), Bytes.toBytes(value));
				puts.add(p);
			}
		}
		log.info("save in db:"+values.size());
		table.put(puts);
	}
	
	/**
	 * 添加数据
	 */
	public static void putData(Configuration cfg, HTable table,String hColumnDescriptor,Map<String,Map<String,String>> values) throws Exception
	{
		if(values==null||values.isEmpty())
			return ;
		// 提交
		ArrayList<Put> puts = new ArrayList<Put>();
		Iterator<String> it = values.keySet().iterator();
		while(it.hasNext()){
			String rowKey = it.next();
			Map<String,String> subValues = values.get(rowKey);
			if(subValues==null||subValues.isEmpty())
				continue;
			Iterator<String> subIt = subValues.keySet().iterator();
			while(subIt.hasNext()){
				String columnKey = subIt.next();
				String value = subValues.get(columnKey);
				Put p = new Put(Bytes.toBytes(rowKey));
				p.add(Bytes.toBytes(hColumnDescriptor), Bytes.toBytes(columnKey), Bytes.toBytes(value));
				puts.add(p);
			}
		}
		log.info("save in db:"+values.size());
		long currTime = System.currentTimeMillis();
		table.put(puts);
		log.info("Save in db cost time:"+(System.currentTimeMillis() - currTime));
	}
	
	/**
	 * 添加数据
	 */
	public static void putData(Configuration cfg, HTable table,List<Put> puts) throws Exception
	{
		if(puts==null||puts.isEmpty())
			return ;
		// 提交
//		log.info("save in db:"+puts.size());
//		long currTime = System.currentTimeMillis();
		table.put(puts);
		table.flushCommits();
//		log.info("Save in db cost time:"+(System.currentTimeMillis() - currTime));
	}
	
	/**
	 * 查询数据
	 */
	public static void getData(Configuration cfg, String tablename) throws Exception
	{
		HTable table = new HTable(cfg, tablename);
		// 单条查询
//		Get g = new Get(Bytes.toBytes("penglin"));
//		Result result = table.get(g);
//		System.out.println("uid1 :" + new String(result.getValue(Bytes.toBytes("visitor"), Bytes.toBytes("visitor"))));
		// 遍历查询
		Scan s = new Scan();
//		s.addColumn(Bytes.toBytes("visitor"), Bytes.toBytes("visitor"));
		ResultScanner ss = table.getScanner(s);
		int i=0;
		
		for (Result r : ss)
		{
			i++;
			System.out.println("uid1 :" + new String(r.getValue(Bytes.toBytes("visitor"), Bytes.toBytes("visitor"))));
		}
		System.out.println(i);
	}
}
