package tracking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class FileReadPoper {
	private String[] fileNames = null;
	
	private long lastPrintTime = System.currentTimeMillis();
	
	private int dataPropertiesNum = 59;
	
	private String splitReg = "\t";
	
	public static void main(String[] args) throws Exception {
		String fileName = "C:\\Documents and Settings\\����\\����\\scan.log";
		fileRead(fileName);
		
		String tt = "1355105053337a1mvp05a5u95d31dua6_21004	13551050515670eczldzs9d6u8e0np03_21004	ZTS6BCDR090F	ZTTDIM91096A	";
		System.out.println(tt.split("\t").length);
	}
	
	private static void fileRead(String fileName) throws Exception{
//		String filePath = URLDecoder.decode(FileReadPoper.class.getClassLoader().getResource("").getPath(), "UTF-8")+fileName;
		List<String> list = new ArrayList<String>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"GBK"),1024000);
		String line = null;
		long insertNum = 0;
		int insertTimes = 0;
		long queryNum = 0;
		long queryTimes = 0;
		long dataNum = 0;
		int lineNum = 0;
		Map<String,Long> map = new HashMap<String,Long>();
		try {
			while((line=br.readLine())!=null){
				if(line==null || line.trim().length()==0)
					continue;
				line = line.trim();
				if(line.contains("ִ��ProcessPoper����[192.168.1.76$4028812c3b5f6f00013b63b83f4d0033$Analysis_ZTBFSOM209DG$")){
					String value = line.trim().split("\\$")[3];
					insertNum += Integer.parseInt(value.trim());
					System.out.println(value);
					list.add(value);
					lineNum++;
				}
				if(line.contains("�ಥ���ݵ�HDFS��ʽ�ļ�")){
					String value = line.substring(line.indexOf("[")+1,line.indexOf("]"));
					queryNum += Integer.parseInt(value);
				}
				if(line.contains("������ݵ�HDFS��ʽ�ļ�")){
					String value = line.substring(line.indexOf("[")+1,line.indexOf("]"));
					queryTimes += Integer.parseInt(value);
				}
				if(line.contains("�����ļ�")){
					String value = line.substring(line.indexOf("�м�¼[")+4,line.indexOf("]��"));
					dataNum += Integer.parseInt(value);
				}
				if(line.contains("/usr/local/adsit/precise/upload/demand/")){
					String value = line.split("]")[0].split("\\[")[1];
					insertTimes += Integer.parseInt(value);
					String filename = line.split(",")[1];
					map.put(filename, Long.parseLong(value));
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("�������ݣ�"+insertNum+",�ಥ���ݣ�"+lineNum+",�����ļ���"+dataNum+",�ಥ��¼:"+insertTimes+",�������:"+queryTimes);
		
		long t = 0;
		for(String key : map.keySet()){
			t += map.get(key);
		}
		System.out.println("t====L:"+t);
		Collections.sort(list);
		int sss = 0;
		for(String s : list){
			sss += Integer.parseInt(s);
		}
		int ss = 0;
		for(int i=1;i<=2000;i++){
			ss += i;
		}
		System.out.println(ss - sss);
	}
	
	private static void fileJingZhunPaiRead(String fileName) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"),1024000);
		String line = null;
		long demandCount = 0;
		int demandUniqueCount = 0;
		try {
			while((line=br.readLine())!=null){
				if(line==null || line.trim().length()==0)
					continue;
				line = line.trim();
				line = line.replace("AdPosZoneSegmentGather	", "");
				JSONObject jsonObj = JSONObject.fromObject(line);
				demandCount += jsonObj.getInt("demandCount");
				demandUniqueCount += jsonObj.getInt("demandUniqueCount");
				/*AnalysisSettle settle = (AnalysisSettle) JsonConvertedUtil.jsonStringToObject(line, AnalysisSettle.class);
				if(settle.getAnalysisSettleKey().getSettleDate().equals("2012��03��01��")){
					dataNum += settle.getSettleSum();
				}*/
				/*if(line.startsWith("����")){
					line = line.substring(5);
					insertNum += Integer.parseInt(line);
					insertTimes++;
				}else if(line.startsWith("��ѯ")){
					line = line.substring(5);
					queryNum += Integer.parseInt(line);
					queryTimes++;
				}else{
					
				}*/
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��ѯ������"+demandCount+",��ѯ�����ܺͣ�"+demandUniqueCount);
	}
}