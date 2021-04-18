package springbean.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/16 11:18
 */
@Data
@Component
public class Book {
    private long id;
    @Value("《Spring Test》")
    private String name;
    private double price;

    public void introduce() {
        System.out.println("Book name is " + name + ", the price is " + price);
    }

}
