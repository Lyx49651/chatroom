import java.sql.*;

public class mysalop {
    public int selecting1(String idw,String passwordw,int key) throws SQLException, ClassNotFoundException{
        int tt,kk;
        if(key==1){
            tt=selecting2(idw,passwordw,key);
            if(tt==0){
                insertinfo(idw,passwordw);
                return 1;
            }else{
                return 0;
            }
        }else{
            kk=selecting(idw,passwordw);
            return kk;
        }
    }
    public int selecting2(String idw,String passwordw,int gg) throws SQLException, ClassNotFoundException {//查找是否重复
        ResultSet resultSet = null;
        Statement statement = null;
        Connection conn = null;
        int kk=0;
        // System.out.println(idw);
        // System.out.println(passwordw);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            String user = "root";
            String password = "123456";
            //建立连接
            conn = DriverManager.getConnection(url, user, password);
            //创建statement对象
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select id from usr");
            while(resultSet.next()){
                if(resultSet.getString(1).equals(idw)){
                    System.out.println(resultSet.getString(1));
                    kk=1;
                    break;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            //释放资源
            resultSet.close();
            statement.close();
            conn.close();
        }
        return kk;
    }
    public int selecting(String idw,String passwordw) throws SQLException, ClassNotFoundException {//验证用户名密码
        ResultSet resultSet = null;
        Statement statement = null;
        Connection conn = null;
        int key2=0,kk=0;
       // System.out.println(idw);
       // System.out.println(passwordw);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            String user = "root";
            String password = "123456";
            String SQL1="select password from usr "+"where id='"+idw+"'";
            //建立连接
            conn = DriverManager.getConnection(url, user, password);
            //创建statement对象
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select id from usr");
            while(resultSet.next()){

                if(resultSet.getString(1).equals(idw)){
                    kk=1;
                    break;
                }
            }
            if(kk==1) {
                resultSet = statement.executeQuery(SQL1);
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(passwordw)) {
                        key2 = -2;
                    }
                    //}
                    if (key2 == -2) {
                        return -1;
                    } else {
                        return key2;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            resultSet.close();
            statement.close();
            conn.close();
        }
        return 2;
    }
    public void insertinfo(String idw,String passwordw) throws SQLException, ClassNotFoundException{//插入
        ResultSet resultSet = null;
        Statement statement = null;
        Connection conn = null;
        int Update1=0;
        String SQL1="insert into usr values('"+idw+"','"+passwordw+"')";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            String user = "root";
            String password = "123456";
            //建立连接
            conn = DriverManager.getConnection(url, user, password);
            //创建statement对象
            statement = conn.createStatement();
            Update1=statement.executeUpdate(SQL1);
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.释放资源
            statement.close();
            conn.close();
        }
    }
    public void deleteinfo(String idw) throws SQLException, ClassNotFoundException{//删除
        ResultSet resultSet = null;
        Statement statement = null;
        Connection conn = null;
        int Update2=0;
        String SQL1="delete from usr where id="+idw;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            String user = "root";
            String password = "123456";
            //建立连接
            conn = DriverManager.getConnection(url, user, password);
            //创建statement对象
            statement = conn.createStatement();
            Update2=statement.executeUpdate(SQL1);
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.释放资源
            statement.close();
            conn.close();
        }
    }
    public void updateinfo(String idw,String passwordw) throws SQLException, ClassNotFoundException{//更新
        ResultSet resultSet = null;
        Statement statement = null;
        Connection conn = null;
        int Update2=0;
        String SQL2;
        SQL2="update usr set password="+passwordw +" where id="+idw;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            String user = "root";
            String password = "123456";
            //建立连接
            conn = DriverManager.getConnection(url, user, password);
            //创建statement对象
            statement = conn.createStatement();
            Update2=statement.executeUpdate(SQL2);
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5.释放资源
            statement.close();
            conn.close();
        }
    }
}
