import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class server {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        ServerSocket ss = new ServerSocket(9595);//创建链接
        HashMap<String,Socket> cm = new HashMap<String, Socket>();存储已登陆的人
        while(true){
            Socket ns = ss.accept();一直监听是否有用户登录
            Cliop cp=new Cliop(ns, cm);
            Thread thread = new Thread(cp);
            thread.start();//启动一个新线程
        }
    }
}
class Cliop implements Runnable{//实现多线程
    Socket conn;
    Map<String, Socket> client;
    public Cliop(Socket conn, Map<String, Socket> client) {
        this.conn = conn;
        this.client = client;
    }
    @Override
    public void run() {//线程要做的工作
        try {
            while(true) {
                byte[] receive = new byte[100];
                conn.getInputStream().read(receive);
                String[] receive1 = new String(receive, "utf-8").split("\r\n");
                if (receive1[0].contains("LG")) {//登录
                    String id, password;
                    id = receive1[1];
                    password = receive1[2];
                    int a = 0,result;//判断是否重复登陆
                    if(true) {
                        mysalop mq = new mysalop();
                        result = mq.selecting1(id, password, 2);//验证用户名和密码
                        for (String name : client.keySet()) {
                            if (name.contains(id)) {//验证重复登陆
                                client.get(name).getOutputStream().write("same\r\n".getBytes("utf-8"));
                                a=1;
                                result=4;
                            }
                        }
                    }
                    System.out.println(a);
                    if (result == -1 && a==0) {
                        if (client.size() != 0) {
                            conn.getOutputStream().write("true\r\n".getBytes("utf-8"));
                            Thread.sleep(2000);
                            for (String name : client.keySet()) {//更新登录用户列表
                                conn.getOutputStream().write(("UF" + "\r\n" + name + "\r\n").getBytes("utf-8"));
                            }
                            for (String key : client.keySet()) {//更新已登录用户列表
                                client.get(key).getOutputStream().write(("ID" + "\r\n" + id + "\r\n").getBytes("utf-8"));
                            }
                        } else {
                            conn.getOutputStream().write("true\r\n".getBytes("utf-8"));
                        }
                        client.put(id, conn);//存储已登录用户

                    } else if (result == 0) {
                        conn.getOutputStream().write("false\r\n".getBytes("utf-8"));
                    }
                    } else if (receive1[0].contains("SI")) {//注册
                        String id, password;
                        id = receive1[1];
                        password = receive1[2];
                        mysalop mq = new mysalop();
                        int result = mq.selecting1(id, password, 1);
                        if (result == 1) {
                            conn.getOutputStream().write(("SI" + "\r\n" + "true\r\n").getBytes("utf-8"));
                        } else {
                            conn.getOutputStream().write(("SI" + "\r\n" + "false\r\n").getBytes("utf-8"));
                        }
                    } else if (receive1[0].contains("MM")) {群聊消息
                        String word = receive1[2];
                        String name = receive1[1];
                        String message = "MM" + "\r\n" + name + "\r\n" + word + "\r\n";
                        for (String key : client.keySet()) {
                            client.get(key).getOutputStream().write(message.getBytes("utf-8"));
                        }
                    } else if (receive1[0].contains("PC")) {私聊
                        String fromid = receive1[1];
                        String toid = receive1[2];
                        String message = receive1[3];
                        String message1 = "PC" + "\r\n" + fromid + "\r\n" + toid + "\r\n" + message + "\r\n";
                        for (String key : client.keySet()) {
                            if (key.equals(toid) || key.equals(fromid)) {
                                client.get(key).getOutputStream().write(message1.getBytes("utf-8"));
                            }

                        }
                    } else if (receive1[0].contains("XX")) {//下线
                        String exitid = receive1[1];
                        String message = "XX" + "\r\n" + exitid + "\r\n";
                        for (String key : client.keySet()) {
                            if (!key.equals(exitid)) {
                                client.get(key).getOutputStream().write(message.getBytes("utf-8"));
                            }
                        }
                        client.remove(exitid);
                    }
                }
            } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        }
    }
