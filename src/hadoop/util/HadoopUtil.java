package hadoop.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HadoopUtil {
	/** 
	 * 为Mapreduce添加第三方jar包 
	 *  
	 * @param jarPath 
	 *            举例：D:/Java/new_java_workspace/scm/lib/guava-r08.jar 
	 * @param conf 
	 * @throws IOException 
	 */  
	public static void addTmpJar(String jarPath, Configuration conf) throws IOException {  
	    System.setProperty("path.separator", ":");  
	    FileSystem fs = FileSystem.getLocal(conf);  
	    String newJarPath = new Path(jarPath).makeQualified(fs).toString();  
	    String tmpjars = conf.get("tmpjars");  
	    if (tmpjars == null || tmpjars.length() == 0) {  
	        conf.set("tmpjars", newJarPath);  
	    } else {  
	        conf.set("tmpjars", tmpjars + "," + newJarPath);  
	    }  
	}  
}
