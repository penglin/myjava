package url;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class KindsOfUrlTest {
	@Test
	public void test1(){
		Pattern pattern = Pattern.compile("localhost|127.0.0.1|10.16.2.60|10.10.126.181");
		Matcher matcher = pattern.matcher("http://126.0.0.1:8080/jdfjsk/ddd");
		if(matcher.find()){
			System.out.println("dddd");
		}
	}
}
