package hdfs;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ScanFile
{
	private static FileSystem fs;

	public static void main(String[] args) throws IOException
	{
		String path = "hdfs://a6-7:10000/user/hive/warehouse/penglin_test.db/user_log/";

		FileSystem fs = FileSystem.get(URI.create(path), new Configuration());

		ScanFile(fs, new Path(URI.create(path)));
	}

	/**
	 * 
	 *		String dst = "hdfs://a6-5:9000/user/hive/warehouse/panel.db/user_log/source_id=tracking/visit_date=20111218";
			//		String dst = "hdfs://a6-5:9000/user/hive/warehouse/panel.db/user_log/source_id=tracking/visit_date=20111218/analysis_date=20111221";

			Configuration conf = new Configuration();

			FileSystem fs = FileSystem.get(URI.create(dst), conf);

			FileStatus fileList[] = fs.listStatus(new Path(dst));

			int size = fileList.length;

			for (int i = 0; i < size; i++)
			{

				System.out.println("name:" + fileList[i].getPath().getName() + "\t\tsize:" + fileList[i].getLen());

			}

			fs.close(); 
	 * @param fs 
	 * @throws IOException 
	 * 
	 */

	public static void ScanFile(FileSystem fs, Path path) throws IOException
	{
		FileStatus[] listStatus = fs.listStatus(path);

		for (FileStatus fileStatus : listStatus)
		{

			if (fileStatus.isDir())
			{
				System.out.println(MessageFormat.format("[ {0} ]", fileStatus.getPath()));
				ScanFile(fs, fileStatus.getPath());
			}

		}

	}
}
