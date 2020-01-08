package chat;

import login.rebutton;
import mychat.TEST;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class private_chatroom extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH=800;
    private static final int FRAME_HEIGHT=680;
    private JTextArea ta;//定义文本域
    public static JTextArea ja;
    private JScrollPane jspane1;//定义滚动窗格\
    private JScrollPane jspane3;
    private rebutton enter;
    private String id1,id2;
    public private_chatroom(String name1,String selfname2){
        id1=selfname2;
        id2=name1;
        setLayout(null);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setResizable(false);
        setTitle("this is the room of "+id1+" and you!");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon=new ImageIcon("F:\\javayuan\\mychat\\lib\\timg.jpg");
        setIconImage(icon.getImage());
        ja=new JTextArea();
        ta=new JTextArea();
        JLabel time = new JLabel();
        ja.setEditable(false);
        enter=new rebutton("Enter");
        enter.setBounds(700,585,70,30);
        enter.addActionListener( this);
        add(enter);
        time.setBounds(5,595,180,15);
        ja.setBounds(0,0,800,400);
        ta.setBounds(0,405,800,175);
        jspane1=new JScrollPane(ta);
        jspane3=new JScrollPane(ja);
        jspane1.setBounds(0,405,800,175);
        jspane3.setBounds(0,0,800,400);
        add(time);
        this.setTimer(time);
        getContentPane().add(jspane1);
        getContentPane().add(jspane3);
        this.addWindowListener(new MyWin());//添加myMouseListener鼠标监听事件
    }

    class MyWin extends WindowAdapter {
        public void windowClosing(WindowEvent e)
        {
            setVisible(false);
            dispose();
            TEST.m=0;
        }
    }

    private void setTimer(JLabel time) {
        final JLabel varTime = time;
        Timer timeAction = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                //转换日期显示格式
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }
    public void actionPerformed(ActionEvent event){
        String clickButton=event.getActionCommand();
        if(clickButton.equals("Enter")){
            String word=ta.getText();
            try {
                TEST.ssendmessage(id1,id2,word);
                ta.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void say(){
        ja.append(TEST.senduser);
        ja.append("\n");
        ja.append(TEST.message1);
        ja.append("\n");
    }

}
