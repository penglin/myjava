package hbase;

import hbase.util.HBaseUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.PropertyConfigurator;

public class HBaseTest extends Thread{
	/**
	 * Log4j��־����
	 */
	private static Log log = null;
	
	private static Random r1 = new Random();
	private static Configuration conf;
	private static String tablename;
	private static long count = 0L;
	private static List<Thread> threadList = new ArrayList<Thread>();
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// ����Log4j�����ļ�,����ļ������ڣ��˳�
		String log4jConfigurePath = "log4j.properties";
		File file = new File(log4jConfigurePath);
		if (!file.exists())
			throw new Exception("��־���Log4j�����ļ�ָ��λ��<" + log4jConfigurePath + ">������.");
		PropertyConfigurator.configure(log4jConfigurePath);
		log = LogFactory.getLog(HBaseTest.class);
		log.info("Log4jװ�������ļ�����ʼ���ɹ�.");
		
		
		conf = HBaseUtil.buildConnection("192.168.6.6,192.168.6.7,192.168.6.8,192.168.6.11,192.168.6.12,192.168.6.13,192.168.6.14", "2181");

		tablename = "User_Identity";
//		tablename = "Test_Increment";
		List<String> hColumnDescriptorList = new ArrayList<String>();
		hColumnDescriptorList.add("visitor");
		HBaseUtil.creatTable(conf, tablename, hColumnDescriptorList, true);
		/*Map<String,Map<String,String>> values = new HashMap<String, Map<String,String>>();
		Map<String,String> columnMap = new HashMap<String, String>();
		columnMap.put("visitor1", "visitor_penglin");
		columnMap.put("visitor2", "visitor_penglin");
		columnMap.put("visitor3", "visitor_penglin");
		values.put("penglin1", columnMap);
		values.put("penglin", columnMap);
		HBaseUtil.putData(conf, tablename, "visitor", values);
		HBaseUtil.getData(conf, tablename);*/
		
/*		String type = args[0];
		if("insert".equals(type)){
			insertData();
		}else{
			queryData();
		}
*/		
//		queryData();
		
		
		
	}
	
	private static void queryData(){
		try {
			Map<String, Long> visitorIdMap = new HashMap<String, Long>();
			HTable table = new HTable(conf, tablename);
			// ������ѯ
			Scan s = new Scan();
//			s.addColumn(Bytes.toBytes("visitor"), Bytes.toBytes("visitor"));
			ResultScanner ss = table.getScanner(s);
			int i=0;
			long currTime = System.currentTimeMillis();
			Iterator<Result> itScaner = ss.iterator();
			long tmpTime = currTime;
			while(itScaner.hasNext()){
				Result r = itScaner.next();
				i++;
				log.info("Key:"+new String(r.getRow()));
				String visitorId = new String(r.getValue(Bytes.toBytes("visitor"), Bytes.toBytes("visitor")));
//				log.info("visitorId:"+visitorId);
				if(!visitorIdMap.containsKey(visitorId)){
					visitorIdMap.put(visitorId, 1L);
				}else{
					long count = visitorIdMap.get(visitorId) + 1L;
					visitorIdMap.put(visitorId, count);
				}
				if(System.currentTimeMillis() - tmpTime>10*1000){
					log.info("�Ѿ���ȡ���ݣ�["+i+"]��");
					tmpTime = System.currentTimeMillis();
				}
			}
			
			log.info("�ܹ���������������"+i+",��ʱ��"+(System.currentTimeMillis() - currTime));
			log.info("["+i+"]�������ж����û���["+visitorIdMap.size()+"]");
			Iterator<String> it = visitorIdMap.keySet().iterator();
			while(it.hasNext()){
				String visitorId = it.next();
				long count = visitorIdMap.get(visitorId);
				log.info("�û�id["+visitorId+"]���ִ���["+count+"]");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("����HBaseʱ�����쳣:", e);
		}
	}
	
	
	/**
	 * ��HBase�����Ӽ�¼
	 */
	private static void insertData(){
		long currTime = System.currentTimeMillis();
		for(int i=0;i<100;i++){
			Thread t = new HBaseTest();
			threadList.add(t);
			t.start();
		}
		validate();
		log.info("��������ʱ(����):"+(System.currentTimeMillis() - currTime));
	}
	
	private static void validate(){
		boolean isHasAlive = true;
		while (isHasAlive)
		{
			isHasAlive = false;
			// ��ѵ�����������߳�״̬
			for (int i = 0; i < threadList.size() && !isHasAlive; i++)
			{
				Thread popThread = threadList.get(i);
				isHasAlive |= !(popThread.getState() == Thread.State.TERMINATED);
			}
			log.info("�����ɼ�¼��"+count);
			// ��ǰ�߳���Ϣ2����
			try
			{
				Thread.sleep(10 * 1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	@Override
	public void run() {
		try {
			for(int i=0;i<10000;i++){
				long currTime = System.currentTimeMillis();
				HBaseUtil.putData(conf, tablename, "visitor", generateData());
				log.info("HBase���һ������¼��ʱ(����):"+(System.currentTimeMillis() - currTime));
			}
		} catch (Exception e) {
			log.error("��HBase�����Ӽ�¼�ǳ����쳣��", e);
		}
	}

	private static int getRandomInt(Random r){
		return r.nextInt(10000000);
	}
	
	private static synchronized Map<String,Map<String,String>> generateData(){
		Map<String,Map<String,String>> values = new HashMap<String, Map<String,String>>();
		for(int i=0;i<100;i++){
			String rowKey = UUID.randomUUID().toString();
			Map<String,String> columnMap = new HashMap<String, String>();
			columnMap.put("visitor", "visitor_"+getRandomInt(r1));
			values.put(rowKey, columnMap);
		}
		count = count + 100;
		return values;
	}
}