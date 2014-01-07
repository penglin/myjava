package file.pojo;

import hadoop.report.pojo.AdPosZoneSegmentGather;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.adsit.common.util.JsonConvertedUtil;
import cn.adsit.iva.common.pojo.LifePojo;

/**
 * VideoPlay generated by MyEclipse - Hibernate Tools
 */

public class MyVideoPlay extends LifePojo
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = 1L;

	private static final String SPLIT_STRING = new String(new char[]{1});;
	
	/**
	 * 是否是有效点播： 否
	 */
	public final static int IS_VALID_PLAY_NO = 0;

	/**
	 * 是否是有效点播： 是
	 */
	public final static int IS_VALID_PLAY_YES = 1;

	/**
	 * 是否点播： 否
	 */
	public final static int IS_PLAY_NO = 0;

	/**
	 * 是否点播： 是
	 */
	public final static int IS_PLAY_YES = 1;

	// Fields

	private String videoPlayId;

	private String videoDemandId;

	private String userId;
	
	private String orderId;
	
	private String advertiserVideoId; //广告id---创意

	private String publishVideoId;
	
	private String workstationId;// 工作站ID

	private String contentClassId;// 工作站栏目ID
	
	private String adPosId;

	private String videoDemandDay;// 视频请求日期(年月日)

	private String videoDemandTime;
	
	private Integer videoDemandHour;

	private String userIp;

	private String zoneName;// 客户端隶属地域名称

	private String zoneProvince;// 客户端隶属地域省名称

	private Long videoPlayLength;

	private Integer isValidPlay;

	private Integer isPlay;

	private String workstationPageUrl;

	
	
	// Constructors

	@Override
	public String toString() {
		Field[] fields = this.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for(Field field : fields){
			String fieldName = field.getName();
			int modifier = field.getModifiers();
			boolean flag = Modifier.isStatic(modifier);
			if(flag)
				continue;
			
			String methodName = "get"+firstStringToUpperCase(fieldName);
			try {
				Method  method = this.getClass().getDeclaredMethod(methodName, new Class[]{});
				Object obj = method.invoke(this, new Object[]{});
				if(obj==null)
					obj = "";
				sb.append(SPLIT_STRING);
				sb.append(obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		sb.deleteCharAt(0);
		sb.append(" ");
		return sb.toString();
	}

	private String firstStringToUpperCase(String string){
		if(string==null||string.trim().isEmpty())
			return string;
			
		String c = string.substring(0, 1);
		if(string.length()==1)
			return c.toUpperCase();
		return c.toUpperCase() + string.substring(1);
	}
	
	/**
	 * default constructor
	 */
	public MyVideoPlay()
	{
	}
	
	public MyVideoPlay(String valueString){
		String[] splits = valueString.split(SPLIT_STRING);
		Field[] fields = this.getClass().getDeclaredFields();
		int i = 0;
		for(Field field : fields){
			String fieldName = field.getName();
			int modifier = field.getModifiers();
			boolean flag = Modifier.isStatic(modifier);
			if(flag)
				continue;
			
			String value = splits[i++];
			String methodName = "set"+firstStringToUpperCase(fieldName);
			
			try {
//				field.getType()
				Method  method = this.getClass().getDeclaredMethod(methodName, new Class[]{field.getType()});
				if(field.getType().equals(Long.class)){
					method.invoke(this, Long.parseLong(value));
				}else if(field.getType().equals(Integer.class)){
					method.invoke(this, Integer.parseInt(value));
				}else{
					method.invoke(this, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public MyVideoPlay(String[] splits){
		int i = 0;
		this.setVideoPlayId(splits[i++]);

		this.setVideoDemandId(splits[i++]);

		this.setUserId(splits[i++]);
		
		this.setOrderId(splits[i++]);
		
		this.setAdvertiserVideoId(splits[i++]); //广告id---创意

		this.setPublishVideoId(splits[i++]);
		
		this.setWorkstationId(splits[i++]);// 工作站ID

		this.setContentClassId(splits[i++]);// 工作站栏目ID
		
		this.setAdPosId(splits[i++]);

		this.setVideoDemandDay(splits[i++]);// 视频请求日期(年月日)

		this.setVideoDemandTime(splits[i++]);
		
		this.setVideoDemandHour(Integer.parseInt(splits[i++]));

		this.setUserIp(splits[i++]);

		this.setZoneName(splits[i++]);// 客户端隶属地域名称

		this.setZoneProvince(splits[i++]);// 客户端隶属地域省名称

		this.setVideoPlayLength(Long.parseLong(splits[i++]));

		this.setIsValidPlay(Integer.parseInt(splits[i++]));

		this.setIsPlay(Integer.parseInt(splits[i++]));

		this.setWorkstationPageUrl(splits[i++]);
	}
	
	public static void main(String[] args) throws Exception {
		String[] adPosZoneSegmentKeys = new String[]{"orderId","advertiserVideoId","workstationId","adPosId","zoneProvince","zoneName","videoDemandDay","videoDemandHour"};

		Map<String, AdPosZoneSegmentGather> adPosZoneSegmentMap = new HashMap<String, AdPosZoneSegmentGather>();
		String ff = "6522a82d52c840b496a47bc3cd2d266ad09ccc8c735e4cb79c8cb549994f29d64f700af2aaaaaaaaaaaaaaa_visitor_id_24984847campaign_id_campaign_id_26campaign_id_campaign_id_261campaign_id_campaign_id_264workstation_id_workstation_id4contentClassIdcontentClassIdworkstation_id_workstation_id4ad_pos2012年03月26日2012年03月26日 07:28:267192.168.0.164无法定位陕西省000www.adsit.cn/fdksjlfd/fsdfsd/fdsfjfksdjflkdsjlfsjdlj ";
		long time = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			MyVideoPlay play = new MyVideoPlay(ff.split(SPLIT_STRING));
//			MyVideoPlay play = new MyVideoPlay(ff);
//			MyVideoPlay play = new MyVideoPlay();
//			JSONObject jsonObject = JSONObject.fromObject(p);
			StringBuffer mapKey = new StringBuffer();
			mapKey.append(play.getOrderId());
			mapKey.append("$");
			mapKey.append(play.getAdvertiserVideoId());
			mapKey.append("$");
			mapKey.append(play.getWorkstationId());
			mapKey.append("$");
			mapKey.append(play.getAdPosId());
			mapKey.append("$");
			mapKey.append(play.getZoneProvince());
			mapKey.append("$");
			mapKey.append(play.getZoneName());
			mapKey.append("$");
			mapKey.append(play.getVideoDemandDay());
			mapKey.append("$");
			mapKey.append(play.getVideoDemandHour());
			mapKey.append("$");
			AdPosZoneSegmentGather gather = adPosZoneSegmentMap.get(mapKey);
			if (gather == null) {
				gather = new AdPosZoneSegmentGather();
				gather.setCampaignId(play.getOrderId());
				gather.setCampaignTrackId(play.getAdvertiserVideoId());
				gather.setWorkstationId(play.getWorkstationId());
				gather.setAdPosId(play.getAdPosId());
				gather.setZoneProvince(play.getZoneProvince());
				gather.setZoneCity(play.getZoneName());
				gather.setGatherDate(play.getVideoDemandDay());
				gather.setGatherHour(play.getVideoDemandHour()+"");
				adPosZoneSegmentMap.put(mapKey.toString(), gather);
			} else {
				gather.setDemandCount(gather.getDemandCount() + 1L);
			}
		}
		System.out.println(System.currentTimeMillis() - time);
	}
	
	private static void setReportGatherUseJSON(AdPosZoneSegmentGather gather, JSONObject jsonObject) throws Exception{
		gather.setCampaignId(jsonObject.getString("orderId"));
		gather.setCampaignTrackId(jsonObject.getString("advertiserVideoId"));
		gather.setWorkstationId(jsonObject.getString("workstationId"));
		gather.setAdPosId(jsonObject.getString("adPosId"));
		gather.setZoneProvince(jsonObject.getString("zoneProvince"));
		gather.setZoneCity(jsonObject.getString("zoneName"));
		gather.setGatherDate(jsonObject.getString("videoDemandDay"));
		gather.setGatherHour(jsonObject.getString("videoDemandHour"));

		gather.setDemandCount(1L);
		gather.setDemandUniqueCount(1L);
	}

	public static String getVideoPlayMapKeyUseJSON(JSONObject jsonObject, String[] keys) {
		StringBuffer sb = new StringBuffer();
		for(String key : keys){
			sb.append(jsonObject.getString(key));
			sb.append("$");
		}
		return sb.toString();
	}
	
	public String getVideoPlayId() {
		return videoPlayId;
	}

	public void setVideoPlayId(String videoPlayId) {
		this.videoPlayId = videoPlayId;
	}

	public String getVideoDemandId() {
		return videoDemandId;
	}

	public void setVideoDemandId(String videoDemandId) {
		this.videoDemandId = videoDemandId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWorkstationId() {
		return workstationId;
	}

	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}

	public String getContentClassId() {
		return contentClassId;
	}

	public void setContentClassId(String contentClassId) {
		this.contentClassId = contentClassId;
	}

	public String getVideoDemandDay() {
		return videoDemandDay;
	}

	public void setVideoDemandDay(String videoDemandDay) {
		this.videoDemandDay = videoDemandDay;
	}

	public String getPublishVideoId() {
		return publishVideoId;
	}

	public void setPublishVideoId(String publishVideoId) {
		this.publishVideoId = publishVideoId;
	}

	public String getAdvertiserVideoId() {
		return advertiserVideoId;
	}

	public void setAdvertiserVideoId(String advertiserVideoId) {
		this.advertiserVideoId = advertiserVideoId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneProvince() {
		return zoneProvince;
	}

	public void setZoneProvince(String zoneProvince) {
		this.zoneProvince = zoneProvince;
	}

	public Long getVideoPlayLength() {
		return videoPlayLength;
	}

	public void setVideoPlayLength(Long videoPlayLength) {
		this.videoPlayLength = videoPlayLength;
	}

	public Integer getIsValidPlay() {
		return isValidPlay;
	}

	public void setIsValidPlay(Integer isValidPlay) {
		this.isValidPlay = isValidPlay;
	}

	public Integer getIsPlay() {
		return isPlay;
	}

	public void setIsPlay(Integer isPlay) {
		this.isPlay = isPlay;
	}

	public String getVideoDemandTime() {
		return videoDemandTime;
	}

	public void setVideoDemandTime(String videoDemandTime) {
		this.videoDemandTime = videoDemandTime;
	}

	public Integer getVideoDemandHour() {
		return videoDemandHour;
	}

	public void setVideoDemandHour(Integer videoDemandHour) {
		this.videoDemandHour = videoDemandHour;
	}

	public String getWorkstationPageUrl() {
		return workstationPageUrl;
	}

	public void setWorkstationPageUrl(String workstationPageUrl) {
		this.workstationPageUrl = workstationPageUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAdPosId() {
		return adPosId;
	}

	public void setAdPosId(String adPosId) {
		this.adPosId = adPosId;
	}

	// Property accessors

}