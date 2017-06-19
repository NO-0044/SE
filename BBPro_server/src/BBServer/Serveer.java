package BBServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveer {
	public static void main(String[] args) {
		Database basedao=new Database();
		basedao.getConnection();
		if(Database.conn==null){
			System.out.println("数据库连接失败！");
			
		}else{
			System.out.println("数据库连接成功!");
		}
		try {
			ServerSocket serverSocket = new ServerSocket(1212);
			Socket socket = null;
			int count=0;
			System.out.println("***server begin***");
			while(true){
				socket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
				count++;
				System.out.println("客户端的数量："+count);
				InetAddress address=socket.getInetAddress();
				System.out.println("当前客户端的IP："+address.getHostAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
