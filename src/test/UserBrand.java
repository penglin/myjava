package test;

import java.io.Serializable;
import java.util.List;


public class UserBrand implements Serializable{
	private long lifeTime;
	private List<UserBrandCampaign> userBrandCampaigns ;
	private String userId;
	public UserBrand() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBrand(long lifeTime, List<UserBrandCampaign> userBrandCampaigns, String userId) {
		super();
		this.lifeTime = lifeTime;
		this.userBrandCampaigns = userBrandCampaigns;
		this.userId = userId;
	}
	public long getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	public List<UserBrandCampaign> getUserBrandCampaigns() {
		return userBrandCampaigns;
	}
	public void setUserBrandCampaigns(List<UserBrandCampaign> userBrandCampaigns) {
		this.userBrandCampaigns = userBrandCampaigns;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
