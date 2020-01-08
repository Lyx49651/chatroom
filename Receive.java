package Network;

import chat.MainFrame;
import chat.private_chatroom;
import chat.retreenode;
import login.MyChat;
import mychat.TEST;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {
    Socket conn;
    public Receive(Socket s){
        conn=s;
    }
    byte[] mess = new byte[100];
    @Override
    public void run() {
        while(true){
            try {
                int j=0;
                conn.getInputStream().read(mess);
                String[] receive1=new String(mess,"utf-8").split("\r\n");
                System.out.println(receive1[0]);
                if(receive1[0].contains("MM")){
                    TEST.senduser=receive1[1];
                    TEST.message1=receive1[2];
                    MainFrame.say(TEST.message1);
                }else if(receive1[0].contains("ID")){
                    String onlineuser=receive1[1];
                    retreenode friend=new retreenode(onlineuser,1);
                    MainFrame.friends.add(friend);
                }else if(receive1[0].contains("UF")){
                    String onlineuser=receive1[1];
                    retreenode friend=new retreenode(onlineuser,1);
                    MainFrame.friends.add(friend);
                }else if(receive1[0].contains("true")){
                    TEST.a="true";
                    MyChat.createMainFrame(TEST.a);
                }else if(receive1[0].contains("false")||receive1[0].contains("invalid")){
                    TEST.a="false";
                    MyChat.createMainFrame(TEST.a);
                }else if(receive1[0].contains("PC")){
                    if(TEST.m==0){
                        TEST.senduser=receive1[1];
                        TEST.receiveuser=receive1[2];
                        TEST.message1=receive1[3];
                        TEST.createprivatechatroom(receive1[1],receive1[2]);
                        private_chatroom.say();
                        TEST.m++;
                    }else{
                        TEST.senduser=receive1[1];
                        TEST.receiveuser=receive1[2];
                        TEST.message1=receive1[3];
                        private_chatroom.say();
                    }
                }else if(receive1[0].contains("XX")){
                    int n=MainFrame.friends.getChildCount(),m=0;
                    String exitid=receive1[1];
                    while(n>0){
                        retreenode test= (retreenode) MainFrame.friends.getChildAt(m);
                        if(exitid.equals(test.getNickName())){
                            MainFrame.friends.remove(m);
                        }
                        n--;
                        m++;
                    }
                }else if(receive1[0].contains("SI")){
                    System.out.println(receive1[1]);
                    if(receive1[1].equals("true")){
                        JOptionPane.showMessageDialog(TEST.sp,"create successful!","message",JOptionPane.INFORMATION_MESSAGE);
                        TEST.sp.setVisible(false);
                        TEST.login=new MyChat();
                        TEST.sp.dispose();
                    }else{
                        JOptionPane.showMessageDialog(TEST.sp,"user name was used!","message",JOptionPane.INFORMATION_MESSAGE);
                    }
                }else if(receive1[0].contains("same")){
                    JOptionPane.showMessageDialog(TEST.login,"this user was login!","message",JOptionPane.INFORMATION_MESSAGE);
                }
                while(j<receive1.length){
                    receive1[j]="";
                    j++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
