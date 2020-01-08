package login;

import Network.Receive;
import mychat.TEST;
import sign_up.Signip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class MyChat extends JFrame implements ActionListener{
    private static final int FRAME_WIDTH=300;
    private static final int FRAME_HEIGHT=180;
    private static final int BUTTON_WIDTH=80;
    private static final int BUTTON_HEIGHT=40;
    rebutton sigh_in,sigh_up;
    JTextField id;
    JPasswordField password;
    public MyChat(){
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setResizable(false);
        setTitle("MyChat");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        ImageIcon icon=new ImageIcon("F:\\javayuan\\mychat\\lib\\timg.jpg");
        setIconImage(icon.getImage());
        JPanel jp=new JPanel(new GridLayout(3,1));
        JPanel jp1=new JPanel();
        Font f = new Font("微软雅黑",Font.PLAIN,13);
        JLabel label1=new JLabel("账号");
        id=new JTextField(12);
        label1.setFont(f);
        jp1.add(label1);
        jp1.add(id);
        jp.add(jp1);
        JPanel jp2=new JPanel();
        JLabel label2=new JLabel("密码");
        password=new JPasswordField(12);
        label2.setFont(f);
        jp2.add(label2);
        jp2.add(password);
        jp.add(jp2);
        JPanel jp3=new JPanel();
        sigh_in=new rebutton("sigh in");
        sigh_in.addActionListener(this);
        sigh_up=new rebutton("sigh up");
        sigh_up.addActionListener(this);
        jp3.add(sigh_in);
        jp3.add(sigh_up);
        jp.add(jp3);
        add(jp);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent event){
        JButton clickButton=(JButton)event.getSource();
        try {
            TEST.clientsocket = new Socket("localhost",9595);
            TEST.re = new Receive(TEST.clientsocket);
            Thread thread = new Thread(TEST.re);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(clickButton.equals(sigh_in)){
                String id1=id.getText();
                TEST.username=id1;
                System.out.println(id1);
                String key=new String(password.getPassword());
                System.out.println(key);
                try {
                    byte[] logininfo = ("LG"+"\r\n"+id1+"\r\n"+key+"\r\n").getBytes("utf-8");
                    TEST.clientsocket.getOutputStream().write(logininfo);
                    TEST.clientsocket.getOutputStream().flush();
                    Thread.sleep(1000);
                    System.out.println(TEST.a);
                }catch (IOException | InterruptedException e){
                    e.printStackTrace();
                }
            }else if(clickButton.equals(sigh_up)){
                setVisible(false);
                TEST.sp=new Signip();
                dispose();
            }
    }
    public static void createMainFrame(String a) throws IOException {
        if(a.equals("true")){
            TEST.login.setVisible(false);
            TEST.createMainFrame();
            TEST.login.dispose();
        }else{
            TEST.clientsocket.close();
            JOptionPane.showMessageDialog(TEST.login,"invalid!","message",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
