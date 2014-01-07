package tracking;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestHttpClient implements Runnable {

    /**
     * @param args
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {
        TestHttpClient testHttpClient = new TestHttpClient();
        for (int i = 0; i < 10; i++) {
            new Thread(testHttpClient).start();
        }
    }

    public void run() {
    	
//    	String adPv = "http://tracker.91adv.com/tk/stat.htm?402881092ab31a9a012acb5781bb02f9;402881092b2de454012b2e57b5a10015;zhoutest_sp;402881092b2de454012b2e52077e000f;16;2;";
//    	String adPv = "http://192.168.0.164:8085/tk/adpvt.php?NL2THV570000;NL2TO7KA0005;402881092be71e2f012be72aa61c000e;NL2TL52V0002;16;2;t=http://tk.digieq.com/tk/adpv.php?NL2THV570000;NL2TO7KA0005;402881092be71e2f012be72aa61c000e;NL2TLHOV0003;16;2;";
//    	String adPv = "http://192.168.1.24/preroll/rms?workStationId=zaobao*QZzaobao-FV&lockedUserIp=1.2.3.11&timeStamp=1346991642598&userId=13469910766681i48792axx4sx8fvql029x28u2z&displayMessage=1&requestAddress=http://cdn.91adv.com/preroll/demo/jzpdemo/20120907-kmlsyy/index.html";
    	
    	/*String sitePv = "http://tracker.91adv.com/tk/s/stat?402881"
                    + "092ab31a9a012acb5781bb02f9;402881092ab31a9a012acb5d721e02fa;402881092ab31a9a012acb569"
                    + "c2e02f6_sp;402881092ab31a9a012acb5d72ab02fb;16;0;0;0";*/
//    	String ips = "192.168.1.16,192.168.1.18,192.168.1.22,192.168.1.23,192.168.1.24,192.168.1.26,192.168.1.27,192.168.1.28,192.168.1.29,192.168.1.30,192.168.1.31,192.168.1.32,192.168.1.33,192.168.1.40,192.168.1.41,192.168.1.42,192.168.1.43,192.168.1.44,192.168.1.70,192.168.1.71,192.168.1.72";
    	String ips = "60.191.57.228,60.191.57.229,60.191.57.230,60.191.57.231,60.191.57.232,60.191.57.233,60.191.57.234,60.191.57.235,60.191.57.236,60.191.57.237,60.191.57.238,60.191.57.239,";
    	
        HttpClient httpClient = new DefaultHttpClient();
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        HttpGet httpGet = null;
        HttpResponse response = null;
        HttpEntity entity = null;
        System.out.println(Thread.currentThread().getName() + " begin");

        for(String ip : ips.split(",")){
        	String adPv = "http://"+ip+"/preroll/rms?workStationId=zaobao*QZzaobao-FV&lockedUserIp=1.2.3.21&timeStamp=1346991642598&userId=13469910766681i48792axx4sx8fvql029x28u2z&displayMessage=1&requestAddress=http://cdn.91adv.com/preroll/demo/jzpdemo/20120907-kmlsyy/index.html";
        	int count = 0;
        	for(int i=0;i<10;i++){
        		httpGet = new HttpGet(adPv);
        		try {
        			response = httpClient.execute(httpGet);
        			count++;
        		} catch (ClientProtocolException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        		entity = response.getEntity();
        		httpGet.abort();
        		// System.out.println(entity.getContentLength());
        	}
        	System.out.println(ip+":发送完毕,发送次数:"+count);
        }
    }
}
