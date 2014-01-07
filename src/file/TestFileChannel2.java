package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class TestFileChannel2 {
	public static void main(String[] args) throws IOException, InterruptedException {
		/*File file = new File("ant.txt");
		System.out.println(file.canRead());;
		System.out.println(file.canWrite());;
		System.out.println(file.exists());
		RandomAccessFile in = new RandomAccessFile(file, "rw");
//		FileInputStream in = new FileInputStream(file);
		FileChannel fileChannel = in.getChannel();
		FileLock lock = fileChannel.tryLock();
		if(lock==null){
			System.out.println("ÒÑ±»¼ÝÉ± ");
			return;
		}
		System.out.println(lock.isValid());
		System.out.println(in.readLine());
		Thread.sleep(10000);
//		System.out.println(lock.isValid());
*/	
		test2();
	}
	
	public static void test(){
		SecurityManager sm = System.getSecurityManager();
		if(sm==null){
			System.out.println(sm);
			sm = new SecurityManager();
		}
//		sm.checkDelete("D:\\worksapce\\MyJava\\tt.txt");
		sm.checkRead("D:\\worksapce\\MyJava\\tt.txt");
		System.out.println("---------------");
	}
	
	public static void test2() throws IOException{
		FileInputStream in = new FileInputStream("tt.txt");
		in.read();
		in.close();
	}
}
