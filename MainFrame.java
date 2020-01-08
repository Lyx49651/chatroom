package chat;

import login.MyChat;
import login.rebutton;
import mychat.TEST;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame implements ActionListener,MouseListener {
    private static final int FRAME_WIDTH=800;
    private static final int FRAME_HEIGHT=680;
    private MenuBar menubar;
    private JTextArea ta;//定义文本域
    public static JTextArea ja;
    private JList jl;//定义列表
    private JScrollPane jspane1;//定义滚动窗格\
    private JScrollPane jspane2;
    private JScrollPane jspane3;
    private rebutton enter;
    private String id1;
    public static retreenode friends;
    public MainFrame(String id) throws IOException {
        id1=id;
        setLayout(null);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setResizable(false);
        setTitle("hello "+id+". welcome to hall!");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon=new ImageIcon("F:\\javayuan\\mychat\\lib\\timg.jpg");
        setIconImage(icon.getImage());
        menubar = new MenuBar();
        Menu fileMenu = new Menu("setting");
        Menu item1= new Menu("account management");
        MenuItem item2= new MenuItem("cancel");
        item1.add(item2);
        fileMenu.add(item1);
        menubar.add(fileMenu);
        item1.addActionListener(this);
        this.setMenuBar(menubar);
        ja=new JTextArea();
        ta=new JTextArea();
        jl=new JList();
        JLabel time = new JLabel();
        ja.setEditable(false);
        time.setBounds(5,585,180,15);
        ja.setBounds(0,0,500,400);
        jl.setBounds(505,20,300,580);
        ta.setBounds(0,405,500,175);
        jspane1=new JScrollPane(ta);
        jspane2=new JScrollPane(jl);
        jspane3=new JScrollPane(ja);
        jspane1.setBounds(0,405,500,175);
        jspane2.setBounds(505,0,300,580);
        jspane3.setBounds(0,0,500,400);
        enter=new rebutton("Enter");
        enter.setBounds(430,585,70,30);
        enter.addActionListener(this);
        add(enter);
        friends=new retreenode("friends",3);
        JTree jt=new JTree(friends);
        jt.putClientProperty("JTree.lineStyle", "Horizontal");
        //设置树的字体大小，样式
        jt.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        //设置树节点的高度
        jt.setRowHeight(80);
        //设置单元描述
        jt.setCellRenderer(new FriendNodeRenderer());
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 如果在这棵树上点击了2次,即双击
                if (e.getSource() == jt && e.getClickCount() == 2) {
                    // 按照鼠标点击的坐标点获取路径
                    TreePath selPath = jt.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null)// 谨防空指针异常!双击空白处是会这样
                    {
                        System.out.println(selPath);// 输出路径看一下
                        // 获取这个路径上的最后一个组件,也就是双击的地方
                        retreenode node = (retreenode) selPath.getLastPathComponent();
                        if(node.NickName!="friends"){
                            TEST.m=1;
                            new private_chatroom(node.NickName,id1).setVisible(true);
                        }
                    }
                }

            }
        });
        //将tree添加到JScrollPane 中JScrollPane scrollPane=new JScrollPane();scrollPane.setViewportView(tree); //将scrollPane添加到JPanel中用于显示containerCenter.add(scrollPane);
        jspane2.setViewportView(jt);
        this.addWindowListener(new MyWin());//添加myMouseListener鼠标监听事件
        add(time);
        this.setTimer(time);
        getContentPane().add(jspane1);
        getContentPane().add(jspane2);
        getContentPane().add(jspane3);
    }
     class MyWin extends WindowAdapter {
        public void windowClosing(WindowEvent e)
        {
            try {
                setVisible(false);
                byte[] logininfo = ("XX"+"\r\n"+id1+"\r\n").getBytes("utf-8");
                TEST.clientsocket.getOutputStream().write(logininfo);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
       if(clickButton.equals("cancel")){
            setVisible(false);
            TEST.login=new MyChat();
            dispose();
       }else if(clickButton.equals("Enter")){
            String word=ta.getText();
            try {
                TEST.sendmessage(id1,word);
                ta.setText("");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
       }
    }

    public static void say(String message){
        ja.append(TEST.senduser);
        ja.append("\n");
        ja.append(message);
        ja.append("\n");
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
