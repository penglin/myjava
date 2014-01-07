package tracking;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class ReadContentFromUrl {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws IllegalStateException 
	 */
	public static void main(String[] args) throws IllegalStateException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
//		String url = "http://wedding.onlylady.com/a/20120306/229539.shtml";
		String url = "http://rs148:50060/tasklog?attemptid=attempt_201203261006_0004_r_000000_0&all=true";
//		String url = "http://opentest.adwin.qq.com/v1/report/getfile?user_name=adwinapi3&token=95fc31259209d4982b5012ccba41b142&report_id=6";
		readUrlContent(url);
	}
	
	private static void readUrlContent(String url) throws URISyntaxException, IllegalStateException, IOException{
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpGet post = new HttpGet();
		 post.setURI(new URI(url));
		 HttpResponse response = httpClient.execute(post);
		
		 HttpEntity entity = response.getEntity();
		 if(entity==null)
			 return;
		 InputStream input = entity.getContent();
		 BufferedReader br = new BufferedReader(new InputStreamReader(input));
		 String line = null;
		 long costTime = 0L;
		 long times = 0L;
		 while(null!=(line = br.readLine())){
			 if(line.contains("write HBase cost time")){
				 costTime += Long.parseLong(line.substring(line.lastIndexOf(":")+1));
				 times ++;
			 }
//			 System.out.println(line);
		 }
		 System.out.println(costTime+"--->"+times);
		 System.out.println(costTime/times);
	}
}
