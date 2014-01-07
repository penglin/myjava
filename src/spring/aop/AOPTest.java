package spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class AOPTest {
	@Test
	public void testCGLIB(){
		
	}
	
	@Test
	public void testDynamicProxy(){
		ISubJect subject = (ISubJect) Proxy.newProxyInstance(AOPTest.class.getClassLoader(), new Class[]{ISubJect.class}, new RequestCtrlInvocationHandler(new ISubJectImpl()));
		subject.request();
	}
}


class RequestCtrlInvocationHandler implements InvocationHandler{
	private static final Log logger = LogFactory.getLog(RequestCtrlInvocationHandler.class);
	private Object target;
	
	public RequestCtrlInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(proxy.getClass().getName());
		if(method.getName().equals("request")){
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if(hour>=0&&hour<6){
				logger.warn("service is not available now.");
				return null;
			}
			return method.invoke(target, args);
		}
		return null;
	}
}

interface ISubJect{
	public void request();
}
class ISubJectImpl implements ISubJect{
	public void request(){
		System.out.println("ISubJect ....");
	}
}