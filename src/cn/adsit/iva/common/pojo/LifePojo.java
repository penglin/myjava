/**
 * ��¼������ʼʱ��Ļ���Pojo����
 */
package cn.adsit.iva.common.pojo;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Administrator
 * 
 * ��¼������ʼʱ��Ļ���Pojo����
 */
public class LifePojo extends Pojo
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = -3110072892890258244L;

	/**
	 * ��������״̬����ʱ
	 */
	public static final int LIFE_STATE_INTIME = 1;

	/**
	 * ��������״̬����Ч
	 */
	public static final int LIFE_STATE_VALID = 2;

	/**
	 * ��������״̬����Ч
	 */
	public static final int LIFE_STATE_INVALID = 4;

	/**
	 * ������ʼʱ��
	 */
	private Long lifeTime = 0L;

	/**
	 * ��������״̬
	 */
	private Integer lifeState = LifePojo.LIFE_STATE_INVALID;

	/**
	 * ���һ�η���ʱ��
	 */
	private Long lastVisitTime = 0L;

	/**
	 * ժҪ
	 */
	private String digest = null;

	/**
	 * �����ʼ��
	 */
	public void init()
	{
	}

	/**
	 * �õ�������ʼʱ��
	 * 
	 * @return ������ʼʱ��
	 */
	public Long getLifeTime()
	{
		return lifeTime;
	}

	/**
	 * ����
	 * 
	 * @param lifeTime ������ʼʱ��
	 */
	public void setLifeTime(Long lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	/**
	 * �õ���������״̬
	 * 
	 * @return ��������״̬
	 */
	public Integer getLifeState()
	{
		return lifeState;
	}

	/**
	 * ������������״̬
	 * 
	 * @param lifeState ��������״̬
	 */
	public void setLifeState(Integer lifeState)
	{
		this.lifeState = lifeState;
	}

	/**
	 * �õ����һ�η���ʱ��
	 * 
	 * @return ���һ�η���ʱ��
	 */
	public Long getLastVisitTime()
	{
		return lastVisitTime;
	}

	/**
	 * �������һ�η���ʱ��
	 * 
	 * @param lastVisitTime ���һ�η���ʱ��
	 */
	public void setLastVisitTime(Long lastVisitTime)
	{
		this.lastVisitTime = lastVisitTime;
	}

	/**
	 * �õ�ժҪ
	 * 
	 * @return ժҪ
	 */
	public String getDigest()
	{
		return digest;
	}

	/**
	 * ����ժҪ
	 * 
	 * @param digest ժҪ
	 */
	public void setDigest(String digest)
	{
		this.digest = digest;
	}

	/**
	 * �����л��ַ�������
	 */
	public void parseSerial() throws Exception
	{
	}

	/**
	 * �������л�ΪXML�ַ���
	 * 
	 * @return XML�ַ���
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
	 * �������л�XML�ַ���
	 * 
	 * @param serialXml �������л��洢�ռ�
	 * @throws Exception �����쳣����
	 */
	public void innerSerialXml(StringBuffer serialXml) throws Exception
	{
		// ������ʼʱ��
		serialXml.append("<lifeTime>" + (this.lifeTime == null ? "" : this.lifeTime) + "</lifeTime>");
		// ��������״̬
		serialXml.append("<lifeState>" + (this.lifeState == null ? "" : this.lifeState) + "</lifeState>");
		// ���һ�η���ʱ��
		serialXml.append("<lastVisitTime>" + (this.lastVisitTime == null ? "" : this.lastVisitTime) + "</lastVisitTime>");
		// ժҪ
		serialXml.append("<digest>" + (this.digest == null ? "" : URLEncoder.encode(this.digest, "GBK")) + "</digest>");
	}

	/**
	 * �����л�XML�ַ�����ԭ����
	 * 
	 * @param serialXml XML�ַ���
	 */
	public void fromSerialXml(String serialXml) throws Exception
	{
		String node = null;
		// ������ʼʱ��
		if (serialXml.indexOf("<lifeTime>") != -1 && serialXml.indexOf("</lifeTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<lifeTime>") + 10, serialXml.indexOf("</lifeTime>"));
			if (node.length() != 0)
				this.lifeTime = Long.valueOf(node);
		}
		// ��������״̬
		if (serialXml.indexOf("<lifeState>") != -1 && serialXml.indexOf("</lifeState>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<lifeState>") + 11, serialXml.indexOf("</lifeState>"));
			if (node.length() != 0)
				this.lifeState = Integer.valueOf(node);
		}
		// ���һ�η���ʱ��
		if (serialXml.indexOf("<lastVisitTime>") != -1 && serialXml.indexOf("</lastVisitTime>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<lastVisitTime>") + 15, serialXml.indexOf("</lastVisitTime>"));
			if (node.length() != 0)
				this.lastVisitTime = Long.valueOf(node);
		}
		// ժҪ
		if (serialXml.indexOf("<digest>") != -1 && serialXml.indexOf("</digest>") != -1)
		{
			node = serialXml.substring(serialXml.indexOf("<digest>") + 8, serialXml.indexOf("</digest>"));
			if (node.length() != 0)
				this.digest = URLDecoder.decode(node, "GBK");
		}
	}
}