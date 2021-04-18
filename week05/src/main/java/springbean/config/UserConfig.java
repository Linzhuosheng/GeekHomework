package springbean.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springbean.pojo.Book;
import springbean.pojo.User;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 14:29
 */
@Configuration
@ComponentScan("springbean")
public class UserConfig {

    //方法名等于 id
    @Bean
    public User getUser(){
        User user  = new User();
        user.setId(3);
        user.setName("HanHan");
        user.setAge(18);
        user.setBook(new Book());
        return user;
    }

}
