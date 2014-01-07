package hbase;

import hbase.util.HBaseUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Row;

public class TestUserMappingHit {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		File userIdFile = new File("userId.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(userIdFile)));
		String line = null;
		List<String> list = new ArrayList<String>();
		List<Row> rowList = new ArrayList<Row>();
		while((line=br.readLine())!=null){
			Row row = new Get(line.trim().getBytes());
			rowList.add(row);
		}
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, "User_Mapping");
		Object[] objs = table.batch(rowList);
		System.out.println(objs.length);
		for(Object obj : objs){
			System.out.println(obj);
		}
		
	}

}
