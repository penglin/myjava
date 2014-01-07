/**
 * JSON���߶���
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
 * JSON���߶���
 */
public class JsonUtil
{
	/**
	 * JSON������չ������ƣ�����
	 */
	private static final String JSON_OBJECT_TAG_FOR_TYPE = "JSONUTIL-FOR-TYPE";

	/**
	 * JSON������չ������ƣ�������
	 */
	private static final String JSON_OBJECT_TAG_FOR_NAME = "JSONUTIL-FOR-NAME";

	/**
	 * JSON������չ������ƣ��򵥶���ֵ
	 */
	private static final String JSON_OBJECT_TAG_FOR_VALUE = "JSONUTIL-FOR-VALUE";

	/**
	 * JSON�������ͣ���
	 */
	private static final int JSON_OBJECT_TYPE_SIMPLE = 1;

	/**
	 * JSON�������ͣ�����
	 */
	private static final int JSON_OBJECT_TYPE_COLLECTION = 2;

	/**
	 * JSON�������ͣ�MAP
	 */
	private static final int JSON_OBJECT_TYPE_MAP = 4;

	/**
	 * JSON�������ͣ�����
	 */
	private static final int JSON_OBJECT_TYPE_COMPLEX = 8;

	/**
	 * ת��ָ������ΪJSON����
	 * 
	 * @param object ����
	 * @return JSON����
	 * @throws Exception �����쳣
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject toJson(Object object) throws Exception
	{
		// ��֤������Ч����
		if (object == null)
			return null;
		// ����JSON����
		JSONObject jsonObject = new JSONObject();
		// ���ݶ������ͣ��ֱ���
		if (object instanceof String || object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double)
		{
			// ����������,ֱ�Ӵ洢ֵ
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_SIMPLE);
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_VALUE, object);
			return jsonObject;
		}
		else if (object instanceof Collection)
		{
			// ������������,�Ѽ�����ÿ������ֱ����棬���洢��Array��
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_COLLECTION);
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
			// ���б�����JSONArray��
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
			// MAP�������ͣ����ÿ��[Key,Value]ֵ�Է���Array�У�������е�ֵ����Array����
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_MAP);
			jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
			// ����MAP,��ÿһ��[Key,Value]����һ��JSONArray��,�������һ��JSONArray��װ
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
		// ���Ӷ������ͽ���,����JSON����TAG
		jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE, JsonUtil.JSON_OBJECT_TYPE_COMPLEX);
		jsonObject.put(JsonUtil.JSON_OBJECT_TAG_FOR_NAME, object.getClass().getName());
		// �����෴����ƻ�ȡ������Ϣ��������
		String fieldName = null;
		Class fieldType = null;
		String fieldTypeName = null;
		Map<String, Integer> fieldTypeInterfaces = new HashMap<String, Integer>();
		Object fieldValue = null;
		Method method = null;
		// �����༰�������������ֵ
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
				// �õ����Զ�Ӧ��ȡֵ����
				try
				{
					method = cls.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
				}
				catch (Exception e)
				{
					continue;
				}
				// �õ�����ֵ
				fieldValue = method.invoke(object);
				// �������ֵΪnull,���ж�����
				if (fieldValue == null)
					continue;
				// ȡֵ������
				// ����������
				if (fieldTypeName.equals("java.lang.String") || fieldTypeName.equals("java.lang.Integer") || fieldTypeName.equals("java.lang.Long")
						|| fieldTypeName.equals("java.lang.Float") || fieldTypeName.equals("java.lang.Double"))
				{
					jsonObject.put(fieldName, fieldValue);
					continue;
				}
				// �б���������
				if (fieldTypeInterfaces.get("java.util.Collection") != null)
				{
					// �������Ϊ�գ�����������JSON���л��ַ���
					if (((Collection) fieldValue).isEmpty())
						continue;
					jsonObject.put(fieldName, JsonUtil.toJson(fieldValue));
					continue;
				}
				// MAP��������
				if (fieldTypeInterfaces.get("java.util.Map") != null)
				{
					// �������Ϊ�գ�����������JSON���л��ַ���
					if (((Map) fieldValue).isEmpty())
						continue;
					jsonObject.put(fieldName, JsonUtil.toJson(fieldValue));
					continue;
				}
			}
			// �õ�����
			cls = cls.getSuperclass();
		}
		// ����
		return jsonObject;
	}

	/**
	 * ��ָ��JSON����ָ�ʵ�ʶ���
	 * 
	 * @param jsonObject JSON����
	 * @return ʵ�ʶ���
	 * @throws Exception �����쳣
	 */
	@SuppressWarnings("unchecked")
	public static Object fromJson(JSONObject jsonObject) throws Exception
	{
		// �ж�JSON������Ч��
		if (jsonObject == null || jsonObject.isNullObject())
			return null;
		// �õ�JSON�������͡����ƺ�ֵ
		Integer jsonObjectType = (Integer) jsonObject.get(JsonUtil.JSON_OBJECT_TAG_FOR_TYPE);
		String jsonObjectName = jsonObject.getString(JsonUtil.JSON_OBJECT_TAG_FOR_NAME);
		Object jsonObjectValue = jsonObject.get(JsonUtil.JSON_OBJECT_TAG_FOR_VALUE);
		if (jsonObjectType == null || jsonObjectName == null)
			return null;
		// ���ݲ�ͬ�������ͣ��ֱ���д���
		if (jsonObjectType.intValue() == JsonUtil.JSON_OBJECT_TYPE_SIMPLE)
		{
			// JSON�����Ǽ����ͣ�ֱ��ȡֵ
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
			// ������������,��Array�л�ԭ������ÿ������,������Collection�з���
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
			// MAP��������,��Array�л�ԭÿ�����ݣ�������Map�з���
			JSONArray jsonArray = (JSONArray) jsonObjectValue;
			Map map = (Map) Class.forName(jsonObjectName).newInstance();
			for (int i = 0; i < jsonArray.size(); i++)
			{
				JSONArray jsonPairArray = jsonArray.getJSONArray(i);
				map.put(JsonUtil.fromJson(jsonPairArray.getJSONObject(0)), JsonUtil.fromJson(jsonPairArray.getJSONObject(1)));
			}
			return map;
		}
		// JSON����Ϊ��������
		String objectName = jsonObject.getString(JsonUtil.JSON_OBJECT_TAG_FOR_NAME);
		Class cls = Class.forName(objectName);
		Object object = cls.newInstance();
		// �����෴����ƻ�ȡ������Ϣ��������
		String fieldName = null;
		Class fieldType = null;
		String fieldTypeName = null;
		Map<String, Integer> fieldTypeInterfaces = new HashMap<String, Integer>();
		Object fieldValue = null;
		String methodName = null;
		Method method = null;
		// �õ�ָ���������嵥��ֵ
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
					// JSON����������ֵ
					fieldValue = jsonObject.get(fieldName);
					if (fieldValue == null)
						continue;
					// �õ����Զ�Ӧ��ȡֵ����
					methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					method = cls.getDeclaredMethod(methodName, new Class[] { Class.forName(fieldType.getName()) });
				}
				catch (Exception e)
				{
					continue;
				}
				// ����������
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
				// ������������
				if (fieldTypeInterfaces.get("java.util.Collection") != null)
				{
					method.invoke(object, new Object[] { JsonUtil.fromJson(jsonObject.getJSONObject(fieldName)) });
					continue;
				}
				// MAP��������
				if (fieldTypeInterfaces.get("java.util.Map") != null)
				{
					method.invoke(object, new Object[] { JsonUtil.fromJson(jsonObject.getJSONObject(fieldName)) });
					continue;
				}
			}
			// �õ�ָ����ĳ���
			cls = cls.getSuperclass();
		}
		// ����
		return object;
	}

	/**
	 * ָ������ת����JSON��ʽ�ַ���
	 * 
	 * @param object Ŀ�����
	 * @return JSON��ʽ�ַ���
	 * @throws Exception �����쳣
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
	 * ת��JSON��ʽ�ַ���ΪĿ�����
	 * 
	 * @param jsonDescriptor JSON��ʽ�ַ���
	 * @return Ŀ�����
	 * @throws Exception �����쳣
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
	 * ��һ��ʵ�������ת����Json���ݸ�ʽ
	 * 
	 * @param bean
	 *            ��Ҫת����ʵ�������
	 * @return ת�����Json��ʽ�ַ���
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
	 * ��һ��List����ת����Json���ݸ�ʽ����
	 * 
	 * @param list
	 *            ��Ҫ����ת����List����
	 * @return ת�����Json���ݸ�ʽ�ַ���
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
	 * ��һ����������ת����Json���ݸ�ʽ����
	 * 
	 * @param array
	 *            ��Ҫ����ת�����������
	 * @return ת�����Json���ݸ�ʽ�ַ���
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
	 * ��һ��Map����ת����Json���ݸ�ʽ����
	 * 
	 * @param map
	 *            ��Ҫ����ת����Map����
	 * @return ת�����Json���ݸ�ʽ�ַ���
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
	 * ��һ��Set����ת����Json���ݸ�ʽ����
	 * 
	 * @param set
	 *            ��Ҫ����ת����Set����
	 * @return ת�����Json���ݸ�ʽ�ַ���
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
	 * ��Json��ʽ���ַ���ת����ָ���Ķ��󷵻�
	 * 
	 * @param jsonString
	 *            Json��ʽ���ַ���
	 * @param pojoCalss
	 *            ת����Ķ�������
	 * @return ת����Ķ���
	 */
	@SuppressWarnings("unchecked")
	public static Object json2Object(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * ��Json��ʽ���ַ���ת����Map<String,Object>���󷵻�
	 * 
	 * @param jsonString
	 *            ��Ҫ����ת����Json��ʽ�ַ���
	 * @return ת�����Map<String,Object>����
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
	 * ��Json��ʽ���ַ���ת���ɶ������鷵��
	 * 
	 * @param jsonString
	 *            ��Ҫ����ת����Json��ʽ�ַ���
	 * @return ת����Ķ�������
	 */
	public static Object[] json2ObjectArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * ��Json��ʽ���ַ���ת����ָ��������ɵ�List����
	 * 
	 * @param jsonString
	 *            Json��ʽ���ַ���
	 * @param pojoClass
	 *            ת�����List�ж�������
	 * @return ת�����List����
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
	 * ��Json��ʽ���ַ���ת�����ַ������鷵��
	 * 
	 * @param jsonString
	 *            ��Ҫ����ת����Json��ʽ�ַ���
	 * @return ת������ַ�������
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
	 * ��Json��ʽ���ַ���ת����Long���鷵��
	 * 
	 * @param jsonString
	 *            ��Ҫ����ת����Json��ʽ�ַ���
	 * @return ת�����Long����
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
	 * ��Json��ʽ���ַ���ת����Integer���鷵��
	 * 
	 * @param jsonString
	 *            ��Ҫ����ת����Json��ʽ�ַ���
	 * @return ת�����Integer����
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
//	 * ��Json��ʽ���ַ���ת�����������鷵��
//	 * 
//	 * @param jsonString
//	 *            ��Ҫ����ת����Json��ʽ�ַ���
//	 * @param DataFormat
//	 *            ���ص����ڸ�ʽ
//	 * @return ת�������������
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
	 * ��Json��ʽ���ַ���ת����Double���鷵��
	 * 
	 * @param jsonString
	 *            ��Ҫ����ת����Json��ʽ�ַ���
	 * @return ת�����Double����
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