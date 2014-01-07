package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class SocketServer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 创建socket服务器对象，开始监听端口
		ServerSocket srvSock = new ServerSocket(22222);
		// 开始监听退出消息
		srvSock.setSoTimeout(2 * 60 * 60 * 1000);
		while (true)
		{
			// 设置Timeout时长为两小时
			srvSock.setSoTimeout(2 * 60 * 60 * 1000);
			Socket commSock = null;
			try
			{
				// 获得一个连接请求
				commSock = srvSock.accept();
			}
			catch (SocketTimeoutException sx)
			{
				continue;
			}
			// 设置交互超时时间为10秒
			commSock.setSoTimeout(10 * 1000);
			// 读取通讯字符串
			BufferedReader reader = new BufferedReader(new InputStreamReader(commSock.getInputStream()));
			String command;
			// 过滤空字符串情况
			while ((command = reader.readLine()) == null)
				continue;
			// 判断是否为退出命令
			if (command.toLowerCase().equals("shutdown"))
			{
				commSock.close();
				commSock = null;
				srvSock.close();
				srvSock = null;
				System.out.println("Socket Server 退出");
				break;
			}
			// 非发连接，关闭该连接
			commSock.close();
			commSock = null;
		}
	}

}
