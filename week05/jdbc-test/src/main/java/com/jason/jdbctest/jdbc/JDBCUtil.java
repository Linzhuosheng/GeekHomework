package com.jason.jdbctest.jdbc;

import com.jason.jdbctest.jdbc.service.StudentDAO;

import java.sql.*;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 15:12
 */
public class JDBCUtil {

    public static final String URL = "jdbc:mysql://localhost:3306/jdbctest?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    public static final String USER = "root";
    public static final String PASSWORD = "123";
    private static Connection conn = null;

    static{
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 获得数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }

}
