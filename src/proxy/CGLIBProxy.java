package proxy;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


public class CGLIBProxy {
	@Test
	public void testCglibProxy(){
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(new RequestCtrlCallback());
		enhancer.setSuperclass(ISubJectImpl.class);
		
		ISubJectImpl subject = (ISubJectImpl) enhancer.create();
		subject.request();
	}
}

class RequestCtrlCallback implements MethodInterceptor{
	private static final Log logger = LogFactory.getLog(RequestCtrlCallback.class);
	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		logger.info("before RequestCtrlCallback.....");
		Thread.sleep(1000);
		Object retObj = proxy.invokeSuper(object, args);
		Thread.sleep(1000);
		logger.info("after RequestCtrlCallback.....");
		return retObj;
	}
	
}


class ISubJectImpl {
	public void request(){
		System.out.println("ISubJect ....");
	}
}