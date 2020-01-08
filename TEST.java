package mychat;

import Network.Receive;
import chat.MainFrame;
import chat.private_chatroom;
import login.MyChat;
import sign_up.Signip;

import java.io.IOException;
import java.net.Socket;

public class TEST {
    public static MyChat login;
    public static MainFrame chat;
    public static private_chatroom privatechat;
    public static int m=0;
    public static String a;
    public static String message1;
    public static String username;
    public static String senduser;
    public static String receiveuser;
    public static Socket clientsocket;
    public static Receive re;
    public static Signip sp;

    public static void createprivatechatroom(String fromid,String toid){
        privatechat = new private_chatroom(fromid,toid);
        privatechat.setVisible(true);
    }

    public static void createMainFrame() throws IOException {
        chat = new MainFrame(username);
        chat.setVisible(true);
    }
    public static void sendmessage(String id,String mess) throws IOException {
        byte[] logininfo = ("MM"+"\r\n"+id+"\r\n"+mess+"\r\n").getBytes("utf-8");
        TEST.clientsocket.getOutputStream().write(logininfo);
    }
    public static void ssendmessage(String fromid,String toid,String mess) throws IOException {
        byte[] logininfo = ("PC"+"\r\n"+fromid+"\r\n"+toid+"\r\n"+mess+"\r\n").getBytes("utf-8");
        TEST.clientsocket.getOutputStream().write(logininfo);
    }
    public static void main(String[] args){
        login = new MyChat();
    }
}
