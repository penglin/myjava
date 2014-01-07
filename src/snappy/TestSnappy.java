package snappy;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.xerial.snappy.Snappy;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;
import org.xerial.util.FileResource;

public class TestSnappy {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		byte[] input = new byte[1024];
		File file = new File("flow-r-00013.snappy");
		SnappyInputStream in = new SnappyInputStream(new FileInputStream(file));
		FileOutputStream out = new FileOutputStream("snappy-uncompress.txt");
		int len = -1;
		while ((len = in.read(input)) != -1) {
			// Snappy.uncompressString(input,"UTF-8")
			out.write(input,0,len);
		}
		out.flush();
		out.close();
		in.close();
	}

	@Test
	public void testSnappyCompress() throws Exception, IOException {
		byte[] bytes = new byte[2048];
		File file = new File("Ip.txt");
		SnappyOutputStream sout = new SnappyOutputStream(new FileOutputStream("snappy.out"));
		FileInputStream in = new FileInputStream(file);

		byte[] contents = readFully(in);
		sout.write(contents);
		sout.flush();
		sout.close();
		in.close();
	}

	@Test
	public void testSnappyUnCompress() throws Exception, IOException {
		byte[] bytes = new byte[1024];
		SnappyInputStream sin = new SnappyInputStream(new FileInputStream("snappy.out"));
		FileOutputStream out = new FileOutputStream("snappy-uncompress.txt");

//		byte[] contents = readFully(sin);
		int readBytes = 0;
		while((readBytes=sin.read(bytes))!=-1){
			out.write(bytes,0,readBytes);
		}
		
		sin.close();
		out.close();
	}

	@Test
	public void read() throws Exception {
		ByteArrayOutputStream compressedBuf = new ByteArrayOutputStream();
		SnappyOutputStream snappyOut = new SnappyOutputStream(compressedBuf);
		byte[] orig = readResourceFile("Ip.txt");
		snappyOut.write(orig);
		snappyOut.close();
		byte[] compressed = compressedBuf.toByteArray();
		System.out.println("compressed size: " + compressed.length);

		SnappyInputStream in = new SnappyInputStream(new ByteArrayInputStream(compressed));
		byte[] uncompressed = readFully(in);

		assertEquals(orig.length, uncompressed.length);
		assertArrayEquals(orig, uncompressed);

	}

	public static byte[] readResourceFile(String fileName) throws IOException {
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
		assertNotNull(input);
		return readFully(input);
	}

	public static byte[] readFully(InputStream input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		for (int readBytes = 0; (readBytes = input.read(buf)) != -1;) {
			out.write(buf, 0, readBytes);
		}
		out.flush();
		return out.toByteArray();
	}
}
