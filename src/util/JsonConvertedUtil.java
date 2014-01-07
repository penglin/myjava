package util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON��ʽ�Զ�ת��������
 * 
 * @author Administrator
 * 
 */
public class JsonConvertedUtil
{

	/**
	 * Json�ַ���ת���ɶ���
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( { "static-access", "unchecked" })
	public static Object jsonStringToObject(String jsonString, Class cls) throws Exception
	{
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return jsonObject.toBean(jsonObject, cls);
	}

	/**
	 * �Ѷ���ת����Json�ַ���
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String objectToJsonString(Object obj) throws Exception
	{
		JSONObject jsonObject = JSONObject.fromObject(obj);
		return jsonObject.toString();
	}

	/**
	 * ��json�����н�����java�ַ�������
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString)
	{
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++)
		{
			stringArray[i] = jsonArray.getString(i);

		}
		return stringArray;
	}

}