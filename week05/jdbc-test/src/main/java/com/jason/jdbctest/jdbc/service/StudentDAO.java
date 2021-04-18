package com.jason.jdbctest.jdbc.service;

import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 15:35
 */
public class StudentDAO {
    /**
     * 作业：使用 JDBC 原生接口，实现数据库的增删改查操作
     * @param connection
     * @throws SQLException
     */
    @Transactional(readOnly = true)
    public void queryStudentName(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM student");
        try {
            //如果有数据，rs.next()返回true
            while (rs.next()) {
                System.out.println("姓名:" + rs.getString("name"));
            }
        } finally {
            rs.close();
            stmt.close();
        }


    }

    /**
     * 使用事务，PrepareStatement 方式，批处理方式，改进上述操作
     * @param connection
     * @param name
     * @throws SQLException
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStudentName(Connection connection, String name) throws SQLException {
        String sql = "UPDATE student SET name = ?  where id = 1";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        try {
            prepareStatement.setString(1, name);
            prepareStatement.execute();
        } finally {
            prepareStatement.close();
        }
    }
}
