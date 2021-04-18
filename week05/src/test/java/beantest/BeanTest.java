package beantest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springbean.config.BookConfig;
import springbean.config.UserConfig;
import springbean.pojo.Book;
import springbean.pojo.User;

/**
 * 作业：写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 11:13
 */
public class BeanTest {
    /**
     * 使用applicationContext.xml进行装配
     */
    @Test
    public void xmlConfigTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) context.getBean("user");
        user.speak();
    }

    /**
     * 使用注解自动装配
     */
    @Test
    public void autoConfigTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) context.getBean("user");
        Book book = user.getBook();
        book.introduce();
    }

    /**
     * 使用构造方法装配
     */
    @Test
    public void constructorConfigTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) context.getBean("user2");
        user.speak();
        Book book = user.getBook();
        book.introduce();
    }

    /**
     * 使用JavaConfig注入Bean
     */
    @Test
    public void javaConfigTest(){
        ApplicationContext userContext = new AnnotationConfigApplicationContext(UserConfig.class);
        ApplicationContext bookContext = new AnnotationConfigApplicationContext(BookConfig.class);
        Book book = bookContext.getBean("getBook",Book.class);
        book.introduce();
        User user = userContext.getBean("getUser",User.class);
        user.speak();
    }


}
