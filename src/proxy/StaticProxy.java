package proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


public class StaticProxy {
	@Test
	public void testStaticProxy() throws Exception{
		IRqeustProxy proxy = new IRqeustProxy(new IRequstImpl());
		proxy.request();
	}
}

class IRqeustProxy implements IRequst{
	private static final Log logger = LogFactory.getLog(StaticProxy.class);
	private IRequst request;
	
	public IRqeustProxy(IRequst request) {
		super();
		this.request = request;
	}

	@Override
	public void request() throws Exception {
		logger.info("before request.....");
		Thread.sleep(1000);
		request.request();
		Thread.sleep(1000);
		logger.info("after request.....");
	}
}

interface IRequst{
	public void request() throws Exception;
}
class IRequstImpl implements IRequst{
	public void request(){
		System.out.println("IRequst ....");
	}
}