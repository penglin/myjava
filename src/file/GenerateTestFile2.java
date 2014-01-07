package file;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import cn.adsit.common.util.BeanUtil;
import cn.adsit.common.util.DateUtil;
import cn.adsit.common.util.FileUtil;
import cn.adsit.iva.common.pojo.LifePojo;
import cn.adsit.iva.preroll.pojo.ValueAdd;
import cn.adsit.iva.preroll.pojo.VideoClick;
import cn.adsit.iva.preroll.pojo.VideoDemand;
import cn.adsit.iva.preroll.pojo.VideoPlay;

/**
 * 生成测试文件
 * @author 彭霖
 *
 */
public class GenerateTestFile2 extends Thread{
	private static Random random = new Random();
	private static Random random2 = new Random();
	private static Random random3 = new Random();
	private static Random random4 = new Random();
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
	
	private static String playDirectory = "/jinzhunpai/MonitorPlayWorkspace/";
	private static String playPrefix = "PlayLog";
	private static String playSuffix = "log";
	
	private static String clickDirectory = "/jinzhunpai/VideoClickWorkspace/";
	private static String clickPrefix = "VideoClick";
	private static String clickSuffix = "xml";
	
	private static String vasDirectory = "/jinzhunpai/ValueAddWorkspace/";
	private static String vasPrefix = "ValueAdd";
	private static String vasSuffix = "xml";
	
	private static int demandCount = 0;
	private static int pipeiCount = 0;
	private static int playCount = 0;
	private static int clickCount = 0 ;
	private static int vasCount = 0 ;
	
	private static List<Thread> threadList = new ArrayList<Thread>();
	
	private static Object obj = new Object();
	private static Object obj2 = new Object();
	private static Object obj3 = new Object();
	private static Object obj4 = new Object();
	private static Object obj5 = new Object();
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*LineNumberReader reader = new LineNumberReader(new FileReader("videoDemand.xml"));
		String line = null;
		while((line=reader.readLine())!=null){
			if(line.isEmpty())
				continue;
//			VideoClick videoClick = (VideoClick) BeanUtil.getObjectFromXmlDescriptor(line, "GBK");
			VideoDemand videoClick = (VideoDemand) BeanUtil.getObjectFromXmlDescriptor(line, "GBK");
			System.out.println(videoClick);
			String xml = BeanUtil.getBeanXmlDescriptor(videoClick, "GBK");
			System.out.println(xml);
		}
		
		
		System.out.println(genVideoDemandId());
		
		System.out.println("0000000034c5f45a0134c68495702edf4f0be898".length());
		System.out.println("76f31069ffde4c46afa71dfe9126fd5e134d06e2c37".length());
		System.out.println(PublishUtil.getHourTime(genVideoDemandId()));*/
		for(int i=0;i<20;i++){
			Thread t = new GenerateTestFile();
			threadList.add(t);
			t.start();
		}
		validate();
		System.out.println("生成demand数据："+demandCount);
		System.out.println("生成匹配数据："+pipeiCount);
		System.out.println("生成监播(play)数据："+playCount);
		System.out.println("生成Click数据："+clickCount);
		System.out.println("生成Vas数据："+vasCount);
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
			System.out.println("生成demand数据："+demandCount);
			System.out.println("生成匹配数据："+pipeiCount);
			System.out.println("生成监播(play)数据："+playCount);
			System.out.println("生成Click数据："+clickCount);
			System.out.println("生成Vas数据："+vasCount);
			System.out.println("-------------------------------");
			// 当前线程休息2秒钟
			try
			{
				Thread.sleep(60 * 1000);
			}
			catch (InterruptedException e)
			{
			}
		}
	}
	
	@Override
	public void run() {
		try {
			List<String> demandContentList = new ArrayList<String>();
			List<String> playTrackContentList = new ArrayList<String>();
			List<String> clickContentList = new ArrayList<String>();
			List<String> vasContentList = new ArrayList<String>();
			for(int j=0;j<1000;j++){
				String uuid = genUUID();
				demandContentList.clear();
				playTrackContentList.clear();
				clickContentList.clear();
				vasContentList.clear();
				
				for(int i=0;i<100;i++){
					String publishVideoId = publishVideoIds[getRandom(random3,publishVideoIds.length)];
					VideoDemand demand = genVideoDemand(publishVideoId);
					VideoPlay play = genVideoPlay(demand);
					
					List<VideoPlay> playList = genVideoPlayList(demand,publishVideoId,play.getAdvertiserVideoId());
					VideoClick click = genVideoClick(demand, publishVideoId, play.getAdvertiserVideoId());
					ValueAdd add = genValueAdd(demand, publishVideoId, play.getAdvertiserVideoId());
					
					//生成请求匹配数据
					String demandXml = BeanUtil.getBeanXmlDescriptor(demand, "GBK");
					String playXml = BeanUtil.getBeanXmlDescriptor(play, "GBK");
					demandContentList.add(demandXml+playXml+"\n");
					//生成监播数据
					for(VideoPlay p : playList){
						playTrackContentList.add( BeanUtil.getBeanXmlDescriptor(p, "GBK")+"\n");
					}
					//点击数据
					String clickXml = BeanUtil.getBeanXmlDescriptor(click, "GBK");
					clickContentList.add(clickXml+"\n");
					//增值数据
					String vasXml = BeanUtil.getBeanXmlDescriptor(add, "GBK");
					vasContentList.add(vasXml+"\n");
				}
				
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				
				String subDirectory = DateUtil.getCurrSimpleTime().substring(0, 12);
				String directory = demandDirectory+subDirectory;
				String fileName = directory + "/"+ demandPrefix+"_"+uuid+"."+demandSuffix;
				genFile(demandContentList, directory, fileName);
				
				directory = playDirectory+subDirectory;
				fileName = directory + "/"+ playPrefix+"_"+uuid+"."+playSuffix;
				genFile(playTrackContentList, directory, fileName);
				
				directory = clickDirectory+subDirectory;
				fileName = directory + "/"+ clickPrefix+"_"+uuid+"."+clickSuffix;
				genFile(clickContentList, directory, fileName);
				
				directory = vasDirectory+subDirectory;
				fileName = directory + "/"+ vasPrefix+"_"+uuid+"."+vasSuffix;
				genFile(vasContentList, directory, fileName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static VideoDemand genVideoDemand(String publishVideoId){
		VideoDemand demand = new VideoDemand();
		demand.setVideoDemandId(genVideoDemandId());
		String[] ids = ad[getRandom(random,ad.length)].split(",");
		String workstationId = ids[0];
		demand.setWorkstationId(workstationId);
		demand.setWorkstationPageId("");
		demand.putExtendAttribute("ContentClassId", ids[1]);
		demand.setUserIp("192.168.0.164");
		Date date = new Date();
		demand.setVideoDemandTime(DateUtil.formateTime(date));
		demand.setVideoDemandYear(DateUtil.getYear(date));
		demand.setVideoDemandMonth(DateUtil.getYearMonth(date));
		demand.setVideoDemandWeek(DateUtil.getYearWeek(date));
		demand.setVideoDemandDay(DateUtil.formateDate(date));
		demand.setGatherState(VideoDemand.GATHER_STATE_WAIT);
		demand.setLifeState(LifePojo.LIFE_STATE_VALID);
		
		demand.putExtendAttribute("PublishVideoId", publishVideoId);
		synchronized (obj) {
			demandCount++;
		}
		return demand;
	}
	
	/**
	 * 生成匹配数据
	 * @param demand
	 * @return
	 */
	private static VideoPlay genVideoPlay(VideoDemand demand){
		VideoPlay play = new VideoPlay();
		play.setVideoPlayId(genUUID());
		play.setVideoDemandId(demand.getVideoDemandId());
		play.setWorkstationId(demand.getWorkstationId());
		play.setContentClassId(demand.getExtendAttribute("ContentClassId").toString());
		play.setWorkstationPageId(demand.getWorkstationPageId());
		play.setWorkstationPageUrl("www.adsit.cn");
		Date date = new Date();
		play.setVideoDemandDay(DateUtil.formateDate(date));
		play.setPublishVideoId(demand.getExtendAttribute("PublishVideoId").toString());
		play.setAdvertiserVideoId(videoIds[getRandom(random4, videoIds.length)]);
		play.setVideoMatchType(VideoPlay.VIDEO_MATCH_PLAY_TYPE);
		play.setUserIp(demand.getUserIp());
		play.setZoneName("海淀区");
		play.setZoneProvince("北京市");
		play.setVideoPlayBeginTime(DateUtil.getCurrTime());
		play.setVideoPlayEndTime(DateUtil.getCurrTime());
		play.setVideoDuration(0L);
		play.setVideoPlayLength(0L);
		play.setVideoPlayScale(0);
		play.setPayMoney(0);
		play.setIsPlay(VideoPlay.IS_PLAY_NO);
		play.setIsValidPlay(VideoPlay.IS_VALID_PLAY_NO);
		play.setVideoPlayLength(0L);
		play.setVideoDuration(0L);
		play.setVideoPlayScale(0);
		play.setPayMoney(0);
		play.setIsValidBoard(VideoPlay.IS_VALID_BOARD_NO);
		play.setVideoDemandTime(demand.getVideoDemandTime());
		play.setGatherState(VideoPlay.GATHER_STATE_WAIT);
		synchronized (obj2) {
			pipeiCount++;
		}
		return play;
	}
	
	/**
	 * 生成监播数据
	 * @param demand
	 * @return
	 */
	private static List<VideoPlay> genVideoPlayList(VideoDemand demand,String publishVideoId,String videoId){
		List<VideoPlay> list = new ArrayList<VideoPlay>();
		long currTime = System.currentTimeMillis() - 5*1000;
		Date startDate = new Date(currTime);
		for(int i=0;i<2;i++){
			long tmpTime = System.currentTimeMillis() - (4-i)*1000;
			Date date = new Date(tmpTime);
			VideoPlay play = new VideoPlay();
			play.setVideoPlayId(genUUID());
			play.setVideoDemandId(demand.getVideoDemandId());
			play.setWorkstationId(demand.getWorkstationId());
			play.setContentClassId(demand.getExtendAttribute("ContentClassId").toString());
			play.setWorkstationPageId(demand.getWorkstationPageId());
			play.setVideoDemandDay(DateUtil.formateDate(date));
			play.setPublishVideoId(publishVideoId);
			play.setAdvertiserVideoId(videoId);
			play.setVideoMatchType(VideoPlay.VIDEO_MATCH_PLAY_TYPE);
			play.setUserIp(demand.getUserIp());
			play.setZoneName("海淀区");
			play.setZoneProvince("北京市");
			play.setVideoPlayBeginTime(DateUtil.formateTime(startDate));
			play.setVideoPlayEndTime(DateUtil.formateTime(date));
			play.setIsPlay(VideoPlay.IS_PLAY_NO);
			play.setIsValidPlay(VideoPlay.IS_VALID_PLAY_YES);
			play.setVideoPlayLength(30000L);
			play.setVideoDuration(tmpTime - currTime);
			play.setVideoPlayScale(0);
			play.setPayMoney(0);
			play.setIsValidBoard(VideoPlay.IS_VALID_BOARD_NO);
			play.setVideoDemandTime(demand.getVideoDemandTime());
			play.setGatherState(VideoPlay.GATHER_STATE_WAIT);
			synchronized (obj3) {
				playCount++;
			}
			list.add(play);
		}
		return list;
	}
	
	private static VideoClick genVideoClick(VideoDemand demand,String publishVideoId,String videoId){
		VideoClick click = new VideoClick();
		click.setVideoClickId(genUUID());
		click.setVideoDemandId(demand.getVideoDemandId());
		click.setPublishVideoId(publishVideoId);
		click.setVideoId(videoId);
		click.setUserIp(demand.getUserIp());
		click.setWorkstationId(demand.getWorkstationId());
		click.setVideoClickTime(demand.getVideoDemandTime());
		click.setVideoClickDate(demand.getVideoDemandDay());
		click.setVideoClickYear(demand.getVideoDemandYear());
		click.setVideoClickMonth(demand.getVideoDemandMonth());
		click.setVideoClickWeek(demand.getVideoDemandWeek());
		click.setVideoClickSeason("2012年第1季度");
		click.setActionState(VideoClick.ACTION_STATE_NORMAL);
		click.setVideoMatchType(VideoClick.VIDEO_MATCH_BOARD_TYPE);
		click.setVideoClickType(VideoClick.VIDEO_CLICK_TYPE_BAIDU);
		synchronized (obj4) {
			clickCount++;
		}
		return click;
	}
	
	private static ValueAdd genValueAdd(VideoDemand demand,String publishVideoId,String videoId){
		ValueAdd add = new ValueAdd();
		add.setValueAddId(genUUID());
		add.setVideoDemandId(demand.getVideoDemandId());
		add.setVideoId(videoId);
		add.setPublishVideoId(publishVideoId);
		add.setWorkstationId(demand.getWorkstationId());
		add.setWorkstationPageId(demand.getWorkstationPageId());
		add.setUserIp(demand.getUserIp());
		add.setVaTime(demand.getVideoDemandTime());
		add.setVaDate(demand.getVideoDemandDay());
		add.setVaYearWeek(demand.getVideoDemandWeek());
		add.setVaYear(demand.getVideoDemandYear());
		add.setVaType(ValueAdd.VALUE_ADD_TYPE_DOWNLOAD);
		int r = getRandom(random2, 4);
		add.setActionImplNumber(r==actionImplNumbers.length?null:actionImplNumbers[r]);
		synchronized (obj5) {
			vasCount++;
		}
		return add;
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
//			writer.write(content);
			writer.append(content);
		}
		writer.flush();
		writer.close();
	}
	
	private static int getRandom(Random random,int radis){
		int r = random.nextInt(radis);
		while(r<0){
			r = random.nextInt(radis);
		}
		return r;
	}
}
