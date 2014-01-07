/**
 * HBase�ͻ��˷�������
 */
package hbase;

import java.util.ArrayList;

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

/**
 * @author Administrator
 * 
 * HBase�ͻ��˷�������
 */
public class HBaseClientSample
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			HBaseClientSample client = new HBaseClientSample();
			Configuration config = client.buildConnection();
			String table = "Test_Visitor";
			client.creatTable(config, table);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ����
	 * 
	 * @throws Exception �����쳣
	 */
	private void sample() throws Exception
	{
		Configuration config = this.buildConnection();
		this.creatTable(config, "panel.tag.user_tag");
		this.putData(config, "panel.tag.user_tag");
		this.getData(config, "panel.tag.user_tag");
	}

	/**
	 * ������HBase��������
	 * 
	 * @return ���ö���
	 * @throws Exception �����쳣
	 */
	private Configuration buildConnection() throws Exception
	{
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", "192.168.6.6,192.168.6.11,192.168.6.12");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		return configuration;
	}

	/**
	 * ������
	 */
	private void creatTable(Configuration cfg, String tablename) throws Exception
	{
		HBaseAdmin admin = new HBaseAdmin(cfg);
		if (admin.tableExists(tablename))
		{
			admin.disableTable(tablename);
			admin.deleteTable(tablename);
		}
		HTableDescriptor tableDescriptor = new HTableDescriptor(tablename);
		tableDescriptor.addFamily(new HColumnDescriptor("visitor"));
		admin.createTable(tableDescriptor);
	}

	/**
	 * ��������
	 */
	private void putData(Configuration cfg, String tablename) throws Exception
	{
		HTable table = new HTable(cfg, tablename);
		// �ύ
		ArrayList<Put> puts = new ArrayList<Put>();
		for (int i = 1; i <= 10; i++)
		{
			Put p = new Put(Bytes.toBytes("uid" + i));
			p.add(Bytes.toBytes("tags"), Bytes.toBytes("user_id"), Bytes.toBytes("uid" + i));
			p.add(Bytes.toBytes("tags"), Bytes.toBytes("tag1"), Bytes.toBytes("zone:beijing"));
			p.add(Bytes.toBytes("tags"), Bytes.toBytes("tag1_seq"), Bytes.toBytes("5"));
			p.add(Bytes.toBytes("tags"), Bytes.toBytes("tag2"), Bytes.toBytes("segment:1"));
			p.add(Bytes.toBytes("tags"), Bytes.toBytes("tag2_seq"), Bytes.toBytes("5"));
			puts.add(p);
		}
		table.put(puts);
	}

	/**
	 * ��ѯ����
	 */
	private void getData(Configuration cfg, String tablename) throws Exception
	{
		HTable table = new HTable(cfg, tablename);
		// ������ѯ
		Get g = new Get(Bytes.toBytes("uid1"));
		Result result = table.get(g);
		System.out.println("user_id uid1 :" + new String(result.getValue(Bytes.toBytes("tags"), Bytes.toBytes("user_id"))));
		// ������ѯ
		Scan s = new Scan();
		ResultScanner ss = table.getScanner(s);
		for (Result r : ss)
		{
			System.out.println(new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("user_id"))) + ":"
					+ new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag1"))) + ":"
					+ new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag1_seq"))) + ":"
					+ new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag2"))) + ":"
					+ new String(r.getValue(Bytes.toBytes("tags"), Bytes.toBytes("tag2_seq"))));
		}
	}
}