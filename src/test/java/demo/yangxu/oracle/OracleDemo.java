package demo.yangxu.oracle;

import oracle.jdbc.driver.OracleTypes;
import org.junit.Test;

import java.sql.*;

public class OracleDemo {
    @Test
    public void javaCallOracle() throws ClassNotFoundException, SQLException {
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //得到Connection连接
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.63.128:1521:orcl", "xiaoming", "mima");
        //得到预编译的Statement对象
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM emp WHERE empno = ?");
        //给参数赋值
        pstm.setObject(1,7788);
        //执行数据库查询操作
        ResultSet rs = pstm.executeQuery();
        //输出结果
        while(rs.next()){
            System.out.println(rs.getString("ename"));
        }
        //释放资源
        rs.close();
        pstm.close();
        connection.close();

    }

    /**
     * java调用存储过程
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]}调用存储过程使用
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void javaCallProcedure() throws ClassNotFoundException, SQLException {
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //得到Connection连接
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.63.128:1521:orcl", "xiaoming", "mima");
        //得到预编译的Statement对象
        CallableStatement pstm = connection.prepareCall("{call p_yearsal(?, ?)}");
        //给参数赋值
        pstm.setObject(1,7788);
        pstm.registerOutParameter(2, OracleTypes.NUMBER);
        //执行数据库查询操作
        pstm.execute();
        //输出结果
        System.out.println(pstm.getObject(2));
        //释放资源
        pstm.close();
        connection.close();
    }

    /**
     * java调用存储函数
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储函数使用
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void javaCallFunction() throws ClassNotFoundException, SQLException {
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //得到Connection连接
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.63.128:1521:orcl", "xiaoming", "mima");
        //得到预编译的Statement对象
        CallableStatement pstm = connection.prepareCall("{?= call f_yearsal(?)}");
        //给参数赋值
        pstm.setObject(2,7788);
        pstm.registerOutParameter(1, OracleTypes.NUMBER);
        //执行数据库查询操作
        pstm.execute();
        //输出结果
        System.out.println(pstm.getObject(1));
        //释放资源
        pstm.close();
        connection.close();
    }
}
