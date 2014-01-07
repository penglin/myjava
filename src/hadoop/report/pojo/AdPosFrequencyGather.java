package hadoop.report.pojo;

import hadoop.report.pojo.common.Gather;
import cn.adsit.common.util.JsonConvertedUtil;

/**
 * AdPosFrequencyGather entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AdPosFrequencyGather extends Gather implements java.io.Serializable {

	// Fields

	private String adPosFrequencyGatherId;
	private String campaignId;
	private String campaignTrackId;
	private String workstationId;
	private String channelId;
	private String adPosId;
	private String zoneProvince;
	private String zoneCity;
	private String gatherDate;
	private Long frequency1Count;
	private Long frequency2Count;
	private Long frequency3Count;
	private Long frequency4Count;
	private Long frequency5Count;
	private Long frequency6Count;
	private Long frequency7Count;
	private Long frequency8Count;
	private Long frequency9Count;
	private Long frequency10Count;
	private Long frequency11Count;
	private Long frequency12Count;
	private Long frequency13Count;
	private Long frequency14Count;
	private Long frequency15Count;
	private Long frequency16Count;
	private Long frequency17Count;
	private Long frequency18Count;
	private Long frequency19Count;
	private Long frequency20Count;
	
	private Long visitorCount;

	// Constructors
	private void initParams(){
		this.pointers = new String[]{"Frequency1Count","Frequency2Count","Frequency3Count",
				"Frequency4Count","Frequency5Count","Frequency6Count",
				"Frequency7Count","Frequency8Count","Frequency9Count",
				"Frequency10Count","Frequency11Count","Frequency12Count",
				"Frequency13Count","Frequency14Count","Frequency15Count",
				"Frequency16Count","Frequency17Count","Frequency18Count",
				"Frequency19Count","Frequency20Count"};
		this.gatherKeys = new String[]{"CampaignId","CampaignTrackId","WorkstationId","AdPosId","ZoneProvince","ZoneCity","GatherDate"};
	}

	/** default constructor */
	public AdPosFrequencyGather() {
		initParams();
	}
	
	public AdPosFrequencyGather(String value) {
		super(value);
		initParams();
	}

	public String getAdPosFrequencyGatherId() {
		return adPosFrequencyGatherId;
	}

	public void setAdPosFrequencyGatherId(String adPosFrequencyGatherId) {
		this.adPosFrequencyGatherId = adPosFrequencyGatherId;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignTrackId() {
		return campaignTrackId;
	}

	public void setCampaignTrackId(String campaignTrackId) {
		this.campaignTrackId = campaignTrackId;
	}

	public String getWorkstationId() {
		return workstationId;
	}

	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAdPosId() {
		return adPosId;
	}

	public void setAdPosId(String adPosId) {
		this.adPosId = adPosId;
	}

	public String getZoneProvince() {
		return zoneProvince;
	}

	public void setZoneProvince(String zoneProvince) {
		this.zoneProvince = zoneProvince;
	}

	public String getZoneCity() {
		return zoneCity;
	}

	public void setZoneCity(String zoneCity) {
		this.zoneCity = zoneCity;
	}

	public String getGatherDate() {
		return gatherDate;
	}

	public void setGatherDate(String gatherDate) {
		this.gatherDate = gatherDate;
	}

	public Long getFrequency1Count() {
		return frequency1Count==null?0L:frequency1Count;
	}

	public void setFrequency1Count(Long frequency1Count) {
		this.frequency1Count = frequency1Count;
	}

	public Long getFrequency2Count() {
		return frequency2Count==null?0L:frequency2Count;
	}

	public void setFrequency2Count(Long frequency2Count) {
		this.frequency2Count = frequency2Count;
	}

	public Long getFrequency3Count() {
		return frequency3Count==null?0L:frequency3Count;
	}

	public void setFrequency3Count(Long frequency3Count) {
		this.frequency3Count = frequency3Count;
	}

	public Long getFrequency4Count() {
		return frequency4Count==null?0L:frequency4Count;
	}

	public void setFrequency4Count(Long frequency4Count) {
		this.frequency4Count = frequency4Count;
	}

	public Long getFrequency5Count() {
		return frequency5Count==null?0L:frequency5Count;
	}

	public void setFrequency5Count(Long frequency5Count) {
		this.frequency5Count = frequency5Count;
	}

	public Long getFrequency6Count() {
		return frequency6Count==null?0L:frequency6Count;
	}

	public void setFrequency6Count(Long frequency6Count) {
		this.frequency6Count = frequency6Count;
	}

	public Long getFrequency7Count() {
		return frequency7Count==null?0L:frequency7Count;
	}

	public void setFrequency7Count(Long frequency7Count) {
		this.frequency7Count = frequency7Count;
	}

	public Long getFrequency8Count() {
		return frequency8Count==null?0L:frequency8Count;
	}

	public void setFrequency8Count(Long frequency8Count) {
		this.frequency8Count = frequency8Count;
	}

	public Long getFrequency9Count() {
		return frequency9Count==null?0L:frequency9Count;
	}

	public void setFrequency9Count(Long frequency9Count) {
		this.frequency9Count = frequency9Count;
	}

	public Long getFrequency10Count() {
		return frequency10Count==null?0L:frequency10Count;
	}

	public void setFrequency10Count(Long frequency10Count) {
		this.frequency10Count = frequency10Count;
	}

	public Long getFrequency11Count() {
		return frequency11Count==null?0L:frequency11Count;
	}

	public void setFrequency11Count(Long frequency11Count) {
		this.frequency11Count = frequency11Count;
	}

	public Long getFrequency12Count() {
		return frequency12Count==null?0L:frequency12Count;
	}

	public void setFrequency12Count(Long frequency12Count) {
		this.frequency12Count = frequency12Count;
	}

	public Long getFrequency13Count() {
		return frequency13Count==null?0L:frequency13Count;
	}

	public void setFrequency13Count(Long frequency13Count) {
		this.frequency13Count = frequency13Count;
	}

	public Long getFrequency14Count() {
		return frequency14Count==null?0L:frequency14Count;
	}

	public void setFrequency14Count(Long frequency14Count) {
		this.frequency14Count = frequency14Count;
	}

	public Long getFrequency15Count() {
		return frequency15Count==null?0L:frequency15Count;
	}

	public void setFrequency15Count(Long frequency15Count) {
		this.frequency15Count = frequency15Count;
	}

	public Long getFrequency16Count() {
		return frequency16Count==null?0L:frequency16Count;
	}

	public void setFrequency16Count(Long frequency16Count) {
		this.frequency16Count = frequency16Count;
	}

	public Long getFrequency17Count() {
		return frequency17Count==null?0L:frequency17Count;
	}

	public void setFrequency17Count(Long frequency17Count) {
		this.frequency17Count = frequency17Count;
	}

	public Long getFrequency18Count() {
		return frequency18Count==null?0L:frequency18Count;
	}

	public void setFrequency18Count(Long frequency18Count) {
		this.frequency18Count = frequency18Count;
	}

	public Long getFrequency19Count() {
		return frequency19Count==null?0L:frequency19Count;
	}

	public void setFrequency19Count(Long frequency19Count) {
		this.frequency19Count = frequency19Count;
	}

	public Long getFrequency20Count() {
		return frequency20Count==null?0L:frequency20Count;
	}

	public void setFrequency20Count(Long frequency20Count) {
		this.frequency20Count = frequency20Count;
	}

	public Long getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(Long visitorCount) {
		this.visitorCount = visitorCount;
	}

	
	public void frequency(int count){
		switch (count) {
		case 1:
			this.setFrequency1Count(this.getFrequency1Count() + 1L);
			break;
		case 2:
			this.setFrequency2Count(this.getFrequency2Count() + 1L);
			break;
		case 3:
			this.setFrequency3Count(this.getFrequency3Count() + 1L);
			break;
		case 4:
			this.setFrequency4Count(this.getFrequency4Count() + 1L);
			break;
		case 5:
			this.setFrequency5Count(this.getFrequency5Count() + 1L);
			break;
		case 6:
			this.setFrequency6Count(this.getFrequency6Count() + 1L);
			break;
		case 7:
			this.setFrequency7Count(this.getFrequency7Count() + 1L);
			break;
		case 8:
			this.setFrequency8Count(this.getFrequency8Count() + 1L);
			break;
		case 9:
			this.setFrequency9Count(this.getFrequency9Count() + 1L);
			break;
		case 10:
			this.setFrequency10Count(this.getFrequency10Count() + 1L);
			break;
		case 11:
			this.setFrequency11Count(this.getFrequency11Count() + 1L);
			break;
		case 12:
			this.setFrequency12Count(this.getFrequency12Count() + 1L);
			break;
		case 13:
			this.setFrequency13Count(this.getFrequency13Count() + 1L);
			break;
		case 14:
			this.setFrequency14Count(this.getFrequency14Count() + 1L);
			break;
		case 15:
			this.setFrequency15Count(this.getFrequency15Count() + 1L);
			break;
		case 16:
			this.setFrequency16Count(this.getFrequency16Count() + 1L);
			break;
		case 17:
			this.setFrequency17Count(this.getFrequency17Count() + 1L);
			break;
		case 18:
			this.setFrequency18Count(this.getFrequency18Count() + 1L);
			break;
		case 19:
			this.setFrequency19Count(this.getFrequency19Count() + 1L);
			break;
		default:
			this.setFrequency20Count(this.getFrequency20Count() + 1L);
			break;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		try {
//			return JsonConvertedUtil.objectToJsonString(this);
		} catch (Exception e) {
		}
		return super.toString();
	}
}