package org.zjs;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zjs.grpc.helloworld.HelloReply;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018/4/9
 * Time: 14:19
 */
@RestController
public class Client implements InitializingBean {

    private GrpcClient client;

    @RequestMapping("/doGreet")
    String doGreet(String name) throws InterruptedException {
        try {
          /* Access a service running on the local machine on port 50051 */
            String user = "world";
            user = name; /* Use the arg as the name to greet if provided */
            HelloReply reply = client.greet(user);
            return reply.getMessage();
        } finally {
            //client.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client = new GrpcClient("grpcdemo", 50051);
    }

}
