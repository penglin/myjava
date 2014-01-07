/*
 * Created on 2006-4-14
 *
 * POJO���������
 */
package cn.adsit.iva.common.pojo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Administrator
 * 
 * POJO���������
 */
public class Pojo implements Serializable
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = -1794419255769340261L;

	/**
	 * ��չ���Լ��϶���
	 */
	protected HashMap<String, Object> extendAttributes = new HashMap<String, Object>();

	/**
	 * �ύģ����չ����
	 * 
	 * @param attributeName String ��������
	 * @param attributeValue Object ����ֵ����
	 */
	public void putExtendAttribute(String attributeName, Object attributeValue)
	{
		extendAttributes.put(attributeName, attributeValue);
	}

	/**
	 * �õ�ָ�����Ƶ���չ����ֵ����
	 * 
	 * @param attributeName String ��������
	 * @return Object ��չ����ֵ����
	 */
	public Object getExtendAttribute(String attributeName)
	{
		return this.extendAttributes.get(attributeName);
	}

	/**
	 * ���������չ����
	 */
	public void clearExtendAttributes()
	{
		this.extendAttributes.clear();
	}

	/**
	 * �õ���չ���Լ���[�ṩBean֧��(DWR)]
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Object> getExtendAttributes()
	{
		return this.extendAttributes;
	}

	/**
	 * ������չ���Լ���[�ṩBean֧��(DWR)]
	 * 
	 * @param extendAttributes
	 */
	public void setExtendAttributes(HashMap<String, Object> extendAttributes)
	{
		this.extendAttributes = extendAttributes;
	}
}