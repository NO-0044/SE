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
			System.out.println("���ݿ�����ʧ�ܣ�");
			
		}else{
			System.out.println("���ݿ����ӳɹ�!");
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
				System.out.println("�ͻ��˵�������"+count);
				InetAddress address=socket.getInetAddress();
				System.out.println("��ǰ�ͻ��˵�IP��"+address.getHostAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
