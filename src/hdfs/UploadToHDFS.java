package hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.log4j.PropertyConfigurator;

public class UploadToHDFS {
	public static void main(String[] args) throws Exception {
		initLog4j();
		long currTime = System.currentTimeMillis();
		testZip();
		System.out.println((System.currentTimeMillis() - currTime));
		
		currTime = System.currentTimeMillis();
		testSnappy();
		System.out.println((System.currentTimeMillis() - currTime));
		
		currTime = System.currentTimeMillis();
		copyFromLocal("/usr/local/adsit/penglin/playlog.log","hdfs://RS143:9000/penglin/test/PlayLog.log");
		System.out.println((System.currentTimeMillis() - currTime));
	}
	
	private static void initLog4j() throws Exception{
		String log4jConfigurePath = "/usr/local/adsit/penglin/hbase_test/classes/log4j.properties";
		File file = new File(log4jConfigurePath);
		if (!file.exists())
			throw new Exception("日志输出Log4j配置文件指定位置<" + log4jConfigurePath + ">不存在.");
		PropertyConfigurator.configure(log4jConfigurePath);
	
	}
	
	public static void testZip() throws ClassNotFoundException, IOException{
		String codeClassName = "org.apache.hadoop.io.compress.GzipCodec";
//		String inputLocalPath = "/test/penglin/hiveTestFile/201203211444/PlayLog_131f43fcb9dc4a9a994b18c528b10593.log";
		String inputLocalPath = "/usr/local/adsit/penglin/playlog.log";
		String outputHDFSPath = "hdfs://RS143:9000/penglin/test/PlayLog.log";
		streamCompressor(codeClassName, inputLocalPath, outputHDFSPath);
		
		String inputHDFSPath = "hdfs://RS143:9000/penglin/test/PlayLog.log";
		String outputLocalPath = "/usr/local/adsit/penglin/hdfs_zip.log";
		streamDeCompressor(codeClassName, inputHDFSPath, outputLocalPath);
	}
	
	public static void testSnappy() throws ClassNotFoundException, IOException{
		String codeClassName = "org.apache.hadoop.io.compress.SnappyCodec";
//		String inputLocalPath = "/test/penglin/hiveTestFile/201203211444/PlayLog_131f43fcb9dc4a9a994b18c528b10593.log";
		String inputLocalPath = "/usr/local/adsit/penglin/playlog.log";
		String outputHDFSPath = "hdfs://RS143:9000/penglin/test/PlayLog.log";
		streamCompressor(codeClassName, inputLocalPath, outputHDFSPath);
		
		String inputHDFSPath = "hdfs://RS143:9000/penglin/test/PlayLog.log";
		String outputLocalPath = "/usr/local/adsit/penglin/hdfs_snappy.log";
		streamDeCompressor(codeClassName, inputHDFSPath, outputLocalPath);
	}
	
	/**
	 * 压缩
	 * @param codeClassName 压缩使用的压缩类
	 * @param inputLocalPath 本地文件路径(可以是目录)
	 * @param outputHDFSPath HDFS文件路径
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void streamCompressor(String codeClassName, String inputLocalPath, String outputHDFSPath) throws ClassNotFoundException, IOException{
		Class<?> codeClass = Class.forName(codeClassName);
		Configuration conf = new Configuration();
		CompressionCodec compress = (CompressionCodec) ReflectionUtils.newInstance(codeClass, conf);
		outputHDFSPath = outputHDFSPath + compress.getDefaultExtension();
		FileSystem fs = FileSystem.get(URI.create(outputHDFSPath), conf);
		Path path = new Path(outputHDFSPath);
		System.out.println("output file exists:"+fs.exists(path));
		if(!fs.exists(path)){
			boolean flag = fs.createNewFile(path);
			System.out.println("create output file:"+flag);
		}
		
		OutputStream out = fs.create(path);
		CompressionOutputStream compressOut = compress.createOutputStream(out);
		
		File localFile = new File(inputLocalPath);
		if(localFile.isDirectory()){
			for(File file : localFile.listFiles()){
				InputStream in = new FileInputStream(file);
				
				IOUtils.copyBytes(in, compressOut, conf,false);
				compressOut.flush();
				
				IOUtils.closeStream(in);
			}
		}else{
			InputStream in = new FileInputStream(inputLocalPath);
			
			IOUtils.copyBytes(in, compressOut, conf,false);
			compressOut.flush();
			
			IOUtils.closeStream(in);
		}
		
		IOUtils.closeStream(compressOut);
	}
	
	public static void streamDeCompressor(String codeClassName, String inputHDFSPath, String outputLocalPath) throws ClassNotFoundException, IOException{
		Class<?> codeClass = Class.forName(codeClassName);
		Configuration conf = new Configuration();
		CompressionCodec compress = (CompressionCodec) ReflectionUtils.newInstance(codeClass, conf);
		
		inputHDFSPath = inputHDFSPath + compress.getDefaultExtension();
		FileSystem fs = FileSystem.get(URI.create(inputHDFSPath), conf);
		Path path = new Path(inputHDFSPath);
		FSDataInputStream in = fs.open(path);
		OutputStream out = new FileOutputStream(outputLocalPath);
		
		CompressionInputStream  compressIn = compress.createInputStream(in);
		
		IOUtils.copyBytes(compressIn, out, conf);
		
		IOUtils.closeStream(out);
		IOUtils.closeStream(compressIn);
	}
	
	public static void copyFromLocal(String inputLocalPath, String outputHDFSPath) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(outputHDFSPath), new Configuration());
		Path path = new Path(outputHDFSPath);
		System.out.println("output file exists:"+fs.exists(path));
		if(fs.exists(path)){
			fs.delete(path, true);
		}
		System.out.println(path.getParent());
		
		Path srcPath = new Path(inputLocalPath);
		
		fs.copyFromLocalFile(false, srcPath, path);
	}
}
