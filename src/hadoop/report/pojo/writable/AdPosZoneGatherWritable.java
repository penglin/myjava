package hadoop.report.pojo.writable;

import hadoop.report.pojo.common.Gather;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class AdPosZoneGatherWritable extends Gather implements WritableComparable<AdPosZoneGatherWritable>{
	private Text campaignId;
	private Text campaignTrackId;
	private Text gatherDate;
	private Text gatherHour;
	private Text workstationId;
	private Text channelId;
	private Text adPosId;
	private Text zoneProvince;
	private Text zoneCity;
	private LongWritable demandCount;
	private LongWritable demandUniqueCount;
	private LongWritable clickCount;
	private LongWritable clickUniqueCount;
	
	public AdPosZoneGatherWritable() {
		super();
		this.gatherKeys = new String[]{"CampaignId","CampaignTrackId","GatherDate","GatherHour","WorkstationId","AdPosId","ZoneProvince","ZoneCity"};
		this.keyMethodPrefix = "get";
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		campaignId.readFields(in);	
		campaignTrackId.readFields(in);
		gatherDate.readFields(in);
		gatherHour.readFields(in);
		workstationId.readFields(in);
		channelId.readFields(in);
		adPosId.readFields(in);
		zoneProvince.readFields(in);
		zoneCity.readFields(in);
		demandCount.readFields(in);
		demandUniqueCount.readFields(in);
		clickCount.readFields(in);
		clickUniqueCount.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		campaignId.write(out);	
		campaignTrackId.write(out);
		gatherDate.write(out);
		gatherHour.write(out);
		workstationId.write(out);
		channelId.write(out);
		adPosId.write(out);
		zoneProvince.write(out);
		zoneCity.write(out);
		demandCount.write(out);
		demandUniqueCount.write(out);
		clickCount.write(out);
		clickUniqueCount.write(out);
	}
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		AdPosZoneGatherWritable gather = new AdPosZoneGatherWritable();
		gather.setCampaignId(new Text("penglin"));
		
		AdPosZoneGatherWritable gather2 = new AdPosZoneGatherWritable();
		gather2.setCampaignId(new Text("penglind"));
		
		int result = gather.compareTo(gather2);
		System.out.println(result);
		
		System.out.println(gather.toString());
	}
	

	@Override
	public int compareTo(AdPosZoneGatherWritable gather) {
		return super.compareTo(gather);
	}

	public Text getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Text campaignId) {
		this.campaignId = campaignId;
	}

	public Text getCampaignTrackId() {
		return campaignTrackId;
	}

	public void setCampaignTrackId(Text campaignTrackId) {
		this.campaignTrackId = campaignTrackId;
	}

	public Text getGatherDate() {
		return gatherDate;
	}

	public void setGatherDate(Text gatherDate) {
		this.gatherDate = gatherDate;
	}

	public Text getGatherHour() {
		return gatherHour;
	}

	public void setGatherHour(Text gatherHour) {
		this.gatherHour = gatherHour;
	}

	public Text getWorkstationId() {
		return workstationId;
	}

	public void setWorkstationId(Text workstationId) {
		this.workstationId = workstationId;
	}

	public Text getChannelId() {
		return channelId;
	}

	public void setChannelId(Text channelId) {
		this.channelId = channelId;
	}

	public Text getAdPosId() {
		return adPosId;
	}

	public void setAdPosId(Text adPosId) {
		this.adPosId = adPosId;
	}

	public Text getZoneProvince() {
		return zoneProvince;
	}

	public void setZoneProvince(Text zoneProvince) {
		this.zoneProvince = zoneProvince;
	}

	public Text getZoneCity() {
		return zoneCity;
	}

	public void setZoneCity(Text zoneCity) {
		this.zoneCity = zoneCity;
	}

	public LongWritable getDemandCount() {
		return demandCount;
	}

	public void setDemandCount(LongWritable demandCount) {
		this.demandCount = demandCount;
	}

	public LongWritable getDemandUniqueCount() {
		return demandUniqueCount;
	}

	public void setDemandUniqueCount(LongWritable demandUniqueCount) {
		this.demandUniqueCount = demandUniqueCount;
	}

	public LongWritable getClickCount() {
		return clickCount;
	}

	public void setClickCount(LongWritable clickCount) {
		this.clickCount = clickCount;
	}

	public LongWritable getClickUniqueCount() {
		return clickUniqueCount;
	}

	public void setClickUniqueCount(LongWritable clickUniqueCount) {
		this.clickUniqueCount = clickUniqueCount;
	}
}
