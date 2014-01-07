package cn.adsit.iva.preroll.pojo;

import cn.adsit.iva.common.pojo.Pojo;

/** 
 * VideoClick generated by MyEclipse Persistence Tools
 */
public class VideoClick extends Pojo
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = 2374664938749366125L;

	/**
	 * 视频点击类型：百度
	 */
	public static final int VIDEO_CLICK_TYPE_BAIDU = 1;

	/**
	 * 视频点击类型：EGC
	 */
	public static final int VIDEO_CLICK_TYPE_EGC = 2;

	/**
	 * cpc请求否
	 */
	public static final int APPLY_CPC_NO = 0;

	/**
	 * cpc请求是
	 */
	public static final int APPLY_CPC_YES = 1;

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
	 * 动作状态：正常
	 */
	public static final int ACTION_STATE_NORMAL = 1;

	/**
	 * 动作状态：恶意
	 */
	public static final int ACTION_STATE_SPITE = 2;

	/**
	 * 动作状态：系统
	 */
	public static final int ACTION_STATE_SYSTEM = 4;

	// Fields

	private String videoClickId;
	private String videoDemandId;
	private String publishVideoId;
	private String videoId;
	private String userIp;
	private String zoneName;
	private String workstationId;
	private String stationUrl;
	private String videoClickTime;
	private String videoClickDate;
	private String videoClickYear;
	private String videoClickMonth;
	private String videoClickWeek;
	private String videoClickSeason;
	private Integer videoClickType;
	private Integer applyCPC;// 投放性质
	private String userId;
	private Long playLengthClick;
	private String zoneProvince;
	private Integer clickExpectScheme; // 点击优化方案
	private Integer videoMatchType;
	private String contentClassId;
	private String actionImplNumber;
	private Integer actionState;

	/**
	 * 处理次数,此属性不持久化
	 */
	private Integer processCount = 0;

	// Constructors

	public VideoClick()
	{
	}

	// Property accessors

	public String getVideoClickId()
	{
		return this.videoClickId;
	}

	public void setVideoClickId(String videoClickId)
	{
		this.videoClickId = videoClickId;
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

	public String getVideoId()
	{
		return this.videoId;
	}

	public void setVideoId(String videoId)
	{
		this.videoId = videoId;
	}

	public String getWorkstationId()
	{
		return this.workstationId;
	}

	public void setWorkstationId(String workstationId)
	{
		this.workstationId = workstationId;
	}

	public String getStationUrl()
	{
		return this.stationUrl;
	}

	public void setStationUrl(String stationUrl)
	{
		this.stationUrl = stationUrl;
	}

	public String getVideoClickTime()
	{
		return this.videoClickTime;
	}

	public void setVideoClickTime(String videoClickTime)
	{
		this.videoClickTime = videoClickTime;
	}

	public String getVideoClickDate()
	{
		return this.videoClickDate;
	}

	public void setVideoClickDate(String videoClickDate)
	{
		this.videoClickDate = videoClickDate;
	}

	public String getVideoClickYear()
	{
		return this.videoClickYear;
	}

	public void setVideoClickYear(String videoClickYear)
	{
		this.videoClickYear = videoClickYear;
	}

	public String getVideoClickMonth()
	{
		return this.videoClickMonth;
	}

	public void setVideoClickMonth(String videoClickMonth)
	{
		this.videoClickMonth = videoClickMonth;
	}

	public String getVideoClickWeek()
	{
		return this.videoClickWeek;
	}

	public void setVideoClickWeek(String videoClickWeek)
	{
		this.videoClickWeek = videoClickWeek;
	}

	public String getVideoClickSeason()
	{
		return this.videoClickSeason;
	}

	public void setVideoClickSeason(String videoClickSeason)
	{
		this.videoClickSeason = videoClickSeason;
	}

	public String getUserIp()
	{
		return userIp;
	}

	public Integer getVideoClickType()
	{
		return videoClickType;
	}

	public void setUserIp(String userIp)
	{
		this.userIp = userIp;
	}

	public void setVideoClickType(Integer videoClickType)
	{
		this.videoClickType = videoClickType;
	}

	public Integer getProcessCount()
	{
		return processCount;
	}

	public void setProcessCount(Integer processCount)
	{
		this.processCount = processCount;
	}

	public Integer getApplyCPC()
	{
		return applyCPC;
	}

	public void setApplyCPC(Integer applyCPC)
	{
		this.applyCPC = applyCPC;
	}

	public String getZoneName()
	{
		return zoneName;
	}

	public void setZoneName(String zoneName)
	{
		this.zoneName = zoneName;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Long getPlayLengthClick()
	{
		return playLengthClick;
	}

	public void setPlayLengthClick(Long playLengthClick)
	{
		this.playLengthClick = playLengthClick;
	}

	public String getZoneProvince()
	{
		return zoneProvince;
	}

	public void setZoneProvince(String zoneProvince)
	{
		this.zoneProvince = zoneProvince;
	}

	public Integer getClickExpectScheme()
	{
		return clickExpectScheme;
	}

	public void setClickExpectScheme(Integer clickExpectScheme)
	{
		this.clickExpectScheme = clickExpectScheme;
	}

	public Integer getVideoMatchType()
	{
		return videoMatchType;
	}

	public void setVideoMatchType(Integer videoMatchType)
	{
		this.videoMatchType = videoMatchType;
	}

	public String getContentClassId()
	{
		return contentClassId;
	}

	public void setContentClassId(String contentClassId)
	{
		this.contentClassId = contentClassId;
	}

	public String getActionImplNumber()
	{
		return actionImplNumber;
	}

	public void setActionImplNumber(String actionImplNumber)
	{
		this.actionImplNumber = actionImplNumber;
	}

	public Integer getActionState()
	{
		return actionState;
	}

	public void setActionState(Integer actionState)
	{
		this.actionState = actionState;
	}
}