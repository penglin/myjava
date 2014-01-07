package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoProtocol implements Protocol {

	private int bufsize; // Ϊÿ���ͻ����ŵ������Ļ�������С

	public EchoProtocol(int bufsize) {

		this.bufsize = bufsize;

	}

	public void handleAccept(SelectionKey key) throws IOException {
		// channel() ��������ע��ʱ���������� Channel ���� Channel ��һ�� ServerSocketChannel ��
		// ��Ϊ��������ע���Ψһһ��֧�� accept �������ŵ���
		// accept() ����Ϊ��������ӷ���һ�� SocketChannel ʵ����
		SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();

		// �����޷�ע������ʽ�ŵ��������Ƿ�����ʽ��
		channel.configureBlocking(false);

		// ����ͨ�� SelectionKey ��� selector() ��������ȡ��Ӧ�� Selector ��
		// ���Ǹ���ָ����С������һ���µ� ByteBuffer ʵ����
		// ��������Ϊ�������ݸ� register() ������������Ϊ�������� regiter() ���������ص�
		// SelectionKey ʵ���������
		channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(bufsize));

	}

	public void handleRead(SelectionKey key) throws IOException {
		// ������֧�����ݶ�ȡ������֪������һ�� SocketChannel ��
		SocketChannel channel = (SocketChannel) key.channel();

		// �������Ӻ���һ�� ByteBuffer �����ӵ��� SelectionKey ʵ���ϣ����������������ݽ�
		// ���ڷ��͵�ʱ���õ�������ʼ���Ǹ��������������
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = channel.read(buf);

		// ��� read() �������� -1 �����ʾ�ײ������Ѿ��رգ���ʱ��Ҫ�ر��ŵ���
		// �ر��ŵ�ʱ������ѡ�����ĸ��ּ������Ƴ�����ŵ������ļ���
		if (bytesRead == -1) {
			channel.close();
		} else if (bytesRead > 0) {
			// ������Ȼ�������ŵ��Ŀɶ���������Ȼ�������п����Ѿ�û��ʣ��ռ��ˣ�
			// ��Ϊ�´λ���Ҫ�����µ�����
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	public void handleWrite(SelectionKey key) throws IOException {
		// ���ӵ� SelectionKey �ϵ� ByteBuffer ������֮ǰ���ŵ��ж�ȡ�����ݡ�
		ByteBuffer buf = (ByteBuffer) key.attachment();

		// �÷��������޸Ļ��������ڲ�״̬����ָʾ write ������ʲô�ط���ȡ���ݣ�����ʣ��������
		buf.flip();
		SocketChannel channel = (SocketChannel) key.channel(); // ��ȡ�ŵ�
		channel.write(buf); // ���ŵ���д����
		if (!buf.hasRemaining()) {
			// ���û��ʣ�����ݿɶ������޸ĸü������Ĳ�������ָʾ��ֻ�ܽ��ж�������
			key.interestOps(SelectionKey.OP_READ);
		}

		// ����������л���ʣ�����ݣ��ò�����ʣ�������Ƶ�������ǰ�ˣ���ʹ�´ε����ܶ���������ݡ�
		buf.compact();

	}

}