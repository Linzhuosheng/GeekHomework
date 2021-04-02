package nio.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * 随机访问路由实现
 * @author JasonLin
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
