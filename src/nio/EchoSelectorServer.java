package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class EchoSelectorServer {
	private static final int BUFSIZE = 256;
	private static final int TIMEOUT = 3000;
	private static final int PORT = 8000;

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel listnChannel = ServerSocketChannel.open();
		listnChannel.socket().bind(new InetSocketAddress(PORT));
		
		// ֻ�з������ŵ��ſ���ע��ѡ�����������Ҫ��������Ϊ�ʵ���״̬
		listnChannel.configureBlocking(false);

		// ��ע�������ָ�����ŵ����Խ��� ��accept�� ����
		listnChannel.register(selector, SelectionKey.OP_ACCEPT);
		Protocol protocol = new EchoProtocol(BUFSIZE);

		while (true) {
			if (selector.select(TIMEOUT) == 0) {
				System.out.print("==");
				continue;
			}

			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while (keyIter.hasNext()) {
				SelectionKey key = keyIter.next();
				if (key.isAcceptable()) {
					protocol.handleAccept(key);
				}

				if (key.isReadable()) {
					protocol.handleRead(key);
				}

				if (key.isWritable() && key.isValid()) {
					protocol.handleWrite(key);
				}

				// ���� select() ����ֻ���� Selector �������ļ�����������Ԫ��
				// ��ˣ�������Ƴ�ÿ���������ļ���
				// ���ͻ����´ε��� select() ����ʱ��Ȼ�����ڼ�����
				// ���ҿ��ܻ������õĲ�������������
				keyIter.remove();
			}

		}

	}
}