/**
 * JSON工具对象
 */
package json;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 * 
 * JSON工具对象
 */
public class JsonUtil
{
	/**
	 * JSON对象扩展标记名称：类型
	 */
	private static final String JSON_OBJECT_TAG_FOR_TYPE = "JSONUTIL-FOR-TYPE";

	/**
	 * JSON对象扩展标记名称：类名称
	 */
	private static final String JSON_OBJECT_TAG_FOR_NAME = "JSONUTIL-FOR-NAME";

	/**
	 * JSON对象扩展标记名称：简单对象值
	 */
	private static final String JSON_OBJECT_TAG_FOR_VALUE = "JSONUTIL-FOR-VALUE";

	/**
	 * JSON对象类型：简单
	 */
	private static final int JSON_OBJECT_TYPE_SIMPLE = 1;

	/**
	 * JSON对象类型：集合
	 */
	private static final int JSON_OBJECT_TYPE_COLLECTION = 2;

	/**
	 * JSON对象类型：MAP
	 */
	private static final int JSON_OBJECT_TYPE_MAP = 4;

	/**
	 * JSON对象类型：复合
	 */
	private static final int JSON_OBJECT_TYPE_COMPLEX = 8;

	/**
	 * 转换指定对象为JSON对象
	 * 
	 * @param object 对象
	 * @return JSON对象
	 * @throws Exception 处理异常
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject toJson(Object object) throws Exception
	{
		// 验证对象有效性能
		if (object == null)
			return null;
		// 创建JSON对象
		JSONObject jsonObject = new JSONObject();
		// 根据对象类型，分别处理
		if (object instanceof String || object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double)
		{
			// 简单数据类型,直接存储值
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_SIMPLE);
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_VALUE, object);
			return jsonObject;
		}
		else if (object instanceof Collection)
		{
			// 集合数据类型,把集合中每个对象分表保存，并存储在Array中
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_COLLECTION);
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
			// 把列表放入JSONArray中
			JSONArray jsonArray = new JSONArray();
			Iterator iterator = ((Collection) object).iterator();
			while (iterator.hasNext())
			{
				jsonArray.add(JsonUtil.toJson(iterator.next()));
			}
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_VALUE, jsonArray);
			return jsonObject;
		}
		else if (object instanceof Map)
		{
			// MAP数据类型，拆出每个[Key,Value]值对放入Array中，最后所有的值放入Array保存
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_MAP);
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
			// 遍历MAP,把每一对[Key,Value]放入一个JSONArray中,最后再用一个JSONArray封装
			JSONArray jsonArray = new JSONArray();
			Iterator iterator = ((Map) object).keySet().iterator();
			while (iterator.hasNext())
			{
				Object key = iterator.next();
				JSONArray jsonPairArray = new JSONArray();
				jsonPairArray.add(JsonUtil.toJson(key));
				jsonPairArray.add(JsonUtil.toJson(((Map) object).get(key)));
				jsonArray.add(jsonPairArray);
			}
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_VALUE, jsonArray);
			return jsonObject;
		}
		// 复杂对象类型解析,设置JSON对象TAG
		jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_COMPLEX);
		jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
		// 利用类反射机制获取对象信息变量定义
		String fieldName = null;
		Class fieldType = null;
		String fieldTypeName = null;
		Map<String, Integer> fieldTypeInterfaces = new HashMap<String, Integer>();
		Object fieldValue = null;
		Method method = null;
		// 遍历类及父类解析出属性值
		Class cls = object.getClass();
		while (cls != Object.class && cls != null)
		{
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				fieldName = fields[i].getName();
				fieldType = fields[i].getType();
				fieldTypeName = fieldType.getName();
				Class[] interfaces = fieldType.getInterfaces();
				fieldTypeInterfaces.clear();
				for (int j = 0; interfaces != null && j < interfaces.length; j++)
					fieldTypeInterfaces.put(interfaces[j].getName(), 0);
				// 得到属性对应的取值方法
				try
				{
					method = cls.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
				}
				catch (Exception e)
				{
					continue;
				}
				// 得到属性值
				fieldValue = method.invoke(object);
				// 如果属性值为null,不判断类型
				if (fieldValue == null)
					continue;
				// 取值并保存
				// 简单数据类型
				if (fieldTypeName.equals("java.lang.String") || fieldTypeName.equals("java.lang.Integer") || fieldTypeName.equals("java.lang.Long")
						|| fieldTypeName.equals("java.lang.Float") || fieldTypeName.equals("java.lang.Double"))
				{
					jsonObject.put(fieldName, fieldValue);
					continue;
				}
				// 列表数据类型
				if (fieldTypeInterfaces.get("java.util.Collection") != null)
				{
					// 如果集合为空，不继续生成JSON序列化字符串
					if (((Collection) fieldValue).isEmpty())
						continue;
					jsonObject.put(fieldName, JsonUtil.toJson(fieldValue));
					continue;
				}
				// MAP数据类型
				if (fieldTypeInterfaces.get("java.util.Map") != null)
				{
					// 如果集合为空，不继续生成JSON序列化字符串
					if (((Map) fieldValue).isEmpty())
						continue;
					jsonObject.put(fieldName, JsonUtil.toJson(fieldValue));
					continue;
				}
			}
			// 得到超类
			cls = cls.getSuperclass();
		}
		// 结束
		return jsonObject;
	}

	/**
	 * 从指定JSON对象恢复实际对象
	 * 
	 * @param jsonObject JSON对象
	 * @return 实际对象
	 * @throws Exception 处理异常
	 */
	@SuppressWarnings("unchecked")
	public static Object fromJson(JSONObject jsonObject) throws Exception
	{
		// 判断JSON对象有效性
		if (jsonObject == null || jsonObject.isNullObject())
			return null;
		// 得到JSON对象类型、名称和值
		Integer jsonObjectType = (Integer) jsonObject.get(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE);
		String jsonObjectName = jsonObject.getString(JsonUtil.JSON_OBJECT_TAG_FOR_NAME);
		Object jsonObjectValue = jsonObject.get(JsonUtil.JSON_OBJECT_TAG_FOR_VALUE);
		if (jsonObjectType == null || jsonObjectName == null)
			return null;
		// 根据不同数据类型，分别进行处理
		if (jsonObjectType.intValue() == JsonUtil.JSON_OBJECT_TYPE_SIMPLE)
		{
			// JSON对象是简单类型，直接取值
			if (jsonObjectName.equals("java.lang.String"))
			{
				if (jsonObjectValue instanceof java.lang.String)
					return jsonObjectValue;
				else if (jsonObjectValue instanceof JSONObject)
					return ((JSONObject) jsonObjectValue).toString();
			}
			else if (jsonObjectName.equals("java.lang.Integer"))
			{
				return jsonObjectValue;
			}
			else if (jsonObjectName.equals("java.lang.Long"))
			{
				return Long.parseLong("" + jsonObjectValue);
			}
			else if (jsonObjectName.equals("java.lang.Float"))
			{
				return Float.parseFloat("" + jsonObjectValue);
			}
			else if (jsonObjectName.equals("java.lang.Double"))
			{
				return Double.parseDouble("" + jsonObjectValue);
			}
			return null;
		}
		else if (jsonObjectType.intValue() == JsonUtil.JSON_OBJECT_TYPE_COLLECTION)
		{
			// 集合数据类型,从Array中还原集合中每个对象,并放入Collection中返回
			JSONArray jsonArray = (JSONArray) jsonObjectValue;
			if (jsonArray == null)
				return null;
			Collection collection = (Collection) Class.forName(jsonObjectName).newInstance();
			for (int i = 0; i < jsonArray.size(); i++)
			{
				JSONObject jsonSingleObject = jsonArray.getJSONObject(i);
				collection.add(JsonUtil.fromJson(jsonSingleObject));
			}
			return collection;
		}
		else if (jsonObjectType.intValue() == JsonUtil.JSON_OBJECT_TYPE_MAP)
		{
			// MAP数据类型,从Array中还原每对数据，并放入Map中返回
			JSONArray jsonArray = (JSONArray) jsonObjectValue;
			Map map = (Map) Class.forName(jsonObjectName).newInstance();
			for (int i = 0; i < jsonArray.size(); i++)
			{
				JSONArray jsonPairArray = jsonArray.getJSONArray(i);
				map.put(JsonUtil.fromJson(jsonPairArray.getJSONObject(0)), JsonUtil.fromJson(jsonPairArray.getJSONObject(1)));
			}
			return map;
		}
		// JSON对象为复杂类型
		String objectName = jsonObject.getString(JsonUtil.JSON_OBJECT_TAG_FOR_NAME);
		Class cls = Class.forName(objectName);
		Object object = cls.newInstance();
		// 利用类反射机制获取对象信息变量定义
		String fieldName = null;
		Class fieldType = null;
		String fieldTypeName = null;
		Map<String, Integer> fieldTypeInterfaces = new HashMap<String, Integer>();
		Object fieldValue = null;
		String methodName = null;
		Method method = null;
		// 得到指定类属性清单及值
		while (cls != Object.class && cls != null)
		{
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				fieldName = fields[i].getName();
				fieldType = fields[i].getType();
				fieldTypeName = fieldType.getName();
				Class[] interfaces = fieldType.getInterfaces();
				fieldTypeInterfaces.clear();
				for (int j = 0; interfaces != null && j < interfaces.length; j++)
					fieldTypeInterfaces.put(interfaces[j].getName(), 0);
				try
				{
					// JSON对象中属性值
					fieldValue = jsonObject.get(fieldName);
					if (fieldValue == null)
						continue;
					// 得到属性对应的取值方法
					methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					method = cls.getDeclaredMethod(methodName, new Class[] { Class.forName(fieldType.getName()) });
				}
				catch (Exception e)
				{
					continue;
				}
				// 简单数据类型
				if (fieldTypeName.equals("java.lang.String"))
				{
					if (fieldValue instanceof java.lang.String)
						method.invoke(object, new Object[] { fieldValue });
					else if (fieldValue instanceof JSONObject)
						method.invoke(object, new Object[] { ((JSONObject) fieldValue).toString() });
				}
				else if (fieldTypeName.equals("java.lang.Integer"))
				{
					method.invoke(object, new Object[] { fieldValue });
					continue;
				}
				else if (fieldTypeName.equals("java.lang.Long"))
				{
					method.invoke(object, new Object[] { Long.parseLong("" + fieldValue) });
					continue;
				}
				else if (fieldTypeName.equals("java.lang.Float"))
				{
					method.invoke(object, new Object[] { Float.parseFloat("" + fieldValue) });
					continue;
				}
				else if (fieldTypeName.equals("java.lang.Double"))
				{
					method.invoke(object, new Object[] { Double.parseDouble("" + fieldValue) });
					continue;
				}
				// 集合数据类型
				if (fieldTypeInterfaces.get("java.util.Collection") != null)
				{
					method.invoke(object, new Object[] { JsonUtil.fromJson(jsonObject.getJSONObject(fieldName)) });
					continue;
				}
				// MAP数据类型
				if (fieldTypeInterfaces.get("java.util.Map") != null)
				{
					method.invoke(object, new Object[] { JsonUtil.fromJson(jsonObject.getJSONObject(fieldName)) });
					continue;
				}
			}
			// 得到指定类的超类
			cls = cls.getSuperclass();
		}
		// 返回
		return object;
	}

	/**
	 * 指定对象转换成JSON格式字符串
	 * 
	 * @param object 目标对象
	 * @return JSON格式字符串
	 * @throws Exception 处理异常
	 */
	public static String getBeanJsonDescriptor(Object object) throws Exception
	{
		if (object == null)
			return null;
		JSONObject jsonObject = JsonUtil.toJson(object);
		if (jsonObject == null)
			return null;
		return jsonObject.toString();
	}

	/**
	 * 转换JSON格式字符串为目标对象
	 * 
	 * @param jsonDescriptor JSON格式字符串
	 * @return 目标对象
	 * @throws Exception 处理异常
	 */
	public static Object getObjectFromJsonDescriptor(String jsonDescriptor) throws Exception
	{
		if (jsonDescriptor == null)
			return null;
		JSONObject jsonObject = JSONObject.fromObject(jsonDescriptor);
		if (jsonObject == null)
			return null;
		return JsonUtil.fromJson(jsonObject);
	}
	
	/**
	 * 将一个实体类对象转换成Json数据格式
	 * 
	 * @param bean
	 *            需要转换的实体类对象
	 * @return 转换后的Json格式字符串
	 */
	public static String beanToJson(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = objectToJson(props[i].getName());
					String value = objectToJson(props[i].getReadMethod()
							.invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 将一个List对象转换成Json数据格式返回
	 * 
	 * @param list
	 *            需要进行转换的List对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 将一个对象数组转换成Json数据格式返回
	 * 
	 * @param array
	 *            需要进行转换的数组对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String arrayToJson(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 将一个Map对象转换成Json数据格式返回
	 * 
	 * @param map
	 *            需要进行转换的Map对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String mapToJson(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(objectToJson(key));
				json.append(":");
				json.append(objectToJson(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 将一个Set对象转换成Json数据格式返回
	 * 
	 * @param set
	 *            需要进行转换的Set对象
	 * @return 转换后的Json数据格式字符串
	 */
	public static String setToJson(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	private static String numberToJson(Number number) {
		return number.toString();
	}

	private static String booleanToJson(Boolean bool) {
		return bool.toString();
	}

	private static String nullToJson() {
		return "";
	}

	private static String stringToJson(String s) {
		if (s == null) {
			return nullToJson();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	private static String objectToJson(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof Number) {
			json.append(numberToJson((Number) obj));
		} else if (obj instanceof Boolean) {
			json.append(booleanToJson((Boolean) obj));
		} else if (obj instanceof String) {
			json.append("\"").append(stringToJson(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(arrayToJson((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(listToJson((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(mapToJson((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(setToJson((Set<?>) obj));
		} else {
			json.append(beanToJson(obj));
		}
		return json.toString();
	}

	// ============================================================================================

	/**
	 * 将Json格式的字符串转换成指定的对象返回
	 * 
	 * @param jsonString
	 *            Json格式的字符串
	 * @param pojoCalss
	 *            转换后的对象类型
	 * @return 转换后的对象
	 */
	@SuppressWarnings("unchecked")
	public static Object json2Object(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 将Json格式的字符串转换成Map<String,Object>对象返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Map<String,Object>对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 将Json格式的字符串转换成对象数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的对象数组
	 */
	public static Object[] json2ObjectArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 将Json格式的字符串转换成指定对象组成的List返回
	 * 
	 * @param jsonString
	 *            Json格式的字符串
	 * @param pojoClass
	 *            转换后的List中对象类型
	 * @return 转换后的List对象
	 */
	@SuppressWarnings("unchecked")
	public static List json2List(String jsonString, Class pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;
	}

	/**
	 * 将Json格式的字符串转换成字符串数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的字符串数组
	 */
	public static String[] json2StringArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}
		return stringArray;
	}

	/**
	 * 将Json格式的字符串转换成Long数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Long数组
	 */
	public static Long[] json2LongArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}

	/**
	 * 将Json格式的字符串转换成Integer数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Integer数组
	 */
	public static Integer[] json2IntegerArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);
		}
		return integerArray;
	}

//	/**
//	 * 将Json格式的字符串转换成日期数组返回
//	 * 
//	 * @param jsonString
//	 *            需要进行转换的Json格式字符串
//	 * @param DataFormat
//	 *            返回的日期格式
//	 * @return 转换后的日期数组
//	 */
//	public static Date[] json2DateArray(String jsonString, String DataFormat) {
//		JSONArray jsonArray = JSONArray.fromObject(jsonString);
//		Date[] dateArray = new Date[jsonArray.size()];
//		String dateString;
//		Date date;
//		for (int i = 0; i < jsonArray.size(); i++) {
//			dateString = jsonArray.getString(i);
//			date = DateUtil.parseDate(dateString, DataFormat);
//			dateArray[i] = date;
//
//		}
//		return dateArray;
//	}

	/**
	 * 将Json格式的字符串转换成Double数组返回
	 * 
	 * @param jsonString
	 *            需要进行转换的Json格式字符串
	 * @return 转换后的Double数组
	 */
	public static Double[] json2DoubleArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);
		}
		return doubleArray;
	}
}
