package hdfs;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.Path;

public class TestMultiWriter extends Thread{
	static String filePath = "/penglin/multiAppend";
	static Path path = new Path(filePath);
	static FileSystem fs = null;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		fs = FileSystem.get(MyConf.getConfiguration());
		if(!fs.exists(path)){
			fs.createNewFile(path);
		}
		
		Thread t = new TestMultiWriter();
		Thread t2 = new TestMultiWriter();
		
		t.start();
		t2.start();
	}

	
	@Override
	public void run() {
		try {
			FsStatus fsStatus = fs.getStatus(path);
			
//			FSDataOutputStream out = fs.append(path);
			FSDataOutputStream out = fs.create(path, true);
			for(int i=0;i<100;i++){
				out.writeUTF(this.getName()+"-->"+i+"-->dddddddddddddddddddddddddddddddddddd\n");
			}
			out.close();
			System.out.println(this.getName()+"finished");
		} catch (Exception e) {
//			System.out.println(this.getName()+e.getMessage());
			e.printStackTrace();
			
		}
		
	}
}
