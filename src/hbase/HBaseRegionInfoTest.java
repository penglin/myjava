package hbase;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Writables;

public class HBaseRegionInfoTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileSystem fs = FileSystem.get(URI.create("hdfs://a6-5:9000/"),Conf.getConfiguration());
		FSDataInputStream fin = fs.open(new Path("/hbase/User_Mapping/e0d1324b837d3b7ff06ecbfa2148892f/.regioninfo"));
		byte[] b = new byte[fin.available()];
		fin.read(b);
		fin.close();

		HRegionInfo hri = Writables.getHRegionInfo(b);
		System.out.println(Bytes.toString(hri.getEndKey()));
	}

}
