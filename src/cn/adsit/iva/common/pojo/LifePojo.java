/**
 * 记录生命开始时间的基础Pojo对象
 */
package cn.adsit.iva.common.pojo;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Administrator
 * 
 * 记录生命开始时间的基础Pojo对象
 */
public class LifePojo extends Pojo
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = -3110072892890258244L;

	/**
	 * 生命周期状态：及时
	 */
	public static final int LIFE_STATE_INTIME = 1;

	/**
	 * 生命周期状态：有效
	 */
	public static final int LIFE_STATE_VALID = 2;

	/**
	 * 生命周期状态：无效
	 */
	public static final int LIFE_STATE_INVALID = 4;

	/**
	 * 生命开始时间
	 */
	private Long lifeTime = 0L;

	/**
	 * 生命周期状态
	 */
	private Integer lifeState = LifePojo.LIFE_STATE_INVALID;

	/**
	 * 最后一次访问时间
	 */
	private Long lastVisitTime = 0L;

	/**
	 * 摘要
	 */
	private String digest = null;

	/**
	 * 对象初始化
	 */
	public void init()
	{
	}

	/**
	 * 得到生命开始时间
	 * 
	 * @return 生命开始时间
	 */
	public Long getLifeTime()
	{
		return lifeTime;
	}

	/**
	 * 设置
	 * 
	 * @param lifeTime 生命开始时间
	 */
	public void setLifeTime(Long lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	/**
	 * 得到生命周期状态
	 * 
	 * @return 生命周期状态
	 */
	public Integer getLifeState()
	{
		return lifeState;
	}

	/**
	 * 设置生命周期状态
	 * 
	 * @param lifeState 生命周期状态
	 */
	public void setLifeState(Integer lifeState)
	{
		this.lifeState = lifeState;
	}

	/**
	 * 得到最后一次访问时间
	 * 
	 * @return 最后一次访问时间
	 */
	public Long getLastVisitTime()
	{
		return lastVisitTime;
	}

	/**
	 * 设置最后一次访问时间
	 * 
	 * @param lastVisitTime 最后一次访问时间
	 */
	public void setLastVisitTime(Long lastVisitTime)
	{
		this.lastVisitTime = lastVisitTime;
	}

	/**
	 * 得到摘要
	 * 
	 * @return 摘要
	 */
	public String getDigest()
	{
		return digest;
	}

	/**
	 * 设置摘要
	 * 
	 * @param digest 摘要
	 */
	public void setDigest(String digest)
	{
		this.digest = digest;
	}

	/**
	 * 从序列化字符串解析
	 */
	public void parseSerial() throws Exception
	{
	}

	/**
	 * 对象序列化为XML字符串
	 * 
	 * @return XML字符串
	 */
	public String toSerialXml() throws Exception
	{
		StringBuffer serialXml = new StringBuffer();
		serialXml.append("<cn.adsit.common.pojo.LifePojo>");
		this.innerSerialXml(serialXml);
		serialXml.append("</cn.adsit.common.pojo.LifePojo>");
		return serialXml.toString();
	}

	/**
	 * 对象序列化XML字符串
	 * 
	 * @param serialXml 对象序列化存储空间
	 * @throws Exception 操作异常对象
	 */
	public void innerSerialXml(StringBuffer serialXml) throws Exception
	{
		// 生命开始时间
		serialXml.append("<lifeTime>" + (this.lifeTime == null ? "" : this.lifeTime) + "</lifeTime>");
		// 生命周期状态
		serialXml.append("<lifeState>" + (this.lifeState == null ? "" : this.lifeState) + "</lifeState>");
		// 最后一次访问时间
		serialXml.append("<lastVisitTime>" + (this.lastVisitTime == null ? "" : this.lastVisitTime) + "</lastVisitTime>");
		// 摘要
		serialXml.append("<digest>" + (this.digest == null ? "" : URLEncoder.encode(this.digest, "GBK")) + "</digest>");
	}

	/**
	 * 从序列化XML字符串还原对象
	 * 
	 * @param serialXml XML字符串
	 */
	public void fromSerialXml(String serialXml) throws Exception
	{
		String node = null;
		// 生命开始时间
		if (serialXml.indexOf("<lifeTime>") != -1 && serialXml.indexOf("</lifeTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<lifeTime>") + 10, serialXml.indexOf("</lifeTime>"));
			if (node.length() != 0)
				this.lifeTime = Long.valueOf(node);
		}
		// 生命周期状态
		if (serialXml.indexOf("<lifeState>") != -1 && serialXml.indexOf("</lifeState>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<lifeState>") + 11, serialXml.indexOf("</lifeState>"));
			if (node.length() != 0)
				this.lifeState = Integer.valueOf(node);
		}
		// 最后一次访问时间
		if (serialXml.indexOf("<lastVisitTime>") != -1 && serialXml.indexOf("</lastVisitTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<lastVisitTime>") + 15, serialXml.indexOf("</lastVisitTime>"));
			if (node.length() != 0)
				this.lastVisitTime = Long.valueOf(node);
		}
		// 摘要
		if (serialXml.indexOf("<digest>") != -1 && serialXml.indexOf("</digest>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<digest>") + 8, serialXml.indexOf("</digest>"));
			if (node.length() != 0)
				this.digest = URLDecoder.decode(node, "GBK");
		}
	}
}
