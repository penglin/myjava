package hbase;

import hbase.util.HBaseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHBaseSerializable {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, "Match_Play_Log");
		Get get = new Get(Bytes.toBytes("2012091128aeaf0ec2dabd6711bd8f85b947efa9"));
		get.setMaxVersions(1);
		Get get2 = new Get(Bytes.toBytes("201209068e9b6fa8bbfce345310f8b7a5a706ab2"));
		List<Get> list = new ArrayList<Get>();
		list.add(get);
		list.add(get2);
		
		
		Result[] results = table.get(list);
		File f = new File("hbase_serializable.obj");
		FileOutputStream out = new FileOutputStream(f);
//		OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
//		ObjectOutputStream oout = new ObjectOutputStream(out);
		for(Result result:results){
			NavigableMap<byte[], NavigableMap<byte[], byte[]>> map = result.getNoVersionMap();
			Set<Entry<byte[], NavigableMap<byte[], byte[]>>> entrySet = map.entrySet();
			Iterator<Entry<byte[], NavigableMap<byte[], byte[]>>> it = entrySet.iterator();
			while(it.hasNext()){
				Entry<byte[], NavigableMap<byte[], byte[]>> entry = it.next();
				String key = new String(entry.getKey());
				Set<byte[]> keySets = entry.getValue().keySet();
				for(byte[] keySet:keySets){
					System.out.print(new String(keySet));
					System.out.println(":"+new String(entry.getValue().get(keySet),"utf-8"));
				}
//				entry.getValue();
			}
		}
		out.close();
		
		
//		FileInputStream in = new FileInputStream(f);
//		ObjectInputStream oin = new ObjectInputStream(in);
//		Object obj = oin.readObject();
//		System.out.println(obj);
//		int i=-1;
//		byte[] bytes = new byte[writable.getLength()];
//		in.read(bytes);
//		ImmutableBytesWritable newWritable = new ImmutableBytesWritable(bytes);
//		Result newResult = new Result(newWritable);
//		System.out.println(new String(newResult.getRow()));
	}

}
