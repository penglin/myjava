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
		
		// 只有非阻塞信道才可以注册选择器，因此需要将其配置为适当的状态
		listnChannel.configureBlocking(false);

		// 在注册过程中指出该信道可以进行 “accept” 操作
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

				// 由于 select() 操作只是向 Selector 所关联的键集合中添加元素
				// 因此，如果不移除每个处理过的键，
				// 它就会在下次调用 select() 方法时仍然保留在集合中
				// 而且可能会有无用的操作来调用它。
				keyIter.remove();
			}

		}

	}
}
