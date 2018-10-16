import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Jianjun Lv
 * @date 7/6/2018 4:53 PM
 */
public class Main {
    //log4j的使用，不会的请看之前写的文章
    static final Log logger = LogFactory.getLog(Main.class);

    public static void main(String[] args) throws Exception {
//        初始化一个httpclient
        HttpClient client = HttpClientBuilder.create().build();
        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
        String url = "https://github.com/search?q=object+detection";
        //抓取的数据
        List<GitSearchModel> gitData = URLFecter.RepositoryListURLParser(client, url);
        //循环输出抓取的数据
        for (GitSearchModel model : gitData) {
            logger.info("repositoryName:" + model.getRepositoryName() + "\t" + "repositoryURL:" + model.getRepositoryURL());
        }
        //将抓取的数据插入数据库
        MYSQLControl.executeInsert(gitData);

        DBCrawlerGitDetailDataModel model = URLFecter.RepositoryContentURLParser(client, "https://github.com/tensorflow/tensorflow");
        System.out.println(model.getRepositoryName());
        System.out.println(model.getWatchCount());
        ExecutorService executor = Executors.newFixedThreadPool(100);
        Future<String> future1 = executor.submit(new Request(url, "utf-8"));
        Future<String> future2 = executor.submit(new Request(url, "utf-8"));
        Future<String> future3 = executor.submit(new Request(url, "utf-8"));
        Future<String> future4 = executor.submit(new Request(url, "utf-8"));
        Future<String> future5 = executor.submit(new Request(url, "utf-8"));
        future1.get();
        future2.get();
        future3.get();
        future4.get();
        future5.get();
        System.out.println(model.getDownloadURL());
        System.out.println(model.getStarCount());
        System.out.println(model.getForkCount());

        executor.shutdownNow();

//        HttpClientTool demo02 = HttpClientTool.getInstance();
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
//            String result = demo02.sendHttpGet("https://github.com/search?p=" + (i + 1) + "&q=object+detection", "123");
//        }
//        System.out.println(result);



    }

    public static class Request implements Callable<String> {
        private String url;
        private String encoding;
        private int connectTimeout;
        private int socketTimeout;

        public Request(String url, String encoding) {
            this.url = url;
            this.encoding = encoding;
            this.connectTimeout = -1;
        }

        public Request(String url, String encoding, int connectTimeout,
                       int socketTimeout) {
            this.url = url;
            this.encoding = encoding;
            this.connectTimeout = connectTimeout;
            this.socketTimeout = socketTimeout;
        }

        public String call() throws Exception {
//            if (connectTimeout < 0) {
//                return HttpContentFetcher.get(url, encoding);
//            } else {
//                return HttpContentFetcher.get(url, encoding, connectTimeout,
//                        socketTimeout);
//            }
            Thread.sleep(100);
            return "future test!!!";
        }
    }
}
