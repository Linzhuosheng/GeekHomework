package nio.gateway.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询路由
 * （选做）实现路由。
 * @Author: Jason Lin<zslin@sailfish.com.cn>
 * @Date: 2021/4/2 15:14
 */
public class RoundRibbonRouter implements HttpEndpointRouter {

    private static AtomicInteger indexAtomic = new AtomicInteger(0);

    @Override
    public String route(List<String> endpoints) {
        if (indexAtomic.get() >= endpoints.size()) {
            indexAtomic.set(0);
        }
        return endpoints.get(indexAtomic.getAndIncrement());

    }
}