package hdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class ReadFile
{
	public static void main(String[] args) throws IOException
	{
		String path = args[0];

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create("hdfs://RS143:9000/penglin/report/part-r-00000"), conf);

		FileStatus[] listStatus = fs.listStatus(new Path("hdfs://a6-5:9000/user/hive/warehouse/panel.db/user_log/source_id=tracking/visit_date=20111218/analysis_date=20111221"));

		Path[] stat2Paths = FileUtil.stat2Paths(listStatus);

		new File(path).mkdirs();
		// 现在之前清除
		FileUtils.cleanDirectory(new File(path));

		for (int i = 0; i < stat2Paths.length; i++)
		{
			OutputStream outputStream = new FileOutputStream(path + i + ".log");

			System.out.println(MessageFormat.format("[ {0} ] {1} 开始~", i, stat2Paths[i]));

			FSDataInputStream fsin = fs.open(stat2Paths[i]);
			IOUtils.copyBytes(fsin, outputStream, 40960, true);

			outputStream.flush();
			outputStream.close();

			System.out.println(MessageFormat.format("[ {0} ] {1} 结束~", i, stat2Paths[i]));

		}

		System.out.println("全部下载完成~");
		System.out.println("开始验证条数~");

		Collection<File> listFiles = FileUtils.listFiles(new File(path), new String[] { "log" }, true);

		int linecount = 0;

		for (File file : listFiles)
		{
			linecount += FileUtils.readLines(file).size();
		}

		System.out.println("文件条数");
		System.out.println(linecount);

	}
}
