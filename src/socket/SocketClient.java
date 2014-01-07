package socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {
	public static void main(String[] args) throws Exception, IOException {
		Socket commSock = new Socket("127.0.0.1", 22222);
		// 得到输出流
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(commSock.getOutputStream()));
		// 发出退出命令
		writer.write("shutdown");
		writer.flush();
		commSock.close();
	}
}
