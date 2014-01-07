package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import cn.adsit.common.util.DateUtil;
import cn.adsit.common.util.FileUtil;
import cn.adsit.common.util.JsonConvertedUtil;
import file.pojo.MyVideoPlay;

/**
 * 生成测试文件
 * @author 彭霖
 *
 */
public class GenerateJingZhunPaiFile extends Thread{
	
	static String[] ad = new String[]{
			"402880d829b63c850129b642ab7c0008,402880d829b63c850129b652c7a1000c",
			"402880d829b63c850129b642ab7c0008,402880d829b63c850129b6589ea00023",
			"402880d829b63c850129b642ab7c0008,402880d829b63c850129b65aea26002f",
			"4028810929920e390129b570915a04ac,4028810929b1072e0129b7cdb1b4065d",
			"4028810929920e390129b570915a04ac,4028810929b1072e0129b7cdb1b40661"};

	static String[] actionImplNumbers = new String[]{"max_close_1","max_close_2","max_close_3","max_close_4"};
	
	static String[] publishVideoIds = new String[]{"402881091feae971011feee9cf330022","402880be1ce4c19f011ce4c2224e0002",
		"402880be1e05e67d011e0682d3460020",
		"402880be1e4dc696011e4df2b67e0008"};
	
	static String[] videoIds = new String[]{"videoIds1","videoIds2","videoIds3","videoIds4","videoIds5"};
	
	private static String demandPrefix = "VideoDemand";
	private static String demandSuffix = "xml";
	private static String demandDirectory = "/jinzhunpai/VideoDemandWorkspace/";
	
	private static String playDirectory = "/disk3/penglin/hiveTestFile/";
	private static String playPrefix = "PlayLog";
	private static String playSuffix = "log";
	
	private static String clickDirectory = "/jinzhunpai/VideoClickWorkspace/";
	private static String clickPrefix = "VideoClick";
	private static String clickSuffix = "xml";
	
	private static String vasDirectory = "/jinzhunpai/ValueAddWorkspace/";
	private static String vasPrefix = "ValueAdd";
	private static String vasSuffix = "xml";
	
	private static int youxiao = 0;
	private static int bofang = 0;
	private static int playCount = 0;
	private static int clickCount = 0 ;
	private static int vasCount = 0 ;
	
	private static List<Thread> threadList = new ArrayList<Thread>();
	
	private static Object obj = new Object();
	private static Object obj2 = new Object();
	private static Object obj3 = new Object();
	private static Object obj4 = new Object();
	private static Object obj5 = new Object();
	
	private static Map<String, List<String>> zoneMap = null;
	
	private static String[] provinces = new String[]{"安徽省","北京市","福建省","甘肃省","广东省","广西","贵州省","海南省","河北省","河南省","黑龙江","湖北省","湖南省","吉林省","江苏省","江西省","辽宁省","内蒙古","宁夏","青海省","山东省","山西省","陕西省","上海市","四川省","台湾","天津市","西藏","香港","新疆","云南省","浙江省","重庆市","澳门"};
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		zoneMap = genZone("zone.txt");
		for(int i=0;i<100;i++){
			Thread t = new GenerateJingZhunPaiFile();
			threadList.add(t);
			t.start();
		}
		validate();
		System.out.println("valid play:"+youxiao);
		System.out.println("play:"+bofang);
		System.out.println("playCount:"+playCount);
	}

	
	private static void validate(){
		boolean isHasAlive = true;
		while (isHasAlive)
		{
			isHasAlive = false;
			// 轮训检查各个处理线程状态
			for (int i = 0; i < threadList.size() && !isHasAlive; i++)
			{
				Thread popThread = threadList.get(i);
				isHasAlive |= !(popThread.getState() == Thread.State.TERMINATED);
			}
			System.out.println("valid play:"+youxiao);
			System.out.println("play:"+bofang);
			System.out.println("playCount:"+playCount);
			System.out.println("-------------------------------");
			// 当前线程休息2秒钟
			try
			{
				Thread.sleep(5 * 1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	@Override
	public void run() {
		try {
			List<String> playTrackContentList = new ArrayList<String>();
			String subDirectory = DateUtil.getCurrSimpleTime().substring(0, 12);
			String directory = playDirectory+subDirectory;
			String uuid = genUUID();
			String fileName = directory + "/"+ playPrefix+"_"+uuid+"."+playSuffix;
			for(int j=0;j<2000;j++){
				playTrackContentList.clear();
				
				for(int i=0;i<1000;i++){
					playTrackContentList.add(genVideoPlay()+"\n");
				}
				
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				
				genFile(playTrackContentList, directory, fileName);
				File file = new File(fileName);
				long length = file.length();
				if(length>512000000){
					subDirectory = DateUtil.getCurrSimpleTime().substring(0, 12);
					directory = playDirectory+subDirectory;
					fileName = directory + "/"+ playPrefix+"_"+uuid+"."+playSuffix;
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Random random = new Random();
	private static Random random2 = new Random();
	private static Random random3 = new Random();
	private static Random random4 = new Random();
	
	private static Random userIdRandom = new Random();
	private static Random orderIdRandom = new Random();
	private static Random workstationIdRandom = new Random();
	private static Random zoneRandom = new Random();
	/**
	 * 生成匹配数据
	 * @param demand
	 * @return
	 * @throws Exception 
	 */
	private static String genVideoPlay() throws Exception{
		MyVideoPlay play = new MyVideoPlay();
		play.setVideoPlayId(genUUID());
		play.setVideoDemandId(genVideoDemandId());
		play.setUserId(genAllKindsId("aaaaaaaaaaaaaaa_visitor_id_", userIdRandom, 80000000));
//		play.setUserId(genAllKindsId("aaaaaaaaaaaaaaa_visitor_id_", userIdRandom, 200000));
		
		String orderId = genAllKindsId("campaign_id_campaign_id_", orderIdRandom, 50);
//		String orderId = genAllKindsId("aaaaaaaaaaaaaaa_visitor_id_", orderIdRandom, 10);
		String advertiserVideoId = orderId + getRandom(random,2);
		String publisVideoId = orderId + getRandom(random2,5);
		play.setOrderId(orderId);
		play.setAdvertiserVideoId(advertiserVideoId);
		play.setPublishVideoId(publisVideoId);
		
		String workstationId = genAllKindsId("workstation_id_workstation_id", workstationIdRandom, 5);
//		String workstationId = genAllKindsId("workstation_id_workstation_id", workstationIdRandom, 10);
		String adPosId = workstationId + "ad_pos";
//		String adPosId = workstationId + "ad_pos" + getRandom(random3,5);
		play.setWorkstationId(workstationId);
		play.setContentClassId("contentClassIdcontentClassId");
		play.setAdPosId(adPosId);
		
		play.setUserIp("192.168.0.164");
		String province = provinces[zoneRandom.nextInt(provinces.length)];
//		String province = provinces[zoneRandom.nextInt(10)];
		play.setZoneName(getZoneCity(province));
		play.setZoneProvince(province);
		
		setVideoPlayDate(play);
		
		setVideoPlayLength(play);
		
		play.setWorkstationPageUrl("www.adsit.cn/fdksjlfd/fsdfsd/fdsfjfksdjflkdsjlfsjdlj");
		synchronized (obj) {
			playCount++;
		}
//		return JsonConvertedUtil.objectToJsonString(play);
		return play.toString();
	}
	
	private static Random dateRandom = new Random();
	private static synchronized void setVideoPlayDate(MyVideoPlay play) throws Exception{
		int dateTime = 24 * 60 * 60 * 1000;
		
		String date = DateUtil.getCurrDate();
		Date currSimpleDate = DateUtil.parseDate(date);
		long timems = currSimpleDate.getTime();
		timems += dateRandom.nextInt(dateTime);
		
		Date tmpDate = new Date(timems);
		
		play.setVideoDemandDay(DateUtil.formateDate(tmpDate));
		play.setVideoDemandTime(DateUtil.formateTime(tmpDate));
		play.setVideoDemandHour(DateUtil.getHourOfDay(tmpDate));
	}
	
	private static Random playRandom = new Random();
	private static Random playLengthRandom = new Random();
	private static synchronized void setVideoPlayLength(MyVideoPlay play){
		int ran = getRandom(playRandom, 20);
		if(ran<10){
			play.setVideoPlayLength((long)getRandom(playLengthRandom, 5000));
			play.setIsPlay(MyVideoPlay.IS_PLAY_YES);
			play.setIsValidPlay(MyVideoPlay.IS_VALID_PLAY_NO);
			bofang++;
			if(ran<6){
				play.setVideoPlayLength((long)getRandom(playLengthRandom, 10000)+5000L);
				play.setIsPlay(MyVideoPlay.IS_VALID_PLAY_YES);
				youxiao++;
			}
		}else{
			play.setVideoPlayLength(0L);
			play.setIsPlay(MyVideoPlay.IS_PLAY_NO);
			play.setIsValidPlay(MyVideoPlay.IS_VALID_PLAY_NO);
		}
	}
	
	private static String genVideoDemandId(){
		long currTime = System.currentTimeMillis()/1000;
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String hex = Long.toHexString(currTime);
		return uuid+hex;
	}
	
	private static String genUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	private static void genFile(List<String> contentList, String directory,String fileName) throws Exception{
		FileUtil.makeDirectory(directory);
		FileWriter writer = new FileWriter(fileName,true);
		for(String content:contentList){
			writer.append(content);
		}
		writer.flush();
		writer.close();
	}
	
	private static int getRandom(Random random,int radis){
		int r = random.nextInt(radis);
		while(r<=0){
			r = random.nextInt(radis);
		}
		return r;
	}
	
	private static String genAllKindsId(String prefix, Random random, int n){
		String userId = prefix+random.nextInt(n);
		return userId;
	}
	
	private static Map<String,List<String>> genZone(String filePath) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"),1024000);
		String line = null;
		Map<String,List<String>> zoneMap = new HashMap<String,List<String>>();
		try {
			while((line=br.readLine())!=null){
				if(line==null || line.trim().length()==0)
					continue;
				line = line.trim();
				String[] zones = line.split(",");
				boolean exist = zoneMap.containsKey(zones[0]);
				if(exist){
					List<String> citys = zoneMap.get(zones[0]);
					if(citys==null)
						citys = new ArrayList<String>();
					citys.add(zones[1]);
				}else{
					List<String> citys = new ArrayList<String>();
					citys.add(zones[1]);
					zoneMap.put(zones[0], citys);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			br.close();
		}
		return zoneMap;
	}
	
	
	private static String getZoneCity(String province){
		List<String> citys = zoneMap.get(province);
//		int size = citys.size()>3?3:citys.size();
//		String city = citys.get(random4.nextInt(size));
		String city = citys.get(random4.nextInt(citys.size()));
		return city;
	}
}
