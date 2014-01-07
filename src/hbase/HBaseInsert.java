package hbase;

import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import cn.adsit.common.util.UUIDUtil;

public class HBaseInsert
{
	private static Configuration buildConnection() throws Exception
	{
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", "192.168.6.13");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		return configuration;
	}

	private static void creatTable(Configuration cfg, String tablename) throws Exception
	{
		HBaseAdmin admin = new HBaseAdmin(cfg);
		if (admin.tableExists(tablename))
		{
			admin.disableTable(tablename);
			admin.deleteTable(tablename);
		}
		HTableDescriptor tableDescriptor = new HTableDescriptor(tablename);
		tableDescriptor.addFamily(new HColumnDescriptor("f"));
		admin.createTable(tableDescriptor);
	}

	private static void putData(Configuration cfg, String tablename) throws Exception
	{
		HTable table = new HTable(cfg, tablename);
		Put put = new Put(Bytes.toBytes("uid_1"));
		for (int i = 1; i <= 50; i++)
		{
			if(i%2==0){
				put.add(("f").getBytes(), ("20120904" + i).getBytes(), Bytes.toBytes(""+i));
			}else if(i%3==0){
				put.add(("f").getBytes(), ("20120905" + i).getBytes(), Bytes.toBytes(i));
			}else if(i%5==0){
				put.add(("f").getBytes(), ("20120906" + i).getBytes(), Bytes.toBytes(Integer.valueOf(i).toString()));
			}
		}
		table.put(put);
		table.close();
	}
	
	private static void putData(HTable talbe,String rowKey) throws Exception
	{
		Put put = new Put(Bytes.toBytes(rowKey));
		for (int i = 1; i <= 50; i++)
		{
			if(i%2==0){
				put.add(("f").getBytes(), ("c" + i).getBytes(), Bytes.toBytes(""+i));
			}else if(i%3==0){
				put.add(("f").getBytes(), ("c" + i).getBytes(), Bytes.toBytes(i));
			}else if(i%5==0){
				put.add(("f").getBytes(), ("c" + i).getBytes(), Bytes.toBytes(Integer.valueOf(i).toString()));
			}
		}
		talbe.put(put);
	}

	private void getData(Configuration cfg, String tablename) throws Exception
	{
		HTable table = new HTable(cfg, tablename);
		Get g = new Get(Bytes.toBytes("uid1"));
		Result result = table.get(g);
		System.out.println("user_id uid1 :" + new String(result.getValue(Bytes.toBytes("tags"), Bytes.toBytes("user_id"))));
		Scan s = new Scan();
		ResultScanner ss = table.getScanner(s);
		for (Result r : ss)
		{
			System.out.println(new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("user_id"))) + ":" + new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag1"))) + ":" + new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag1_seq"))) + ":" + new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag2"))) + ":" + new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag2_seq"))));
		}
	}

	public static void main(String[] args) throws Exception
	{
		Configuration conf = buildConnection();
		creatTable(conf, "testInsert");
		HTable table = new HTable(conf, "testInsert");
		String[] s = new String[]{"20120904","20120905","20120906"};
		for (int i = 0; i < 100; i++)
		{
			for(String tmp : s){
				putData(table,tmp+UUIDUtil.getUUID());
			}
			
//			Thread.sleep(10000);
		}
		table.close();
	}
}
