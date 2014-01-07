package hbase;

import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.hfile.HFile;
import org.apache.hadoop.hbase.io.hfile.HFile.Reader;
import org.apache.hadoop.hbase.io.hfile.HFileScanner;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseHFileTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		FileSystem fs = FileSystem.get(URI.create("hdfs://a6-5:9000/"),Conf.getConfiguration());
		Path path = new Path("/hbase/User_Mapping/e0d1324b837d3b7ff06ecbfa2148892f/uid/4208965081845762527");
		HFile hfile = new HFile();
		HFile.Reader reader = new Reader(fs, path, null, false);
		reader.loadFileInfo();
		HFileScanner scanner = reader.getScanner(false, false);
		scanner.seekTo();
		while(scanner.next()){
			KeyValue keyvalue = scanner.getKeyValue();
			System.out.println(Bytes.toString(keyvalue.getFamily())+"-->"+Bytes.toString(keyvalue.getQualifier())+"-->"+Bytes.toString(keyvalue.getRow())+"-->"+Bytes.toString(keyvalue.getValue())+"");
		}
//		HFile.Reader.Scanner scanner = new HFile.Reader.Scanner(reader, false, false);
		byte[] firstRowKey = reader.getFirstRowKey();
		System.out.println(Bytes.toString(firstRowKey));
		reader.close();
	}

}
