package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class TestFileChannel {
	public static void main(String[] args) throws IOException, InterruptedException {
		/*File file = new File("ant.txt");
		System.out.println(file.canRead());;
		System.out.println(file.canWrite());;
		System.out.println(file.exists());
		Thread.sleep(10000);
		RandomAccessFile in = new RandomAccessFile(file, "rw");
//		FileInputStream in = new FileInputStream(file);
		FileChannel fileChannel = in.getChannel();
		FileLock lock = fileChannel.tryLock();
		System.out.println(in.readLine());
		Thread.sleep(10000);
//		System.out.println(lock.isValid());
*/		//test();

		File f = new File("tffff.txt");
		System.out.println(f.getAbsoluteFile());
	}
	
	public static void test() throws IOException, InterruptedException{
		FileOutputStream out = new FileOutputStream("tt.txt");
		FileChannel fileChannel = out.getChannel();
		FileLock lock = fileChannel.tryLock();
		System.out.println(lock);
		for(int i=0;i<10;i++){
			out.write("fjsdlfjsdlf".getBytes());
			out.write("\n".getBytes());
		}
		out.flush();
		
		
		out.close();
		Thread.sleep(10000);
	}
}
