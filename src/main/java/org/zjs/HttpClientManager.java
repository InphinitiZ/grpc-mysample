package org.zjs;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fanchao on 16/8/17.
 */
@Component
public class HttpClientManager {

    private final  static Logger LOGGER = LoggerFactory.getLogger(HttpClientManager.class);

    private static final int maxConnections = 200;
    private static final int maxPerRouteConnections = 200;
    private PoolingHttpClientConnectionManager cm = null;

    @PostConstruct
    public void init() {
        LOGGER.info("init http connection pool succ! maxConnections={}, maxPerRouteConnections={}",
                new Object[]{maxConnections, maxPerRouteConnections});
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(maxConnections);
        cm.setDefaultMaxPerRoute(maxPerRouteConnections);
    }

    public CloseableHttpClient newHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }
}
