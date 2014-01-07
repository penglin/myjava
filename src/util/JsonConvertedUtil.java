package util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSON格式自动转换工具类
 * 
 * @author Administrator
 * 
 */
public class JsonConvertedUtil
{

	/**
	 * Json字符串转换成对象
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
	 * 把对象转换成Json字符串
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
	 * 从json数组中解析出java字符串数组
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
