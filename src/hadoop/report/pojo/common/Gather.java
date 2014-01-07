package hadoop.report.pojo.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.io.Text;

public class Gather {
	public static final String SPLIT_STRING = new String(new char[]{1});;
	
	public String[] gatherKeys = null;
	public String[] pointers = null;
	public String keyMethodPrefix = "get";
	
	public String family = "GatherProperties";
	
	public int compareToUseReflect(Gather gather, String propertyName, Class<?>... parameterTypes) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method method = this.getClass().getDeclaredMethod(propertyName, parameterTypes);
		Method method2 = gather.getClass().getDeclaredMethod(propertyName, parameterTypes);
		Object[] objs = null;
		Object value = method.invoke(this, objs);
		Object value2 = method2.invoke(gather, objs);
		
		if(value==null && value2==null)
			return -1;
		
		if(value  == null)
			value = "";
		if(value2 == null)
			value2 = "";
		
		return value.toString().compareTo(value2.toString());
	}
	
	public int compareTo(Gather gather){
		Method[] methods = this.getClass().getDeclaredMethods();
		
		int result = 0;
		for(Method method : methods){
			String methodName = method.getName();
			if(!methodName.startsWith("get")){
				continue;
			}
			
			Class<?> returnType = method.getReturnType();
			if(!returnType.equals(Text.class))
				continue;
			
			try {
				result = this.compareToUseReflect(gather, methodName, method.getParameterTypes());
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			if(result!=0)
				return result;
		}
		
		return 0;
	}
	
	public String getGatherKey(){
		String[] gatherKeys = this.getGatherKeys();
		StringBuffer sb = new StringBuffer();
		for(String key : gatherKeys){
			String propertyName = this.getKeyMethodPrefix() + key;
			try {
				Method method = this.getClass().getDeclaredMethod(propertyName, new Class[]{});
				Object obj = method.invoke(this, new Object[]{});
				sb.append(obj.toString());
				sb.append("$");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public String toString(){
		Field[] fields = this.getClass().getDeclaredFields();
		
		StringBuffer sb = new StringBuffer();
		for(Field field : fields){
			String fieldName = field.getName();
			String methodName = keyMethodPrefix + firstStringToUpperCase(fieldName);
			
			try {
				Method method = this.getClass().getDeclaredMethod(methodName, new Class[]{});
				Object[] objs = null;
				Object value = method.invoke(this, objs);
				Class<?> returnType = method.getReturnType();
				if(returnType.equals(Long.class)){
					sb.append(value==null?0L:value.toString());
				}else if(returnType.equals(Integer.class)){
					sb.append(value==null?0:value.toString());
				}else{
					sb.append(value==null?"":value.toString());
				}
				sb.append(SPLIT_STRING);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		sb.append(" ");
		return sb.toString();
	}
	
	private String firstStringToUpperCase(String string){
		if(string==null||string.trim().isEmpty())
			return string;
			
		String c = string.substring(0, 1);
		if(string.length()==1)
			return c.toUpperCase();
		return c.toUpperCase() + string.substring(1);
	}

	public void initIncrement(Increment increment, String family, Object gather, String[] pointers){
		if(gather==null)
			return ;
		
		for(String pointer : pointers){
			String methodName = keyMethodPrefix + pointer;
			
			try {
				Method method = this.getClass().getDeclaredMethod(methodName, new Class[]{});
				Object[] objs = null;
				Object value = method.invoke(gather, objs);
				
				if(value==null)
					continue;
				
				increment.addColumn(family.getBytes(), pointer.getBytes(), Long.parseLong(value.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public Gather() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Gather(String valueString){
		String[] splits = valueString.split(SPLIT_STRING);
		Field[] fields = this.getClass().getDeclaredFields();
		int i = 0;
		for(Field field : fields){
			String fieldName = field.getName();
			int modifier = field.getModifiers();
			boolean flag = Modifier.isStatic(modifier);
			if(flag)
				continue;
			
			String value = splits[i++];
			String methodName = "set"+firstStringToUpperCase(fieldName);
			
			try {
				Method  method = this.getClass().getDeclaredMethod(methodName, new Class[]{field.getType()});
				if(field.getType().equals(Long.class)){
					method.invoke(this, Long.parseLong(value));
				}else if(field.getType().equals(Integer.class)){
					method.invoke(this, Integer.parseInt(value));
				}else{
					method.invoke(this, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public String[] getGatherKeys() {
		return gatherKeys;
	}

	public void setGatherKeys(String[] gatherKeys) {
		this.gatherKeys = gatherKeys;
	}

	public String getKeyMethodPrefix() {
		return keyMethodPrefix;
	}

	public void setKeyMethodPrefix(String keyMethodPrefix) {
		this.keyMethodPrefix = keyMethodPrefix;
	}

	public String[] getPointers() {
		return pointers;
	}

	public void setPointers(String[] pointers) {
		this.pointers = pointers;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}
}
