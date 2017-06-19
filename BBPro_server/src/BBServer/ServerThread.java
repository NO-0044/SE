package BBServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerThread extends Thread{
	Socket socket = null;
	public static PreparedStatement ps;
	public static ResultSet rs;
	public ServerThread(Socket socket){
		this.socket = socket;
	}
	public void run(){
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		PrintWriter pw = null;
		String userID = "";
		String passwd = "";
		String tpasswd = "";
		try {
			//��ȡ������������ȡ�ͻ�����Ϣ
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String info=null;
			info=br.readLine();
			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			if(info.equals("0")){
				userID=br.readLine();
				passwd = br.readLine();
				System.out.println("�û���:"+userID);
				System.out.println("����:"+passwd);
				try {
					ps = Database.conn.prepareStatement("select password from USER_INFO_TABLE where userid = ?");
					ps.setString(1, userID);
					rs = ps.executeQuery();
					while (rs.next()) {
						tpasswd = rs.getString("password");
						tpasswd = tpasswd.trim();
						System.out.println("true password = "+ tpasswd);
						if(passwd.equals(tpasswd)){
							pw.write("��½�ɹ�");
						}
						else{
							pw.write("��½ʧ�ܣ�");
						}
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(info.equals("1")){
				System.out.println("getHW");
				userID=br.readLine();
				System.out.println(userID);
				try {
					ps = Database.conn.prepareStatement("select * from HW_PUB_TABLE,COURSE where HW_PUB_TABLE.courseid =COURSE.courseid and userid = ?");
					ps.setString(1, userID);
					rs = ps.executeQuery();
					while (rs.next()) {
						tpasswd = "courseid:"+rs.getString("courseid").trim()+" ";
						tpasswd = tpasswd + "hwid:" + rs.getString("hwid").trim() + " ";
						tpasswd = tpasswd + "hwinfo:" + rs.getString("hwinfo").trim() + " ";
						tpasswd = tpasswd + "ddl:" + rs.getString("ddl").trim() + " ";
						tpasswd = tpasswd + "������ַ:" + rs.getString("annexaddr").trim() + " " + "\n";
						System.out.println(tpasswd);
						pw.write(tpasswd);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(info.equals("2")){
				userID = br.readLine();
				System.out.println(userID);
				String courseid = br.readLine();
				String hwid = br.readLine();
				String hwinfo = br.readLine();
				String ddl = br.readLine();
				String addr = br.readLine();
				try {
					ps = Database.conn.prepareStatement("select position from USER_INFO_TABLE where userid = ?");
					ps.setString(1, userID);
					rs = ps.executeQuery();
					int pri=1;
					while (rs.next()) {
						pri = rs.getInt("position");
						System.out.println("Ȩ��Ϊ��"+pri);
					}
					if(pri == 1){
						pw.write("��û��Ȩ�ޣ�����ʧ��");
					}
					else{
						ps = Database.conn.prepareStatement("INSERT INTO HW_PUB_TABLE VALUES (?,?,?,?,?)");
						ps.setString(1, courseid);
						ps.setString(2, hwid);
						ps.setString(3, hwinfo);
						ps.setString(4, ddl);
						ps.setString(5, addr);
						rs = ps.executeQuery();
						pw.write("����ɹ�");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					pw.write("����ʧ��");
					e.printStackTrace();
				}
			}
			socket.shutdownInput();//�ر�������
			//��ȡ���������Ӧ�ͻ��˵�����
			//pw.write("1");
			pw.flush();//����flush()�������������

			//ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			//oos = new ObjectOutputStream(socket.getOutputStream());
			//Object object = ois.readObject();
		/*	if(info.equals("0")){
				User user = (User)object;
				System.out.println("user:"+user.getName()+ "/" + user.getPassword());
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر���Դ
			try {
				if(pw!=null)
					pw.close();
				if(os!=null)
					os.close();
				if(br!=null)
					br.close();
				if(isr!=null)
					isr.close();
				if(is!=null)
					is.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	}
}
