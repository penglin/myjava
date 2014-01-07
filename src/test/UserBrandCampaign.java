package test;

import java.io.Serializable;

public class UserBrandCampaign implements Serializable{
	private String brandName;
	private String	brandId;
	private String	campaignId;
	private long	clickFreq;
	private long	siteFreq;
	public UserBrandCampaign() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBrandCampaign(String brandName, String brandId, String campaignId, long clickFreq, long siteFreq) {
		super();
		this.brandName = brandName;
		this.brandId = brandId;
		this.campaignId = campaignId;
		this.clickFreq = clickFreq;
		this.siteFreq = siteFreq;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public long getClickFreq() {
		return clickFreq;
	}
	public void setClickFreq(long clickFreq) {
		this.clickFreq = clickFreq;
	}
	public long getSiteFreq() {
		return siteFreq;
	}
	public void setSiteFreq(long siteFreq) {
		this.siteFreq = siteFreq;
	}
}
