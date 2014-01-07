package hadoop.report.pojo;

import java.lang.reflect.Constructor;

import oracle.net.ano.SupervisorService;
import hadoop.report.pojo.common.Gather;
import cn.adsit.common.util.JsonConvertedUtil;

/**
 * AdPosZoneSegmentDateGather entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AdPosZoneSegmentGather extends Gather implements java.io.Serializable {
	// Fields

	private String adPosZoneSegmentGatherId;
	private String campaignId;
	private String campaignTrackId;
	private String gatherDate;
	private String gatherHour;
	private String workstationId;
	private String channelId;
	private String adPosId;
	private String zoneProvince;
	private String zoneCity;
	private Long demandCount;
	private Long demandUniqueCount;
	private Long demandUvCount;
	private Long demandIpCount;
	private Long clickCount;
	private Long clickUniqueCount;
	private Long clickUvCount;
	private Long clickIpCount;

	// Constructors

	private void initParams(){
		this.pointers = new String[]{"DemandCount","DemandUniqueCount"};
		this.gatherKeys = new String[]{"CampaignId","CampaignTrackId","WorkstationId","AdPosId","ZoneProvince","ZoneCity","GatherDate","GatherHour"};
	}
	
	/** default constructor */
	public AdPosZoneSegmentGather() {
		initParams();
	}
	
	public AdPosZoneSegmentGather(String value) {
		super(value);
		initParams();
	}

	// Property accessors
	public AdPosZoneSegmentGather(String adPosZoneSegmentGatherId, String campaignId, String campaignTrackId, String gatherDate, String gatherHour,
			String workstationId, String channelId, String adPosId, String zoneProvince, String zoneCity, Long demandCount, Long demandUniqueCount,
			Long demandUvCount, Long demandIpCount, Long clickCount, Long clickUniqueCount, Long clickUvCount, Long clickIpCount) {
		initParams();
		this.adPosZoneSegmentGatherId = adPosZoneSegmentGatherId;
		this.campaignId = campaignId;
		this.campaignTrackId = campaignTrackId;
		this.gatherDate = gatherDate;
		this.gatherHour = gatherHour;
		this.workstationId = workstationId;
		this.channelId = channelId;
		this.adPosId = adPosId;
		this.zoneProvince = zoneProvince;
		this.zoneCity = zoneCity;
		this.demandCount = demandCount;
		this.demandUniqueCount = demandUniqueCount;
		this.demandUvCount = demandUvCount;
		this.demandIpCount = demandIpCount;
		this.clickCount = clickCount;
		this.clickUniqueCount = clickUniqueCount;
		this.clickUvCount = clickUvCount;
		this.clickIpCount = clickIpCount;
	}
	
	public static void main(String[] args) throws Exception {
		AdPosZoneSegmentGather gather = new AdPosZoneSegmentGather();
		gather.setAdPosZoneSegmentGatherId("fsdfsdfsdfsdf");
		gather.setCampaignId("fsdfasdfasdfasd");
		gather.setCampaignTrackId("fasdfasfasdfasdf");
		gather.setGatherDate("2012年03月26日");
		gather.setGatherHour("12");
		gather.setWorkstationId("asfasdfasdfads");
		gather.setAdPosId("fasdfasdfsad");
		gather.setZoneProvince("北京市");
		gather.setZoneCity("海淀区");
		gather.setDemandCount(12L);
		gather.setDemandUniqueCount(3L);
		gather.setDemandUvCount(0L);
		gather.setDemandIpCount(0l);
		gather.setClickCount(12L);
		gather.setClickUniqueCount(2L);
		gather.setClickUvCount(0L);
		gather.setClickIpCount(0L);
		
		Class<?> gather2 = Class.forName("hadoop.report.pojo.AdPosZoneSegmentGather");
		Constructor<?> constructor = gather2.getConstructor(new Class[]{String.class});
		Object obj = constructor.newInstance(gather.toString());
		
		AdPosZoneSegmentGather g = (AdPosZoneSegmentGather) obj;
		System.out.println(g.getGatherKey());
		System.out.println(obj.toString());
	}

	public String getAdPosZoneSegmentGatherId() {
		return this.adPosZoneSegmentGatherId;
	}

	public void setAdPosZoneSegmentGatherId(String adPosZoneSegmentGatherId) {
		this.adPosZoneSegmentGatherId = adPosZoneSegmentGatherId;
	}

	public String getCampaignId() {
		return this.campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignTrackId() {
		return this.campaignTrackId;
	}

	public void setCampaignTrackId(String campaignTrackId) {
		this.campaignTrackId = campaignTrackId;
	}

	public String getGatherDate() {
		return this.gatherDate;
	}

	public void setGatherDate(String gatherDate) {
		this.gatherDate = gatherDate;
	}

	public String getGatherHour() {
		return this.gatherHour;
	}

	public void setGatherHour(String gatherHour) {
		this.gatherHour = gatherHour;
	}

	public String getWorkstationId() {
		return this.workstationId;
	}

	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAdPosId() {
		return this.adPosId;
	}

	public void setAdPosId(String adPosId) {
		this.adPosId = adPosId;
	}

	public String getZoneProvince() {
		return this.zoneProvince;
	}

	public void setZoneProvince(String zoneProvince) {
		this.zoneProvince = zoneProvince;
	}

	public String getZoneCity() {
		return this.zoneCity;
	}

	public void setZoneCity(String zoneCity) {
		this.zoneCity = zoneCity;
	}

	public Long getDemandCount() {
		return this.demandCount;
	}

	public void setDemandCount(Long demandCount) {
		this.demandCount = demandCount;
	}

	public Long getDemandUniqueCount() {
		return this.demandUniqueCount;
	}

	public void setDemandUniqueCount(Long demandUniqueCount) {
		this.demandUniqueCount = demandUniqueCount;
	}

	public Long getDemandUvCount() {
		return this.demandUvCount;
	}

	public void setDemandUvCount(Long demandUvCount) {
		this.demandUvCount = demandUvCount;
	}

	public Long getDemandIpCount() {
		return this.demandIpCount;
	}

	public void setDemandIpCount(Long demandIpCount) {
		this.demandIpCount = demandIpCount;
	}

	public Long getClickCount() {
		return this.clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public Long getClickUniqueCount() {
		return this.clickUniqueCount;
	}

	public void setClickUniqueCount(Long clickUniqueCount) {
		this.clickUniqueCount = clickUniqueCount;
	}

	public Long getClickUvCount() {
		return this.clickUvCount;
	}

	public void setClickUvCount(Long clickUvCount) {
		this.clickUvCount = clickUvCount;
	}

	public Long getClickIpCount() {
		return this.clickIpCount;
	}

	public void setClickIpCount(Long clickIpCount) {
		this.clickIpCount = clickIpCount;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}