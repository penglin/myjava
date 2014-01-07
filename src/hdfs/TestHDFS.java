package hdfs;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import cn.adsit.common.util.DateUtil;

public class TestHDFS {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
//		FileSystem fs = FileSystem.newInstance(conf);
		String basePath = "201303131003";
		FileSystem fs =  FileSystem.get(URI.create("hdfs://a6-5:9000/"), new Configuration());
		Path path = new Path("/precise/log/"+basePath);
		if(fs.exists(path)){
			FileStatus fileStatus = fs.getFileStatus(path);
			long modifyTime = fileStatus.getModificationTime();
			System.out.println(DateUtil.formateTime(new Date(modifyTime)));
			System.out.println("cunzai");
		}
		
		FileStatus[] fileStatus = fs.listStatus(path, new PathFilter(){
			@Override
			public boolean accept(Path p) {
				return p.getName().contains("advance");
			}
			
		});
//		FileStatus[] fileStatus = fs.listStatus(path);
		System.out.println(fileStatus.length);
		for(FileStatus f : fileStatus){
			if(f.getLen()>67000000){
				System.out.println(f.getPath().toString()+"---->"+f.getLen());
				fs.delete(f.getPath(), true);
			}
		}
	}
}
