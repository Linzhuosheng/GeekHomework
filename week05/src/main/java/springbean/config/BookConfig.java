package springbean.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springbean.pojo.Book;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 14:46
 */
@Configuration
@ComponentScan("springbean")
public class BookConfig {

    @Bean
    public Book getBook(){
        return new Book();
    }
}
