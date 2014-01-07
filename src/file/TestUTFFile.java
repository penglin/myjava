package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class TestUTFFile {

	public static void main(String[] args) {
		File file = new File("utf8.txt");
		String str = "大家好才是真的好";
		try {
			OutputStream out = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
//			out.write(new String(str.getBytes(),"utf-8").getBytes());
			bw.write(str);
			bw.flush();
			
//			out.write(str.getBytes());
//			out.flush();
			out.close();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File testFile = new File("file:///C:/Documents and Settings/Administrator/桌面/reportin html/index.htm");
		System.out.println(testFile.exists());
		System.out.println(testFile.getAbsolutePath());
	}
}
