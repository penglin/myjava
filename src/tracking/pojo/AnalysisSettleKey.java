package tracking.pojo;

/**
 * AnalysisSettleId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AnalysisSettleKey implements java.io.Serializable
{
	/**
	 * SerialId
	 */
	private static final long serialVersionUID = 2039046501825887008L;

	// Fields

	private String analysisIp;
	private String settleDate;

	// Constructors

	public AnalysisSettleKey()
	{
	}

	public AnalysisSettleKey(String analysisIp, String settleDate)
	{
		this.analysisIp = analysisIp;
		this.settleDate = settleDate;
	}

	// Property accessors

	public String getAnalysisIp()
	{
		return this.analysisIp;
	}

	public void setAnalysisIp(String analysisIp)
	{
		this.analysisIp = analysisIp;
	}

	public String getSettleDate()
	{
		return this.settleDate;
	}

	public void setSettleDate(String settleDate)
	{
		this.settleDate = settleDate;
	}

	public boolean equals(Object other)
	{
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AnalysisSettleKey))
			return false;
		AnalysisSettleKey castOther = (AnalysisSettleKey) other;

		return ((this.getAnalysisIp() == castOther.getAnalysisIp()) || (this.getAnalysisIp() != null && castOther.getAnalysisIp() != null && this
				.getAnalysisIp().equals(castOther.getAnalysisIp())))
				&& ((this.getSettleDate() == castOther.getSettleDate()) || (this.getSettleDate() != null && castOther.getSettleDate() != null && this
						.getSettleDate().equals(castOther.getSettleDate())));
	}

	public int hashCode()
	{
		int result = 17;

		result = 37 * result + (getAnalysisIp() == null ? 0 : this.getAnalysisIp().hashCode());
		result = 37 * result + (getSettleDate() == null ? 0 : this.getSettleDate().hashCode());
		return result;
	}

}