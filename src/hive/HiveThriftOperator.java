/*
 * adsit
 * 2011-11-10
 */
package hive;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.service.HiveClient;
import org.apache.hadoop.hive.service.HiveServerException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class HiveThriftOperator
{
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());

	// hive数据库
	private String dbName;

	// hive 地址 & 端口
	private String host;
	private int part;

	public HiveThriftOperator(String host, int part, String dbName)
	{
		this.host = host;
		this.part = part;
		this.dbName = dbName;
	}

	public Set<String> getTableNames(String tableNamePrefix) throws HiveServerException, TException
	{
		Set<String> result = new TreeSet<String>();

		for (String tablename : this.executeQuery("show tables"))
		{
			if (tablename.startsWith(tableNamePrefix))
			{
				result.add(tablename);
			}
		}

		return result;
	}

	public void dropTable(String... tableName) throws HiveServerException, TException
	{
		for (String s : tableName)
		{
			this.execute("drop table if exists " + s);
		}
	}

	public void execute(String hql) throws HiveServerException, TException
	{
		TTransport transport = new TSocket(this.host, this.part);
		try
		{
			TProtocol protocol = new TBinaryProtocol(transport);
			HiveClient client = new HiveClient(protocol);

			transport.open();
//			client.execute("set mapred.map.tasks=48");
//			client.execute("set mapred.reduce.tasks = 8");
			client.execute("use " + this.dbName);
			client.execute(hql);
		}
		finally
		{
			if (transport.isOpen())
			{
				transport.close();
			}
		}
	}

	public List<String> executeQuery(String hql) throws HiveServerException, TException
	{
		List<String> result = null;

		TTransport transport = new TSocket(this.host, this.part);
		try
		{
			TProtocol protocol = new TBinaryProtocol(transport);
			HiveClient client = new HiveClient(protocol);

			transport.open();
			client.execute("use " + this.dbName);
			client.execute(hql);

			result = client.fetchAll();
		}
		finally
		{
			if (transport.isOpen())
			{
				transport.close();
			}
		}

		return result;
	}
	
	public static void main(String[] args) throws HiveServerException, TException {
		HiveThriftOperator hiveOperator = new HiveThriftOperator("192.168.1.143", 10000, "penglin_test");
		Set<String> set = hiveOperator.getTableNames("visitor");
		System.out.println(set.size());

	}
}

