package hbase;

import hbase.util.HBaseUtil;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.HServerAddress;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.PropertyConfigurator;

public class GetHBaseRegionInfo {
	private static Log log =  LogFactory.getLog(GetHBaseRegionInfo.class);
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String log4jConfigurePath = "log4j.properties";
		File file = new File(log4jConfigurePath);
		if (!file.exists())
			throw new Exception("日志输出Log4j配置文件指定位置<" + log4jConfigurePath + ">不存在.");
		PropertyConfigurator.configure(log4jConfigurePath);
		log.info("Log4j装载配置文件及初始化成功.");
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.11,192.168.6.12", "2181");
		String tablename = "Match_Play_Log";
		
		HTable table = new HTable(conf, tablename);
		Map<HRegionInfo, HServerAddress> map = table.getRegionsInfo();
		Set<Entry<HRegionInfo, HServerAddress>> entrySet = map.entrySet();
		Iterator<Entry<HRegionInfo, HServerAddress>> it = entrySet.iterator();
		while(it.hasNext()){
			Entry<HRegionInfo, HServerAddress> entry = it.next();
			HRegionInfo regionInfo = entry.getKey();
			HServerAddress hserver = entry.getValue();
			String regionName = regionInfo.getRegionNameAsString();
			String hostName = hserver.getHostname();
			if(regionName.contains("20121216")){
				System.out.print(regionInfo.isSplit()+"-->");
				System.out.print(regionName+"-->"+hostName);
//				System.out.print("-->"+regionInfo.isOffline());
				System.out.println("-->startKey"+Bytes.toString(regionInfo.getStartKey()));
				System.out.println("-->endKey"+Bytes.toString(regionInfo.getEndKey()));
				System.out.println(regionInfo.getEncodedName());
			}
		}
		System.out.println("总共region个数:"+map.size());
		
		table.close();
		
	}

	private static void getAllRegions() throws Exception{
		String log4jConfigurePath = "log4j.properties";
		File file = new File(log4jConfigurePath);
		if (!file.exists())
			throw new Exception("日志输出Log4j配置文件指定位置<" + log4jConfigurePath + ">不存在.");
		PropertyConfigurator.configure(log4jConfigurePath);
		log.info("Log4j装载配置文件及初始化成功.");
		Configuration conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.11,192.168.6.12", "2181");
		String tablename = "User_Mapping";
		
		HTable table = new HTable(conf, tablename);
		
		Map<HRegionInfo, HServerAddress> map = table.getRegionsInfo();
		Set<Entry<HRegionInfo, HServerAddress>> entrySet = map.entrySet();
		Iterator<Entry<HRegionInfo, HServerAddress>> it = entrySet.iterator();
		while(it.hasNext()){
			Entry<HRegionInfo, HServerAddress> entry = it.next();
			HRegionInfo regionInfo = entry.getKey();
			HServerAddress hserver = entry.getValue();
			String regionName = regionInfo.getRegionNameAsString();
			String hostName = hserver.getHostname();
			System.out.print(regionInfo.isSplit()+"-->");
			System.out.print(regionName+"-->"+hostName);
			System.out.println("-->"+regionInfo.isOffline());
		}
		
		table.close();
	}
}
