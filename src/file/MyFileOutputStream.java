package file;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyFileOutputStream extends FileOutputStream{

	public MyFileOutputStream(File file, boolean append) throws FileNotFoundException {
		super(file, append);
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			System.out.println("----------");
		    security.checkWrite(file.getAbsolutePath());
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		MyFileOutputStream m = new MyFileOutputStream(new File("ttt.txt"),false);
	}
}
