package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		
		prop.load(new InputStreamReader(new FileInputStream("ttt.properties")));
		String value = prop.getProperty("SpecialAreaZone.TrackAdPos");
		System.out.println(new String(value.getBytes("ISO-8859-1"),"GBK"));
		
		File file = new File("D:\\worksapce\\MyJava\\ttt.properties");
		System.out.println(file.getPath());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getName());
	}

}
