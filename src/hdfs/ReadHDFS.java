package hdfs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IOUtils;

public class ReadHDFS {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		getHDFSFile(args);
//		test();
	}
	
	private static void getHDFSFile(String[] args) throws Exception{
		InputStream in = null;
		File f = new File("report.txt");
		if(!f.exists()){
			f.createNewFile();
		}
		
		OutputStream out = new FileOutputStream(f);
		OutputStreamWriter outs = new OutputStreamWriter(out,"GBK");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://A6-5:9000");
		conf.set("mapred.job.tracker", "hdfs://A6-5:9001");
		conf.set("hadoop.tmp.dir", "/home/hdfs/tmp");
		String inputPath = "hdfs://A6-5:9000/precise/upload/demand/201209212101/192.168.1.41_demand_201209212101_0.log";
		if(args!=null && args.length!=0)
			inputPath = args[0];
		FileSystem fs = FileSystem.get(URI.create(inputPath), conf);
		Path path = new Path(inputPath);
		in = fs.open(path);
		InputStreamReader ins = new InputStreamReader(in,"utf-8");
		BufferedReader br = new BufferedReader(ins);
//		IOUtils.copyBytes(in, out, conf, false);
//		IOUtils.closeStream(in);
		String line = null;
		while((line=br.readLine())!=null){
			outs.write(line);
			outs.write("\n");
		}
		outs.flush();
		br.close();
	}

	
	private static void test() throws IOException, ClassNotFoundException{
		FileInputStream f = new FileInputStream("report.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("report.txt")));
		String line = br.readLine();
		while(line!=null){
			ImmutableBytesWritable newWritable = new ImmutableBytesWritable(Bytes.toBytes(line));
			Result newResult = new Result(newWritable);
			System.out.println(new String(newResult.getRow()));
			line = br.readLine();
		}
		int i = -1;
	}
}
