import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author Jianjun Lv
 * @date 7/6/2018 4:49 PM
 */
public class URLFecter {
    public static List<GitSearchModel> RepositoryListURLParser(HttpClient client, String url) throws Exception {
        //用来接收解析的数据
        List<GitSearchModel> gitSearchData = new ArrayList<GitSearchModel>();
        //获取网站响应的html，这里调用了HTTPUtils类
        HttpResponse response = HTTPUtils.getRawHtml(client, url);
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();
        //如果状态响应码为200，则获取html实体内容或者json文件
        if (StatusCode == 200) {
            String entity = EntityUtils.toString(response.getEntity(), "utf-8");
            gitSearchData = HTMLParse.getSearchRepositoryList(entity);
            EntityUtils.consume(response.getEntity());
        } else {
            //消耗掉实体,保证底层的资源得以释放
            EntityUtils.consume(response.getEntity());
        }
        return gitSearchData;
    }

    public static DBCrawlerGitDetailDataModel RepositoryContentURLParser(HttpClient client, String url) throws Exception {
        //用来接收解析的数据
        //获取网站响应的html，这里调用了HTTPUtils类
        HttpResponse response = HTTPUtils.getRawHtml(client, url);
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();
        //如果状态响应码为200，则获取html实体内容或者json文件
        if (StatusCode == 200) {
            String entity = EntityUtils.toString(response.getEntity(), "utf-8");
            DBCrawlerGitDetailDataModel model = HTMLParse.getDBCrawlerGitDetailDataModel(entity, url);
            EntityUtils.consume(response.getEntity());
            return model;
        } else {
            //消耗掉实体,保证底层的资源得以释放
            EntityUtils.consume(response.getEntity());
            return null;
        }
    }
}
