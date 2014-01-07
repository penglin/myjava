package hdfs;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestDFSClient {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestDFSClient.class);

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
	}
	
	@Test
	public void testDfsClient() throws IOException{
		Configuration conf = MyConf.getConfiguration();
		DFSClient client = new DFSClient(conf);
		if (logger.isInfoEnabled()) {
			logger.info("testDfsClient() - DFSClient client=" + client.toString()); //$NON-NLS-1$
		}
		
		ClientProtocol protocol = DFSClient.createNamenode(conf);
		System.out.println(protocol.getClass().toString());
		ContentSummary summary = protocol.getContentSummary("/penglin");
		System.out.println(summary.getFileCount());
		System.out.println(summary.toString());
		System.out.println(summary.getSpaceConsumed());
	}
	
	@Test
	public void testNameNode() throws IOException{
		Configuration conf = MyConf.getConfiguration();
		new File("D:\\home\\hdfs\\tmp\\dfs\\name").mkdirs();
		NameNode namenode = new NameNode(conf);
		System.out.println(namenode.getFsImageName().getName());
	}

	@Test
	public void testDataeNode() throws IOException{
		Configuration conf = MyConf.getConfiguration();
	}
}
