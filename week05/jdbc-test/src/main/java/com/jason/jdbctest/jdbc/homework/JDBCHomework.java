package com.jason.jdbctest.jdbc.homework;

import com.jason.jdbctest.jdbc.JDBCUtil;
import com.jason.jdbctest.jdbc.service.StudentDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 16:09
 */
public class JDBCHomework {

    public static Connection conn = JDBCUtil.getConnection();

    public static void main(String[] args) throws SQLException {
        try {
            //使用 JDBC 原生接口，实现数据库的增删改查操作
            conn = JDBCUtil.getConnection();
            //查询
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.queryStudentName(conn);
            //使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
            studentDAO.updateStudentName(conn,"LinJason");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            conn.close();
        }

    }
}
