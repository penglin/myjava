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
		// ����ͨ���������� finishConnect() ����������ѯ������״̬���÷��������ӳɹ�����֮ǰ
		// һֱ���� false ����ӡ������ʾ���ڵȴ����ӽ����Ĺ����У����򻹿���ִ���������񡣲���
		// , ����æ�ȵķ����ǳ��˷�ϵͳ��Դ������������ֻ��Ϊ����ʾ�÷�����ʹ�á�
		if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
			while (!clntChan.finishConnect()) {
				System.out.print("="); // �����������������
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