package com.jason.jdbctest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class JdbcTestApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。
     */
    @Test
    public void hikariTest(){
        String sql  = "SELECT name FROM student";
        String studentName =  jdbcTemplate.queryForObject(sql,String.class);
        System.out.println("使用Hikari连接池查询出结果:"+studentName);
        //System.out.println(sql);
    }


}
