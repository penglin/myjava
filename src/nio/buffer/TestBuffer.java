package nio.buffer;

import org.junit.Test;

import java.net.SocketException;
import java.nio.ByteBuffer;

public class TestBuffer {
	@Test
	public void testBuffer(){
		byte[] data = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
		ByteBuffer writeBuf = ByteBuffer.wrap(data);
		ByteBuffer readBuf = ByteBuffer.allocate(data.length);
		
		int totalBytesRcvd = 0;

		int bytesRcvd = 0;

		while (totalBytesRcvd < data.length) {

			totalBytesRcvd += bytesRcvd;

			System.out.print("=");
		}
	}
}
