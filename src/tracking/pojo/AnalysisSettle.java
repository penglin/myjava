package tracking.pojo;

/**
 * AnalysisSettle entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AnalysisSettle implements java.io.Serializable
{
	/**
	 * SerialId
	 */
	private static final long serialVersionUID = 8742514109178197789L;
	//数据状态 历史数据
	public static final int DATA_STATE_HISTORY= 0;
	//数据状态 正常数据
	public static final int DATA_STATE_NOMAL= 1;
	
	// Fields
	private AnalysisSettleKey analysisSettleKey;
	private Integer sendSum;
	private Integer receiveSum;
	private Integer settleSum;
	private Integer settleErrorSum;
	private String endSettleTime;
	private String analysisSettleTime;
	//数据状态
	private Integer dateState;

	// Constructors

	public AnalysisSettle()
	{
	}

	// Property accessors

	public AnalysisSettleKey getAnalysisSettleKey()
	{
		return this.analysisSettleKey;
	}

	public AnalysisSettle(AnalysisSettleKey analysisSettleKey, Integer sendSum, Integer receiveSum, Integer settleSum, Integer settleErrorSum,
			String endSettleTime, String analysisSettleTime) {
		super();
		this.analysisSettleKey = analysisSettleKey;
		this.sendSum = sendSum;
		this.receiveSum = receiveSum;
		this.settleSum = settleSum;
		this.settleErrorSum = settleErrorSum;
		this.endSettleTime = endSettleTime;
		this.analysisSettleTime = analysisSettleTime;
	}

	public void setAnalysisSettleKey(AnalysisSettleKey analysisSettleKey)
	{
		this.analysisSettleKey = analysisSettleKey;
	}

	public Integer getSendSum()
	{
		return this.sendSum;
	}

	public void setSendSum(Integer sendSum)
	{
		this.sendSum = sendSum;
	}

	public Integer getReceiveSum()
	{
		return this.receiveSum;
	}

	public void setReceiveSum(Integer receiveSum)
	{
		this.receiveSum = receiveSum;
	}

	public Integer getSettleSum()
	{
		return this.settleSum;
	}

	public void setSettleSum(Integer settleSum)
	{
		this.settleSum = settleSum;
	}

	public Integer getSettleErrorSum()
	{
		return this.settleErrorSum;
	}

	public void setSettleErrorSum(Integer settleErrorSum)
	{
		this.settleErrorSum = settleErrorSum;
	}

	public String getEndSettleTime()
	{
		return this.endSettleTime;
	}

	public void setEndSettleTime(String endSettleTime)
	{
		this.endSettleTime = endSettleTime;
	}

	public String getAnalysisSettleTime()
	{
		return this.analysisSettleTime;
	}

	public void setAnalysisSettleTime(String analysisSettleTime)
	{
		this.analysisSettleTime = analysisSettleTime;
	}

	public Integer getDateState() {
		return dateState;
	}

	public void setDateState(Integer dateState) {
		this.dateState = dateState;
	}

}