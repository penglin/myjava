package hadoop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.hadoop.conf.Configuration;

public class TestConfiguration {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.addResource(new FileInputStream("my-conf.xml"));
		
		String value = conf.get("fs.default.name");
		System.out.println(value);
	}

}
