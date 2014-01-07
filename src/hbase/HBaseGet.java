package hbase;

import java.io.IOException;
import java.util.NavigableMap;
import java.util.Set;

import hbase.util.HBaseUtil;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseGet {
	public static void main(String[] args) throws Exception {
//		String rowKey = "20120903ca929ff6f21586f02efaff3e125fdc80";
		/*String rowKey = "201209068e9b6fa8bbfce345310f8b7a5a706ab2";
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, "Match_Play_Log");
		Get g = new Get(Bytes.toBytes(rowKey));
		Result result = table.get(g);
		byte[] url = result.getValue(Bytes.toBytes("mp"), Bytes.toBytes("url"));
		byte[] hour = result.getValue(Bytes.toBytes("mp"), Bytes.toBytes("hour"));
		byte[] h = result.getValue(Bytes.toBytes("mp"), Bytes.toBytes("isvp"));
		byte[] date = result.getValue(Bytes.toBytes("mp"), Bytes.toBytes("date"));
		byte[] time = result.getValue(Bytes.toBytes("mp"), Bytes.toBytes("time"));
		byte[] zoneName = result.getValue(Bytes.toBytes("mp"), Bytes.toBytes("zn"));
		System.out.println(new String(new String(zoneName,"utf-8")));
		System.out.println(new String(url,"utf-8"));
		System.out.println(new String(hour));
		System.out.println(new String(date,"utf-8"));
		System.out.println(new String(time,"utf-8"));
		table.close();
		
		String z = new String(new char[]{1})+"山东省青岛市";
		System.out.println(new String(z.getBytes("utf-8"),"utf-8"));*/
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, "User_Identity");
		System.out.println(table.getConfiguration().getLong("hbase.client.keyvalue.maxsize", 10485760L));
		;
		table.close();
//		getUserIdentity();
	}
	
	
	public static void getUserIdentity() throws Exception{
		String myKey = "q:97fb25f214665ba0287910e604a47fe5";
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, "User_Identity");
		Get g = new Get(Bytes.toBytes(myKey));
		Result result = table.get(g);
		KeyValue keyValue = result.getColumnLatest(Bytes.toBytes("uid"), Bytes.toBytes("i"));
		System.out.println(keyValue);
		System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("uid"), Bytes.toBytes("m"))));
		NavigableMap<byte[], byte[]>  map = result.getFamilyMap(Bytes.toBytes("uid"));
		Set<byte[]> values = map.keySet();
		for(byte[] tmp : values){
			String qualifier = Bytes.toString(tmp); 
			if(qualifier.equals("i")){
				continue;
			}
			
			String counterKey = "i:"+qualifier;
			System.out.println(counterKey);
		}
		table.close();
	}
}
