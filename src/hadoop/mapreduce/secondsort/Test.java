package hadoop.mapreduce.secondsort;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

public class Test {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Test.class);

	@org.junit.Test
	public void testTextPair(){
		TextPair tp1 = new TextPair("userid1", "192.168.1.10");
		TextPair tp2 = new TextPair("userid1", "192.168.1.9");
		TextPair tp3 = new TextPair("userid2", "192.168.1.11");
		TextPair tp4 = new TextPair("userid3", "192.168.1.12");
		
		List<TextPair> list = new ArrayList<TextPair>();
		list.add(tp1);
		list.add(tp3);
		list.add(tp4);
		list.add(tp2);
		
		Collections.sort(list);

		if (logger.isInfoEnabled()) {
			logger.info("testTextPair() - List<TextPair> list=" + list); //$NON-NLS-1$
		}
	}
	
	@org.junit.Test
	public void testTextPairSerializable() throws IOException{
		TextPair tp1 = new TextPair("userid1", "192.168.1.10");
		byte[] buf = new byte[1024]; 
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(output);
		tp1.write(dataOut);
		dataOut.close();
		byte[] bytes = output.toByteArray();
		
		ByteArrayInputStream input = new ByteArrayInputStream(buf);
		DataInputStream dataIn = new DataInputStream(input);
		dataIn.read(bytes);
		dataIn.close();
		
	}
	
	@org.junit.Test
	public void testGenFile() throws IOException{
		FileOutputStream out = new FileOutputStream("output");
		Random r = new Random();
		int seed = 10;
		
		for(int i=0;i<50;i++){
			String s = "userId"+r.nextInt(seed)+"\t"+r.nextInt(seed)+"\n";
			out.write(s.getBytes());
		}
		out.flush();
		out.close();
	}
}
