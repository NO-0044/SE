package BBServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.security.auth.login.LoginContext;

/*
 * �ͻ���
 */
public class Client {
	public static String userID=null;
	public static void login(PrintWriter pw){
		Scanner in0 = new Scanner(System.in);
		System.out.println("UserID:");
		userID = in0.nextLine();
		System.out.println("Password:");
		String password = in0.nextLine();
		pw.write(userID+"\n");
		pw.write(password+"\n");
	}
	public static void getHW(PrintWriter pw){
		pw.write(userID+"\n");
		//pw.write("PB14011098");
	}
	public static void pubHW(PrintWriter pw){
		Scanner in0 = new Scanner(System.in);
		System.out.println("�γ���:");
		String courseid = in0.nextLine();
		System.out.println("�ڼ�����ҵ:");
		String hwid = in0.nextLine();
		System.out.println("��ҵ����:");
		String hwinfo = in0.nextLine();
		System.out.println("��ֹ����:");
		String ddl = in0.nextLine();
		System.out.println("������ַ:");
		String addr = in0.nextLine();
		pw.write(userID+"\n");
		pw.write(courseid+"\n");
		pw.write(hwid+"\n");
		pw.write(hwinfo+"\n");
		pw.write(ddl+"\n");
		pw.write(addr+"\n");
	}
	public static void main(String[] args) {
		//ObjectInputStream ois = null;
		//ObjectOutputStream oos = null;
		while(true){
		try {
			Scanner input = new Scanner(System.in);
			//1.�����ͻ���Socket��ָ����������ַ�Ͷ˿�
			Socket socket=new Socket("localhost", 1212);
			socket.setSoTimeout(60000);
			//2.��ȡ���������������˷�����Ϣ
			OutputStream os=socket.getOutputStream();//�ֽ������
			//oos = new ObjectOutputStream(os);
			PrintWriter pw=new PrintWriter(os,true);//���������װΪ��ӡ��
			InputStream is=socket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String info="";
			System.out.println("0:login, 1:get homework 2: public homework �������˳�");
			int a = input.nextInt();
			if(a == 0){
				pw.write("0\n");
				login(pw);
				pw.flush();
				socket.shutdownOutput();//�ر������
				//3.��ȡ������������ȡ�������˵���Ӧ��Ϣ
				info = br.readLine();
				System.out.println("������˵��"+info);
				if(!info.equals("��½�ɹ�")){
					break;
				}
			}
			else if(a == 1){
				if(userID == null || userID.length()<=0){
					System.out.println("�����ȵ�½");
					br.close();
					is.close();
					pw.close();
					os.close();
					socket.close();
					continue;
				}
				pw.write("1\n");
				getHW(pw);
				pw.flush();
				socket.shutdownOutput();//�ر������
				while((info=br.readLine())!=null){
					System.out.println("��ҵ��Ϣ��"+info);
				}
			}
			else if(a == 2){
				if(userID == null || userID.length()<=0){
					System.out.println("�����ȵ�½");
					br.close();
					is.close();
					pw.close();
					os.close();
					socket.close();
					continue;
				}
				pw.write("2\n");
				pubHW(pw);
				pw.flush();
				socket.shutdownOutput();//�ر������
				while((info=br.readLine())!=null){
					System.out.println(info);
				}
			}
			else{
				System.out.println("bye!");
				break;
			}
			//pw.println("0");
			
			/*while((info=br.readLine())!=null){
				System.out.println("���ǿͻ��ˣ�������˵��"+info);
			}*/
			
			//4.�ر���Դ
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
}

