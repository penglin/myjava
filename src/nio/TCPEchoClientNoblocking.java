package nio;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNoblocking {
	public static void main(String[] args) throws Exception {
		String server = "127.0.0.1";
		byte[] data = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
		int servPort = 8000;

		SocketChannel clntChan = SocketChannel.open();
		clntChan.configureBlocking(false);
		// 我们通过持续调用 finishConnect() 方法来“轮询”连接状态，该方法在连接成功建立之前
		// 一直返回 false 。打印操作显示了在等待连接建立的过程中，程序还可以执行其他任务。不过
		// , 这种忙等的方法非常浪费系统资源，这里这样做只是为了演示该方法的使用。
		if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
			while (!clntChan.finishConnect()) {
				System.out.print("="); // 这里可以做其他事情
			}
		}

		ByteBuffer writeBuf = ByteBuffer.wrap(data);
		ByteBuffer readBuf = ByteBuffer.allocate(data.length);

		int totalBytesRcvd = 0;

		int bytesRcvd;

		while (totalBytesRcvd < data.length) {
			if (writeBuf.hasRemaining()) {
				clntChan.write(writeBuf);
			}

			if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
				throw new SocketException("Connection closed prematurely");
			}

			totalBytesRcvd += bytesRcvd;

			System.out.print("=");
		}

		System.out.println("Recieved: "+ new String(readBuf.array(), 0, totalBytesRcvd));
		clntChan.close();

	}
}
