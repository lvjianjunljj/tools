import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jianjun Lv
 * @date 7/11/2018 9:19 AM
 */
public class HttpClientTool {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientTool.class);

    private PoolingHttpClientConnectionManager poolConnManager;
    private final int poolMaxTotal = 2;
    private final int maxConPerRoute = 2;
    private final int socketTimeout = 2000;
    private final int connectionRequestTimeout = 3000;
    private final int connectTimeout = 1000;

    private static class SSLClientHolder {
        private static final HttpClientTool instance = new HttpClientTool();
    }

    public static HttpClientTool getInstance() {
        return SSLClientHolder.instance;
    }

    public HttpClientTool() {
        try {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,
                    new TrustSelfSignedStrategy())
                    .build();
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // Increase max total connection to 200
            poolConnManager.setMaxTotal(poolMaxTotal);
            // Increase default max connection per route to 20
            poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
            poolConnManager.setDefaultSocketConfig(socketConfig);
        } catch (Exception e) {

        }
    }

    public HttpClient getConnection() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
        if (poolConnManager != null && poolConnManager.getTotalStats() != null) {

            logger.info("now client pool " + poolConnManager.getTotalStats().toString());
        }
        return httpClient;
    }


    public String sendHttpGet(String url, String jsonStr) {
        //参数检测
        if (url == null || "".equals(url)) {
            return null;
        }
        HttpGet httpGet = new HttpGet(url);
        int a = -3;
        try {
            HttpClient client = this.getConnection();
            a= -2;
            HttpResponse response = client.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String returnStr = null;
                if (entity != null) {
                    returnStr = EntityUtils.toString(entity, "utf-8");
                }
                logger.info(" receive response: url \"" + url + "\" status=" + status);
                return returnStr;
            } else {
                HttpEntity entity = response.getEntity();
                httpGet.abort();
                logger.info(" receive response: url \"" + url + "\" status=" + status + " resopnse=" + EntityUtils.toString(entity, "utf-8"));
                EntityUtils.consume(entity);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpGet.abort();
            logger.info(" Exception" + e.toString() + " url=\"" + url + "\"");
            return null;
        }
    }

}