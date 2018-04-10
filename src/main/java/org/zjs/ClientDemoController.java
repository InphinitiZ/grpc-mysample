package org.zjs;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zjs.grpc.GrpcClient;
import org.zjs.grpc.helloworld.HelloReply;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018/4/9
 * Time: 14:19
 */
@RestController
public class ClientDemoController implements InitializingBean {

    private static final String GRPC_DEMO_SERVICE_NAME = "grpcdemo";
    private static final String REST_DEMO_SERVICE_NAME = "http://restdemo:8080";

    @Autowired
    private HttpClientManager httpClientManager;

    private GrpcClient client;

    @RequestMapping("/greetings/{name}")
    String doGreet(@PathVariable String name) throws InterruptedException {
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

    @RequestMapping("/getUser")
    String getUser(int uid) {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet;
        CloseableHttpResponse response;
        try {
            httpClient = httpClientManager.newHttpClient();
            httpGet = new HttpGet(REST_DEMO_SERVICE_NAME + "/user/" + uid);
            httpGet.setConfig(RequestConfig.custom().setConnectTimeout(2000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build());
            response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    @RequestMapping("/deleteUser")
    String deleteUser(int uid) {
        CloseableHttpClient httpClient = null;
        HttpDelete httpDelete;
        CloseableHttpResponse response;
        try {
            httpClient = httpClientManager.newHttpClient();
            httpDelete = new HttpDelete(REST_DEMO_SERVICE_NAME + "/user/" + uid);
            httpDelete.setConfig(RequestConfig.custom().setConnectTimeout(2000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build());
            response = httpClient.execute(httpDelete);
            try {
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    @RequestMapping("/addUser")
    String addUser(String name, int age, String gender) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost;
        CloseableHttpResponse response;
        try {
            httpClient = httpClientManager.newHttpClient();
            httpPost = new HttpPost(REST_DEMO_SERVICE_NAME + "/user");
            httpPost.setConfig(RequestConfig.custom().setConnectTimeout(2000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build());
            List formparams = new ArrayList();
            formparams.add(new BasicNameValuePair("name", name));
            formparams.add(new BasicNameValuePair("age", age + ""));
            formparams.add(new BasicNameValuePair("gender", gender));
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    @RequestMapping("/updateUser")
    String updateUser(int uid, String name, Integer age, String gender) {
        CloseableHttpClient httpClient = null;
        HttpPut httpPut;
        CloseableHttpResponse response;
        try {
            httpClient = httpClientManager.newHttpClient();
            httpPut = new HttpPut(REST_DEMO_SERVICE_NAME + "/user/" + uid);
            httpPut.setConfig(RequestConfig.custom().setConnectTimeout(2000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build());
            List formparams = new ArrayList();
            if(name != null) {
                formparams.add(new BasicNameValuePair("name", name));
            }
            if(age != null) {
                formparams.add(new BasicNameValuePair("age", age + ""));
            }
            if(gender != null) {
                formparams.add(new BasicNameValuePair("gender", gender));
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPut.setEntity(uefEntity);
            response = httpClient.execute(httpPut);
            try {
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client = new GrpcClient(GRPC_DEMO_SERVICE_NAME, 50051);

    }

}
