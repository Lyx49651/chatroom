package sign_up;

import login.MyChat;
import login.rebutton;
import mychat.TEST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Signip extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH=300;
    private static final int FRAME_HEIGHT=200;
    private static final int BUTTON_WIDTH=80;
    private static final int BUTTON_HEIGHT=40;
    private static final int LABEL_WIDTH=80;
    private static final int LABEL_HEIGHT=40;
    private rebutton ok,cancel;
    private JTextField id;
    private JPasswordField password,repassword;

    public Signip(){
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setResizable(false);
        setTitle("Sign up");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon=new ImageIcon("F:\\javayuan\\mychat\\lib\\timg.jpg");
        setIconImage(icon.getImage());
        JPanel jp=new JPanel(new GridLayout(4,1));
        Font f = new Font("微软雅黑",Font.PLAIN,13);
        ok=new rebutton("ok");
        cancel=new rebutton("cancel");
        JLabel label1= new JLabel("账号");
        JLabel label2=new JLabel("密码");
        JLabel label3=new JLabel("密码");
        label1.setFont(f);
        label2.setFont(f);
        label3.setFont(f);
        JPanel jp1=new JPanel();
        id=new JTextField(12);
        jp1.add(label1);
        jp1.add(id);
        jp.add(jp1);
        JPanel jp2=new JPanel();
        password=new JPasswordField(12);
        jp2.add(label2);
        jp2.add(password);
        jp.add(jp2);
        JPanel jp3=new JPanel();
        repassword=new JPasswordField(12);
        jp3.add(label3);
        jp3.add(repassword);
        jp.add(jp3);
        JPanel jp4=new JPanel();
        ok.addActionListener(this);
        cancel.addActionListener(this);
        jp4.add(ok);
        jp4.add(cancel);
        jp.add(jp4);
        add(jp);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickButton=(JButton)e.getSource();
        if(clickButton.equals(ok)){
            String id1=id.getText();
            String key=new String(password.getPassword());
            String key1=new String(repassword.getPassword());
            if(id1.length()<=0){
                JOptionPane.showMessageDialog(this,"id or password is null","message",JOptionPane.ERROR_MESSAGE);
            }
            int message;
            if(key.equals(key1)&&key.length()>0&&key1.length()>0){
               try{
                   byte[] logininfo = ("SI"+"\r\n"+id1+"\r\n"+key+"\r\n").getBytes("utf-8");
                   TEST.clientsocket.getOutputStream().write(logininfo);
                   TEST.clientsocket.getOutputStream().flush();
               }catch(IOException ex){
                   ex.printStackTrace();
               }
            }
        }else if(clickButton.equals(cancel)){
            setVisible(false);
            new MyChat().setVisible(true);
            dispose();
        }
    }
}
