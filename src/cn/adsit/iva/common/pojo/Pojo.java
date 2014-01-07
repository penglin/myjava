/*
 * Created on 2006-4-14
 *
 * POJO对象基础类
 */
package cn.adsit.iva.common.pojo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Administrator
 * 
 * POJO对象基础类
 */
public class Pojo implements Serializable
{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = -1794419255769340261L;

	/**
	 * 扩展属性集合对象
	 */
	protected HashMap<String, Object> extendAttributes = new HashMap<String, Object>();

	/**
	 * 提交模板扩展属性
	 * 
	 * @param attributeName String 属性名称
	 * @param attributeValue Object 属性值对象
	 */
	public void putExtendAttribute(String attributeName, Object attributeValue)
	{
		extendAttributes.put(attributeName, attributeValue);
	}

	/**
	 * 得到指定名称的扩展属性值对象
	 * 
	 * @param attributeName String 属性名称
	 * @return Object 扩展属性值对象
	 */
	public Object getExtendAttribute(String attributeName)
	{
		return this.extendAttributes.get(attributeName);
	}

	/**
	 * 清空所有扩展属性
	 */
	public void clearExtendAttributes()
	{
		this.extendAttributes.clear();
	}

	/**
	 * 得到扩展属性集和[提供Bean支持(DWR)]
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Object> getExtendAttributes()
	{
		return this.extendAttributes;
	}

	/**
	 * 设置扩展属性集和[提供Bean支持(DWR)]
	 * 
	 * @param extendAttributes
	 */
	public void setExtendAttributes(HashMap<String, Object> extendAttributes)
	{
		this.extendAttributes = extendAttributes;
	}
}