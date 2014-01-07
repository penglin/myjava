package hbase;

import hbase.util.HBaseUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import cn.adsit.common.util.DateUtil;

public class HBaseScanTest extends TestCase{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getBeforeDate(4));
		System.out.println(DateUtil.getBeforeDate(3));
//		testScan();
		System.out.println(DateUtil.formateDate(new Date(1343393674226L)));
//		testGet();
		
		testMeta();
	}

	private static void testScan() throws Exception{
		Scan scan = new Scan();
		RegexStringComparator comparator = new RegexStringComparator(".*,.*");
		Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL,comparator);
		Filter preFilter = new PrefixFilter("d:".getBytes());
		File userIdFile = new File("userId2.txt");
		FileOutputStream out = new FileOutputStream(userIdFile);

		scan.setBatch(1);
		scan.setCaching(1000);
		scan.setFilter(preFilter);
		scan.setTimeRange(DateUtil.parseDate(DateUtil.getBeforeDate(4)).getTime(), DateUtil.parseDate(DateUtil.getBeforeDate(3)).getTime());
//		Configuration conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTablePool pool = new HTablePool(conf, 10);
		HTableInterface htable = pool.getTable("User_Mapping");
		ResultScanner rs = htable.getScanner(scan);
		Iterator<Result> it = rs.iterator();
		while(it.hasNext()){
			Result result = it.next();
			List<KeyValue> keyvalues = result.list();
			for(KeyValue keyvalue : keyvalues){
				if(keyvalue==null)
					continue;
//				System.out.print(code(new String(keyvalue.getFamily()), "UTF-8", "GBK")+",");
//				System.out.print(code(new String(keyvalue.getQualifier()), "UTF-8", "GBK")+",");
//				System.out.print(code(new String(keyvalue.getRow()), "UTF-8", "GBK")+",");
//				System.out.println(code(new String(keyvalue.getValue()), "UTF-8", "GBK"));
				out.write((new String(keyvalue.getRow())+new String(keyvalue.getValue())+"\n").getBytes());
			}
		}
		out.flush();
		out.close();
		rs.close();
		pool.closeTablePool("User_Identity");
	}
	
	private static void testGet() throws Exception{
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, "User_Mapping");
		Get get = new Get(Bytes.toBytes("t:a"));
		Result result = table.get(get);
		if(result!=null){
			List<KeyValue> keyvalues = result.list();
			if(keyvalues!=null&&!keyvalues.isEmpty()){
				for(KeyValue keyvalue : keyvalues){
					System.out.println(new String(keyvalue.getRow()));
				}
			}
		}
		table.close();
	}
	
	private static void testMeta() throws Exception{
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTable table = new HTable(conf, ".META.");
		Scan scan = new Scan();
//		RegexStringComparator comparator = new RegexStringComparator("info:regioninfo");
//		Filter filter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, comparator);
		scan.setBatch(10);
		scan.setCaching(100);
//		scan.setFilter(filter);
		
		File userIdFile = new File("User_Identity.txt");
		FileOutputStream out = new FileOutputStream(userIdFile);
		
		ResultScanner rs = table.getScanner(scan);
		Iterator<Result> it = rs.iterator();
		while(it.hasNext()){
			Result result = it.next();
			List<KeyValue> keyvalues = result.list();
			boolean flag = false;
			for(KeyValue keyvalue : keyvalues){
				if(keyvalue==null)
					continue;
				String qualifier = Bytes.toString(keyvalue.getQualifier());
				if("regioninfo".equals(qualifier)){
					flag = true;
					break;
				}
			}
			if(!flag){
				out.write(result.getRow());
				out.write("\n".getBytes());
			}
			
		}
		table.close();
		rs.close();
	}
	
	private static String code(String source, String fromCode, String toCode) throws UnsupportedEncodingException{
		return new String(new String(source.getBytes(fromCode),"ISO-8859-1").getBytes(toCode));
	}
	
	
	private static void testScanMatchPlayLog() throws Exception{
		Scan scan = new Scan();
		
		BinaryComparator bc = new BinaryComparator(Bytes.toBytes("cqpeople_sp"));
		SingleColumnValueFilter scvFilter = new SingleColumnValueFilter(Bytes.toBytes("mp"), Bytes.toBytes("w"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes("cqpeople_sp"));
		
		File userIdFile = new File("userId2.txt");
		FileOutputStream out = new FileOutputStream(userIdFile);

		scan.setBatch(100);
		scan.setCaching(1000);
		scan.setFilter(scvFilter);
//		scan.setTimeRange(DateUtil.parseDate(DateUtil.getBeforeDate(3)).getTime(), DateUtil.parseDate(DateUtil.getBeforeDate(2)).getTime());
//		Configuration conf = HBaseUtil.buildConnection("192.168.1.146,192.168.1.147,192.168.1.148", "2181");
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTablePool pool = new HTablePool(conf, 10);
		HTableInterface htable = pool.getTable("Match_Play_Log");
		ResultScanner rs = htable.getScanner(scan);
		Iterator<Result> it = rs.iterator();
		while(it.hasNext()){
			Result result = it.next();
			List<KeyValue> keyvalues = result.list();
			for(KeyValue keyvalue : keyvalues){
				if(keyvalue==null)
					continue;
//				System.out.print(code(new String(keyvalue.getFamily()), "UTF-8", "GBK")+",");
//				System.out.print(code(new String(keyvalue.getQualifier()), "UTF-8", "GBK")+",");
//				System.out.print(code(new String(keyvalue.getRow()), "UTF-8", "GBK")+",");
//				System.out.println(code(new String(keyvalue.getValue()), "UTF-8", "GBK"));
//				out.write((new String(keyvalue.getRow())+new String(keyvalue.getValue())+"\n").getBytes());
				if(Bytes.toBytes("w").equals(keyvalue.getQualifier())){
					System.out.println(new String(keyvalue.getRow())+":"+new String(keyvalue.getValue())+"\n");
				}
			}
		}
		out.flush();
		out.close();
		rs.close();
		pool.closeTablePool("Match_Play_Log");
	}
	
	private static void testFilterList() throws Exception{
		String tableName = "Match_Play_Log";
//		String tableName = "testInsert";
		/*Scan scan = new Scan();
		scan.addFamily("mp".getBytes());
		FilterList flist = new FilterList(FilterList.Operator.MUST_PASS_ONE);
//		flist.addFilter(new PrefixFilter(Bytes.toBytes("20120904")));
//		flist.addFilter(new PrefixFilter(Bytes.toBytes("20120905")));
		scan.setBatch(100);
		scan.setCaching(1000);
		RegexStringComparator comparator = new RegexStringComparator("^(20120905|20120906)+([0-9a-zA-Z])+$");
		WhileMatchFilter wfilter = new WhileMatchFilter(new RowFilter(CompareFilter.CompareOp.EQUAL, comparator));
//		flist.addFilter(wfilter);
		scan.setFilter(new RowFilter(CompareFilter.CompareOp.EQUAL, comparator));*/
		
		Scan scan = new Scan();
		scan.setCaching(20); // 1 is the default in Scan, which will be bad
		// for MapReduce jobs
		scan.setCacheBlocks(false); // don't set to true for MR jobs
		// set other scan attrs
		scan.addFamily(Bytes.toBytes("mp"));
		scan.setMaxVersions(1);
		String startRow = "2012年09月21日".replaceAll("日|月|年", "");
		scan.setStartRow(Bytes.toBytes(startRow));
		//DateUtil.formateDate(new Date(DateUtil.parseDate("2012年09月21日").getTime() + 24*60*60*1000));
		String tmpNextDate = DateUtil.formateDate(new Date(DateUtil.parseDate("2012年09月21日").getTime() + 24*60*60*1000));
		String stopRow = tmpNextDate.replaceAll("日|月|年", "");
		scan.setStopRow(Bytes.toBytes(stopRow));
		scan.setFilter(new PrefixFilter(Bytes.toBytes("20120921")));
		
		File userIdFile = new File("userId2.txt");
		FileOutputStream out = new FileOutputStream(userIdFile);
		Writer writer = new OutputStreamWriter(out,"UTF-8");
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");
		HTablePool pool = new HTablePool(conf, 10);
		HTableInterface htable = pool.getTable(tableName);
		ResultScanner rs = htable.getScanner(scan);
		Iterator<Result> it = rs.iterator();
		while(it.hasNext()){
			Result result = it.next();
			List<KeyValue> keyvalues = result.list();
			writer.write(new String(result.getRow()));
			writer.write(":\n");
			for(KeyValue keyvalue : keyvalues){
				if(keyvalue==null)
					continue;
				System.out.println(new String(keyvalue.getQualifier())+":"+new String(keyvalue.getValue())+",");
				writer.write((new String(keyvalue.getQualifier())+new String(keyvalue.getValue())+","));
			}
			writer.write("\n--------------------------------------\n");
		}
		writer.flush();
		writer.close();
		rs.close();
		pool.closeTablePool(tableName);
	}
}
