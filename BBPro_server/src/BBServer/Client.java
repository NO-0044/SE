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
 * 客户端
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
		System.out.println("课程名:");
		String courseid = in0.nextLine();
		System.out.println("第几次作业:");
		String hwid = in0.nextLine();
		System.out.println("作业内容:");
		String hwinfo = in0.nextLine();
		System.out.println("截止日期:");
		String ddl = in0.nextLine();
		System.out.println("附件地址:");
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
			//1.创建客户端Socket，指定服务器地址和端口
			Socket socket=new Socket("localhost", 1212);
			socket.setSoTimeout(60000);
			//2.获取输出流，向服务器端发送信息
			OutputStream os=socket.getOutputStream();//字节输出流
			//oos = new ObjectOutputStream(os);
			PrintWriter pw=new PrintWriter(os,true);//将输出流包装为打印流
			InputStream is=socket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String info="";
			System.out.println("0:login, 1:get homework 2: public homework 其它：退出");
			int a = input.nextInt();
			if(a == 0){
				pw.write("0\n");
				login(pw);
				pw.flush();
				socket.shutdownOutput();//关闭输出流
				//3.获取输入流，并读取服务器端的响应信息
				info = br.readLine();
				System.out.println("服务器说："+info);
				if(!info.equals("登陆成功")){
					break;
				}
			}
			else if(a == 1){
				if(userID == null || userID.length()<=0){
					System.out.println("请首先登陆");
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
				socket.shutdownOutput();//关闭输出流
				while((info=br.readLine())!=null){
					System.out.println("作业信息："+info);
				}
			}
			else if(a == 2){
				if(userID == null || userID.length()<=0){
					System.out.println("请首先登陆");
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
				socket.shutdownOutput();//关闭输出流
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
				System.out.println("我是客户端，服务器说："+info);
			}*/
			
			//4.关闭资源
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

