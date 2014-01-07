package cn.adsit.iva.preroll.pojo;

import java.net.URLDecoder;
import java.net.URLEncoder;

import cn.adsit.iva.common.pojo.LifePojo;

/**
 * VideoPlay generated by MyEclipse - Hibernate Tools
 */

public class VideoPlay extends LifePojo
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = 1L;

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

	/**
	 * 视频展现确认:否
	 */
	public final static int IS_EXHIBIT_NO = 0;

	/**
	 * 视频展现确认:否
	 */
	public final static int IS_EXHIBIT_YES = 1;

	/**
	 * 是否生成明细文件 ：否
	 */
	public final static int GENERATE_FILE_NO = 1;

	/**
	 * 是否生成明细文件 ：是
	 */
	public final static int GENERATE_FILE_YES = 2;

	/**
	 * 是否生成明细文件 ：失败
	 */
	public static final int GENERATE_FILE_LOST = 4;

	/**
	 * 汇总状态:等待汇总
	 */
	public static final int GATHER_STATE_WAIT = 1;

	/**
	 * 汇总状态:汇总成功
	 */
	public static final int GATHER_STATE_SUCCESS = 2;

	/**
	 * 汇总状态:汇总失败
	 */
	public static final int GATHER_STATE_FAIL = 4;

	/**
	 * 地域分析状态:等待地域分析
	 */
	public static final int ZONE_ANALYZE_STATE_WAIT = 1;

	/**
	 * 地域分析状态:地域分析成功
	 */
	public static final int ZONE_ANALYZE_STATE_SUCCESS = 2;

	/**
	 * 地域分析状态:地域分析失败
	 */
	public static final int ZONE_ANALYZE_STATE_FAIL = 4;

	/**
	 * 时间段分析状态：等待
	 */
	public static final int SEGMENT_ANALYZE_STATE_WAIT = 1;

	/**
	 * 时间段分析状态：分析成功
	 */
	public static final int SEGMENT_ANALYZE_STATE_SUCCESS = 2;

	/**
	 * 时间段分析状态：分析失败
	 */
	public static final int SEGMENT_ANALYZE_STATE_FAIL = 4;

	/**
	 * 视频匹配类型常量：没有匹配到任何视频
	 */
	public static final int VIDEO_MATCH_NONE_TYPE = 0;

	/**
	 * 视频匹配类型常量：匹配到标版
	 */
	public static final int VIDEO_MATCH_BOARD_TYPE = 1;

	/**
	 * 视频匹配类型常量：匹配到播放
	 */
	public static final int VIDEO_MATCH_PLAY_TYPE = 2;

	/**
	 * 视频匹配类型常量：匹配到CPC标版
	 */
	public static final int VIDEO_MATCH_CPC_TYPE = 4;

	/**
	 * 视频匹配类型常量：匹配到看板
	 */
	public static final int VIDEO_MATCH_BOARD_EX_TYPE = 8;

	/**
	 * 视频匹配类型常量：固定匹配
	 */
	public static final int VIDEO_MATCH_FIXED_TYPE = 16;

	/**
	 * 播放器类型：浮出
	 */
	public static final int PLAYER_TYPE_RISE = 1;

	/**
	 * 播放器类型：嵌入
	 */
	public static final int PLAYER_TYPE_EMBED = 2;

	/**
	 * 播放器类型：贴片
	 */
	public static final int PLAYER_TYPE_AFFIX = 4;

	/**
	 * cpc请求否
	 */
	public static final int APPLY_CPC_NO = 0;

	/**
	 * cpc请求是
	 */
	public static final int APPLY_CPC_YES = 1;

	/**
	 * 是否匹配到看板：否
	 */
	public static final int APPLY_BOARD_NO = 0;

	/**
	 * 是否匹配到看板：是
	 */
	public static final int APPLY_BOARD_YES = 1;

	/**
	 * 是否有效看板：否
	 */
	public static final int IS_VALID_BOARD_NO = 0;

	/**
	 * 是否有效看板：是
	 */
	public static final int IS_VALID_BOARD_YES = 1;

	/**
	 * 是否溢出:否
	 */
	public static final int IS_OVERFLOW_NO = 0;

	/**
	 * 是否溢出:是
	 */
	public static final int IS_OVERFLOW_YES = 1;

	/**
	 * 多播匹配常量：匹配
	 */
	public static final int PACKAGE_MATCH_MATCHING = 0;

	/**
	 * 多播匹配常量：选择
	 */
	public static final int PACKAGE_MATCH_CHOOSING = 1;

	/**
	 * 多播匹配常量：未决
	 */
	public static final int PACKAGE_MATCH_PENDING = 2;

	// Fields

	private String videoPlayId;

	private String videoDemandId;

	private String userId;

	private String workstationId;// 工作站ID

	private String contentClassId;// 工作站栏目ID

	private String workstationPageId;// 工作站网页ID

	private String videoDemandDay;// 视频请求日期(年月日)

	private String publishVideoId;

	private String advertiserVideoId;

	private Integer videoMatchType;

	private String userIp;

	private String zoneName;// 客户端隶属地域名称

	private String zoneProvince;// 客户端隶属地域省名称

	private String videoPlayBeginTime;

	private String videoPlayEndTime;

	private Long videoDuration;

	private Long videoPlayLength;

	private Integer videoPlayScale;

	private Integer payMoney;

	private Integer isValidPlay;

	private Integer isPlay;

	private Integer isGenerateFile;// 是否生成文件

	private Integer gatherState;// 是否已经汇总

	private Integer zoneAnalyzeState; // 地域分析状态

	private Integer segmentAnalyzeState;

	private String videoDemandTime;

	private Integer videoDemandHour;

	private String workstationPageUrl;

	private Integer applyCPC;// 投放性质

	private Integer applyBoard; // 看板

	private Integer isValidBoard; // 有效看板

	private Integer playerType; // 播放器类型，包括:浮出(1或参数不存在)、嵌入(2)和贴片(4)

	private Integer isOverflow; // 是否播放溢出抹掉

	private Integer packageMatch; // 多播组匹配

	private Integer isExhibit; // 是否前端展现

	private String affixContentName; // 贴片广告内容名称

	private String ipOrganization; // IP隶属运营商和使用单位

	// Constructors

	/**
	 * default constructor
	 */
	public VideoPlay()
	{
	}

	// Property accessors

	/**
	 * @return the segmentAnalyzeState
	 */
	public Integer getSegmentAnalyzeState()
	{
		return segmentAnalyzeState;
	}

	/**
	 * @param segmentAnalyzeState the segmentAnalyzeState to set
	 */
	public void setSegmentAnalyzeState(Integer segmentAnalyzeState)
	{
		this.segmentAnalyzeState = segmentAnalyzeState;
	}

	public String getVideoPlayId()
	{
		return this.videoPlayId;
	}

	public void setVideoPlayId(String videoPlayId)
	{
		this.videoPlayId = videoPlayId;
	}

	public String getVideoDemandId()
	{
		return this.videoDemandId;
	}

	public void setVideoDemandId(String videoDemandId)
	{
		this.videoDemandId = videoDemandId;
	}

	public String getPublishVideoId()
	{
		return this.publishVideoId;
	}

	public void setPublishVideoId(String publishVideoId)
	{
		this.publishVideoId = publishVideoId;
	}

	public String getAdvertiserVideoId()
	{
		return this.advertiserVideoId;
	}

	public void setAdvertiserVideoId(String advertiserVideoId)
	{
		this.advertiserVideoId = advertiserVideoId;
	}

	public String getUserIp()
	{
		return this.userIp;
	}

	public String getZoneName()
	{
		return zoneName;
	}

	public void setZoneName(String zoneName)
	{
		this.zoneName = zoneName;
	}

	public String getZoneProvince()
	{
		return zoneProvince;
	}

	public void setZoneProvince(String zoneProvince)
	{
		this.zoneProvince = zoneProvince;
	}

	public void setUserIp(String userIp)
	{
		this.userIp = userIp;
	}

	public String getVideoPlayBeginTime()
	{
		return this.videoPlayBeginTime;
	}

	public void setVideoPlayBeginTime(String videoPlayBeginTime)
	{
		this.videoPlayBeginTime = videoPlayBeginTime;
	}

	public String getVideoPlayEndTime()
	{
		return this.videoPlayEndTime;
	}

	public void setVideoPlayEndTime(String videoPlayEndTime)
	{
		this.videoPlayEndTime = videoPlayEndTime;
	}

	public Long getVideoPlayLength()
	{
		return this.videoPlayLength;
	}

	public void setVideoPlayLength(Long videoPlayLength)
	{
		this.videoPlayLength = videoPlayLength;
	}

	public Integer getVideoPlayScale()
	{
		return this.videoPlayScale;
	}

	public void setVideoPlayScale(Integer videoPlayScale)
	{
		this.videoPlayScale = videoPlayScale;
	}

	public Integer getPayMoney()
	{
		return this.payMoney;
	}

	public void setPayMoney(Integer payMoney)
	{
		this.payMoney = payMoney;
	}

	public Integer getIsValidPlay()
	{
		return this.isValidPlay;
	}

	public void setIsValidPlay(Integer isValidPlay)
	{
		this.isValidPlay = isValidPlay;
	}

	public Integer getIsPlay()
	{
		return this.isPlay;
	}

	public void setIsPlay(Integer isPlay)
	{
		this.isPlay = isPlay;
	}

	public Long getVideoDuration()
	{
		return videoDuration;
	}

	public void setVideoDuration(Long videoDuration)
	{
		this.videoDuration = videoDuration;
	}

	public Integer getIsGenerateFile()
	{
		return isGenerateFile;
	}

	public void setIsGenerateFile(Integer isGenerateFile)
	{
		this.isGenerateFile = isGenerateFile;
	}

	public Integer getGatherState()
	{
		return gatherState;
	}

	public void setGatherState(Integer gatherState)
	{
		this.gatherState = gatherState;
	}

	public String getWorkstationId()
	{
		return workstationId;
	}

	public void setWorkstationId(String workstationId)
	{
		this.workstationId = workstationId;
	}

	public String getWorkstationPageId()
	{
		return workstationPageId;
	}

	public void setWorkstationPageId(String workstationPageId)
	{
		this.workstationPageId = workstationPageId;
	}

	public String getVideoDemandDay()
	{
		return videoDemandDay;
	}

	public void setVideoDemandDay(String videoDemandDay)
	{
		this.videoDemandDay = videoDemandDay;
	}

	public Integer getZoneAnalyzeState()
	{
		return zoneAnalyzeState;
	}

	public void setZoneAnalyzeState(Integer zoneAnalyzeState)
	{
		this.zoneAnalyzeState = zoneAnalyzeState;
	}

	public Integer getVideoDemandHour()
	{
		return videoDemandHour;
	}

	public String getVideoDemandTime()
	{
		return videoDemandTime;
	}

	public void setVideoDemandHour(Integer videoDemandHour)
	{
		this.videoDemandHour = videoDemandHour;
	}

	public void setVideoDemandTime(String videoDemandTime)
	{
		this.videoDemandTime = videoDemandTime;
	}

	public String getWorkstationPageUrl()
	{
		return workstationPageUrl;
	}

	public void setWorkstationPageUrl(String workstationPageUrl)
	{
		this.workstationPageUrl = workstationPageUrl;
	}

	public Integer getApplyCPC()
	{
		return applyCPC;
	}

	public void setApplyCPC(Integer applyCPC)
	{
		this.applyCPC = applyCPC;
	}

	public String getContentClassId()
	{
		return contentClassId;
	}

	public void setContentClassId(String contentClassId)
	{
		this.contentClassId = contentClassId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Integer getVideoMatchType()
	{
		return videoMatchType;
	}

	public void setVideoMatchType(Integer videoMatchType)
	{
		this.videoMatchType = videoMatchType;
	}

	public Integer getApplyBoard()
	{
		return applyBoard;
	}

	public void setApplyBoard(Integer applyBoard)
	{
		this.applyBoard = applyBoard;
	}

	public Integer getIsValidBoard()
	{
		return isValidBoard;
	}

	public void setIsValidBoard(Integer isValidBoard)
	{
		this.isValidBoard = isValidBoard;
	}

	public Integer getPlayerType()
	{
		return playerType;
	}

	public void setPlayerType(Integer playerType)
	{
		this.playerType = playerType;
	}

	public Integer getIsOverflow()
	{
		return isOverflow;
	}

	public void setIsOverflow(Integer isOverflow)
	{
		this.isOverflow = isOverflow;
	}

	public Integer getPackageMatch()
	{
		return packageMatch;
	}

	public void setPackageMatch(Integer packageMatch)
	{
		this.packageMatch = packageMatch;
	}

	public Integer getIsExhibit()
	{
		return isExhibit;
	}

	public void setIsExhibit(Integer isExhibit)
	{
		this.isExhibit = isExhibit;
	}

	public String getAffixContentName()
	{
		return affixContentName;
	}

	public void setAffixContentName(String affixContentName)
	{
		this.affixContentName = affixContentName;
	}

	public String getIpOrganization()
	{
		return ipOrganization;
	}

	public void setIpOrganization(String ipOrganization)
	{
		this.ipOrganization = ipOrganization;
	}

	/**
	 * 对象序列化为XML字符串
	 * 
	 * @return XML字符串
	 */
	public String toSerialXml() throws Exception
	{
		StringBuffer serialXml = new StringBuffer();
		serialXml.append("<cn.adsit.iva.preroll.pojo.VideoPlay>");
		serialXml.append("<videoPlayId>" + (this.videoPlayId == null ? "" : URLEncoder.encode(this.videoPlayId, "GBK")) + "</videoPlayId>");
		serialXml.append("<videoDemandId>" + (this.videoDemandId == null ? "" : URLEncoder.encode(this.videoDemandId, "GBK")) + "</videoDemandId>");
		serialXml.append("<workstationId>" + (this.workstationId == null ? "" : URLEncoder.encode(this.workstationId, "GBK")) + "</workstationId>");
		serialXml.append("<contentClassId>" + (this.contentClassId == null ? "" : URLEncoder.encode(this.contentClassId, "GBK")) + "</contentClassId>");
		serialXml.append("<workstationPageId>" + (this.workstationPageId == null ? "" : URLEncoder.encode(this.workstationPageId, "GBK"))
				+ "</workstationPageId>");
		serialXml.append("<videoDemandDay>" + (this.videoDemandDay == null ? "" : URLEncoder.encode(this.videoDemandDay, "GBK")) + "</videoDemandDay>");
		serialXml.append("<publishVideoId>" + (this.publishVideoId == null ? "" : URLEncoder.encode(this.publishVideoId, "GBK")) + "</publishVideoId>");
		serialXml.append("<advertiserVideoId>" + (this.advertiserVideoId == null ? "" : URLEncoder.encode(this.advertiserVideoId, "GBK"))
				+ "</advertiserVideoId>");
		serialXml.append("<userIp>" + (this.userIp == null ? "" : URLEncoder.encode(this.userIp, "GBK")) + "</userIp>");
		serialXml.append("<zoneName>" + (this.zoneName == null ? "" : URLEncoder.encode(this.zoneName, "GBK")) + "</zoneName>");
		serialXml.append("<zoneProvince>" + (this.zoneProvince == null ? "" : URLEncoder.encode(this.zoneProvince, "GBK")) + "</zoneProvince>");
		serialXml.append("<videoPlayBeginTime>" + (this.videoPlayBeginTime == null ? "" : URLEncoder.encode(this.videoPlayBeginTime, "GBK"))
				+ "</videoPlayBeginTime>");
		serialXml.append("<videoPlayEndTime>" + (this.videoPlayEndTime == null ? "" : URLEncoder.encode(this.videoPlayEndTime, "GBK")) + "</videoPlayEndTime>");
		serialXml.append("<videoDuration>" + (this.videoDuration == null ? "" : this.videoDuration) + "</videoDuration>");
		serialXml.append("<videoPlayLength>" + (this.videoPlayLength == null ? "" : this.videoPlayLength) + "</videoPlayLength>");
		serialXml.append("<videoPlayScale>" + (this.videoPlayScale == null ? "" : this.videoPlayScale) + "</videoPlayScale>");
		serialXml.append("<payMoney>" + (this.payMoney == null ? "" : this.payMoney) + "</payMoney>");
		serialXml.append("<isValidPlay>" + (this.isValidPlay == null ? "" : this.isValidPlay) + "</isValidPlay>");
		serialXml.append("<isPlay>" + (this.isPlay == null ? "" : this.isPlay) + "</isPlay>");
		serialXml.append("<isGenerateFile>" + (this.isGenerateFile == null ? "" : this.isGenerateFile) + "</isGenerateFile>");
		serialXml.append("<gatherState>" + (this.gatherState == null ? "" : this.gatherState) + "</gatherState>");
		serialXml.append("<zoneAnalyzeState>" + (this.zoneAnalyzeState == null ? "" : this.zoneAnalyzeState) + "</zoneAnalyzeState>");
		serialXml.append("<segmentAnalyzeState>" + (this.segmentAnalyzeState == null ? "" : this.segmentAnalyzeState) + "</segmentAnalyzeState>");
		serialXml.append("<videoDemandTime>" + (this.videoDemandTime == null ? "" : URLEncoder.encode(this.videoDemandTime, "GBK")) + "</videoDemandTime>");
		serialXml.append("<videoDemandHour>" + (this.videoDemandHour == null ? 0 : this.videoDemandHour) + "</videoDemandHour>");
		serialXml.append("<workstationPageUrl>" + (this.workstationPageUrl == null ? "" : URLEncoder.encode(this.workstationPageUrl, "GBK"))
				+ "</workstationPageUrl>");
		serialXml.append("<applyCPC>" + (this.applyCPC == null ? 0 : this.applyCPC) + "</applyCPC>");
		serialXml.append("<userId>" + (this.userId == null ? "" : URLEncoder.encode(this.userId, "GBK")) + "</userId>");
		serialXml.append("<videoMatchType>" + (this.videoMatchType == null ? 0 : this.videoMatchType) + "</videoMatchType>");
		serialXml.append("<applyBoard>" + (this.applyBoard == null ? 0 : this.applyBoard) + "</applyBoard>");
		serialXml.append("<playerType>" + (this.playerType == null ? PLAYER_TYPE_RISE : this.playerType) + "</playerType>");
		serialXml.append("<isOverflow>" + (this.isOverflow == null || this.isOverflow == 0 ? 0 : 1) + "</isOverflow>");
		serialXml.append("<packageMatch>" + (this.packageMatch == null ? "" : this.packageMatch) + "</packageMatch>");
		serialXml.append("<isExhibit>" + (this.isExhibit == null ? "" : this.isExhibit) + "</isExhibit>");
		serialXml.append("<affixContentName>" + (this.affixContentName == null ? "" : URLEncoder.encode(this.affixContentName, "GBK")) + "</affixContentName>");
		serialXml.append("<ipOrganization>" + (this.ipOrganization == null ? "" : URLEncoder.encode(this.ipOrganization, "GBK")) + "</ipOrganization>");
		// 父类LifePojo内部属性序列
		super.innerSerialXml(serialXml);
		// 返回
		serialXml.append("</cn.adsit.iva.preroll.pojo.VideoPlay>");
		return serialXml.toString();
	}

	/**
	 * 从序列化XML字符串还原对象
	 * 
	 * @param serialXml XML字符串
	 */
	public void fromSerialXml(String serialXml) throws Exception
	{
		String node = null;
		if (serialXml.indexOf("<videoPlayId>") != -1 && serialXml.indexOf("</videoPlayId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoPlayId>") + 13, serialXml.indexOf("</videoPlayId>"));
			if (node.length() != 0)
				this.videoPlayId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoDemandId>") != -1 && serialXml.indexOf("</videoDemandId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoDemandId>") + 15, serialXml.indexOf("</videoDemandId>"));
			if (node.length() != 0)
				this.videoDemandId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<workstationId>") != -1 && serialXml.indexOf("</workstationId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<workstationId>") + 15, serialXml.indexOf("</workstationId>"));
			if (node.length() != 0)
				this.workstationId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<contentClassId>") != -1 && serialXml.indexOf("</contentClassId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<contentClassId>") + 16, serialXml.indexOf("</contentClassId>"));
			if (node.length() != 0)
				this.contentClassId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<workstationPageId>") != -1 && serialXml.indexOf("</workstationPageId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<workstationPageId>") + 19, serialXml.indexOf("</workstationPageId>"));
			if (node.length() != 0)
				this.workstationPageId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoDemandDay>") != -1 && serialXml.indexOf("</videoDemandDay>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoDemandDay>") + 16, serialXml.indexOf("</videoDemandDay>"));
			if (node.length() != 0)
				this.videoDemandDay = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<publishVideoId>") != -1 && serialXml.indexOf("</publishVideoId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<publishVideoId>") + 16, serialXml.indexOf("</publishVideoId>"));
			if (node.length() != 0)
				this.publishVideoId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<advertiserVideoId>") != -1 && serialXml.indexOf("</advertiserVideoId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<advertiserVideoId>") + 19, serialXml.indexOf("</advertiserVideoId>"));
			if (node.length() != 0)
				this.advertiserVideoId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<userIp>") != -1 && serialXml.indexOf("</userIp>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<userIp>") + 8, serialXml.indexOf("</userIp>"));
			if (node.length() != 0)
				this.userIp = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<zoneName>") != -1 && serialXml.indexOf("</zoneName>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<zoneName>") + 10, serialXml.indexOf("</zoneName>"));
			if (node.length() != 0)
				this.zoneName = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<zoneProvince>") != -1 && serialXml.indexOf("</zoneProvince>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<zoneProvince>") + 14, serialXml.indexOf("</zoneProvince>"));
			if (node.length() != 0)
				this.zoneProvince = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoPlayBeginTime>") != -1 && serialXml.indexOf("</videoPlayBeginTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoPlayBeginTime>") + 20, serialXml.indexOf("</videoPlayBeginTime>"));
			if (node.length() != 0)
				this.videoPlayBeginTime = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoPlayEndTime>") != -1 && serialXml.indexOf("</videoPlayEndTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoPlayEndTime>") + 18, serialXml.indexOf("</videoPlayEndTime>"));
			if (node.length() != 0)
				this.videoPlayEndTime = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoDuration>") != -1 && serialXml.indexOf("</videoDuration>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoDuration>") + 15, serialXml.indexOf("</videoDuration>"));
			if (node.length() != 0)
				this.videoDuration = Long.valueOf(node);
		}
		if (serialXml.indexOf("<videoPlayLength>") != -1 && serialXml.indexOf("</videoPlayLength>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoPlayLength>") + 17, serialXml.indexOf("</videoPlayLength>"));
			if (node.length() != 0)
				this.videoPlayLength = Long.valueOf(node);
		}
		if (serialXml.indexOf("<videoPlayScale>") != -1 && serialXml.indexOf("</videoPlayScale>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoPlayScale>") + 16, serialXml.indexOf("</videoPlayScale>"));
			if (node.length() != 0)
				this.videoPlayScale = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<payMoney>") != -1 && serialXml.indexOf("</payMoney>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<payMoney>") + 10, serialXml.indexOf("</payMoney>"));
			if (node.length() != 0)
				this.payMoney = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<isValidPlay>") != -1 && serialXml.indexOf("</isValidPlay>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<isValidPlay>") + 13, serialXml.indexOf("</isValidPlay>"));
			if (node.length() != 0)
				this.isValidPlay = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<isPlay>") != -1 && serialXml.indexOf("</isPlay>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<isPlay>") + 8, serialXml.indexOf("</isPlay>"));
			if (node.length() != 0)
				this.isPlay = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<isGenerateFile>") != -1 && serialXml.indexOf("</isGenerateFile>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<isGenerateFile>") + 16, serialXml.indexOf("</isGenerateFile>"));
			if (node.length() != 0)
				this.isGenerateFile = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<gatherState>") != -1 && serialXml.indexOf("</gatherState>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<gatherState>") + 13, serialXml.indexOf("</gatherState>"));
			if (node.length() != 0)
				this.gatherState = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<zoneAnalyzeState>") != -1 && serialXml.indexOf("</zoneAnalyzeState>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<zoneAnalyzeState>") + 18, serialXml.indexOf("</zoneAnalyzeState>"));
			if (node.length() != 0)
				this.zoneAnalyzeState = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<segmentAnalyzeState>") != -1 && serialXml.indexOf("</segmentAnalyzeState>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<segmentAnalyzeState>") + 21, serialXml.indexOf("</segmentAnalyzeState>"));
			if (node.length() != 0)
				this.segmentAnalyzeState = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<videoDemandTime>") != -1 && serialXml.indexOf("</videoDemandTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoDemandTime>") + 17, serialXml.indexOf("</videoDemandTime>"));
			if (node.length() != 0)
				this.videoDemandTime = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoDemandHour>") != -1 && serialXml.indexOf("</videoDemandHour>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoDemandHour>") + 17, serialXml.indexOf("</videoDemandHour>"));
			if (node.length() != 0)
				this.videoDemandHour = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<workstationPageUrl>") != -1 && serialXml.indexOf("</workstationPageUrl>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<workstationPageUrl>") + 20, serialXml.indexOf("</workstationPageUrl>"));
			if (node.length() != 0)
				this.workstationPageUrl = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<applyCPC>") != -1 && serialXml.indexOf("</applyCPC>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<applyCPC>") + 10, serialXml.indexOf("</applyCPC>"));
			if (node.length() != 0)
				this.applyCPC = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<userId>") != -1 && serialXml.indexOf("</userId>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<userId>") + 8, serialXml.indexOf("</userId>"));
			if (node.length() != 0)
				this.userId = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<videoMatchType>") != -1 && serialXml.indexOf("</videoMatchType>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<videoMatchType>") + 16, serialXml.indexOf("</videoMatchType>"));
			if (node.length() != 0)
				this.videoMatchType = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<applyBoard>") != -1 && serialXml.indexOf("</applyBoard>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<applyBoard>") + 12, serialXml.indexOf("</applyBoard>"));
			if (node.length() != 0)
				this.applyBoard = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<playerType>") != -1 && serialXml.indexOf("</playerType>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<playerType>") + 12, serialXml.indexOf("</playerType>"));
			if (node.length() != 0)
				this.playerType = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<isOverflow>") != -1 && serialXml.indexOf("</isOverflow>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<isOverflow>") + 12, serialXml.indexOf("</isOverflow>"));
			if (node.length() != 0)
				this.isOverflow = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<packageMatch>") != -1 && serialXml.indexOf("</packageMatch>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<packageMatch>") + 14, serialXml.indexOf("</packageMatch>"));
			if (node.length() != 0)
				this.packageMatch = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<isExhibit>") != -1 && serialXml.indexOf("</isExhibit>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<isExhibit>") + 11, serialXml.indexOf("</isExhibit>"));
			if (node.length() != 0)
				this.isExhibit = Integer.valueOf(node);
		}
		if (serialXml.indexOf("<affixContentName>") != -1 && serialXml.indexOf("</affixContentName>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<affixContentName>") + 18, serialXml.indexOf("</affixContentName>"));
			if (node.length() != 0)
				this.affixContentName = URLDecoder.decode(node, "GBK");
		}
		if (serialXml.indexOf("<ipOrganization>") != -1 && serialXml.indexOf("</ipOrganization>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<ipOrganization>") + 16, serialXml.indexOf("</ipOrganization>"));
			if (node.length() != 0)
				this.ipOrganization = URLDecoder.decode(node, "GBK");
		}
		// 父类属性恢复
		super.fromSerialXml(serialXml);
	}
}