
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jianjun Lv
 * @date 7/6/2018 4:51 PM
 */
public class HTMLParse {
    private static final Logger logger = LoggerFactory.getLogger(HTMLParse.class);

    public static List<GitSearchModel> getSearchRepositoryList(String html) throws Exception {
        //获取的数据，存放在集合中
        List<GitSearchModel> data = new ArrayList<>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("ul[class=repo-list]").select("a[class=v-align-middle]");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(doc.select("div[class=d-flex flex-justify-between border-bottom pb-3]").select("h3").text());
        for (Element ele : elements) {
            String repositoryName = ele.text();
            String repositoryURL = ele.attr("href");
            //创建一个对象，这里可以看出，使用Model的优势，直接进行封装
            GitSearchModel gitSearchModel = new GitSearchModel();
            //对象的值
            gitSearchModel.setRepositoryName(repositoryName);
            gitSearchModel.setRepositoryURL(repositoryURL);
            //将每一个对象的值，保存到List集合中
            data.add(gitSearchModel);
        }
        //返回数据
        return data;
    }

    public static DBCrawlerGitDetailDataModel getDBCrawlerGitDetailDataModel(String html, String repositoryPath) {
        DBCrawlerGitDetailDataModel model = new DBCrawlerGitDetailDataModel();
        Document doc = Jsoup.parse(html);
        String repositoryName = repositoryPath.substring(Constant.ROOT_URL.length() + 1);
        String downloadURL = doc.select("a[data-ga-click=Repository, download zip, location:repo overview]").attr("href");
        String watchCountStr = doc.select("ul[class=pagehead-actions]").select("a[aria-label$=users are watching this repository]").text().replaceAll(",","");
        String starCountStr = doc.select("ul[class=pagehead-actions]").select("a[aria-label$=users starred this repository]").text().replaceAll(",","");
        String forkCountStr = doc.select("ul[class=pagehead-actions]").select("a[aria-label$=users forked this repository]").text().replaceAll(",","");
        String repositoryContent = doc.select("div[class=js-repo-meta-container]").select("div[class=repository-meta-content col-11 mb-1]").text();
        String topicsListStr = doc.select("div[class=list-topics-container f6 mt-1]").select("a[class=topic-tag topic-tag-link]").text();
        String[] topicsList;
        if (topicsListStr == null || topicsListStr.length() == 0) {
            topicsList = new String[0];
        } else {
            topicsList = topicsListStr.split(" ");
        }
        String readmeFileContent = Jsoup.clean(doc.select("div[id=readme").select("article[itemprop=text]").toString(), "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        model.setRepositoryName(repositoryName);
        model.setRepositoryPath(repositoryPath);
        model.setDownloadURL(downloadURL);
        try {
            model.setWatchCount(Integer.parseInt(watchCountStr));
            model.setStarCount(Integer.parseInt(starCountStr));
            model.setForkCount(Integer.parseInt(forkCountStr));
        } catch (Exception e) {
            logger.error("\"" + repositoryName + "\" get count error: " + e.getMessage());
        }
        model.setRepositoryContent(repositoryContent);
        model.setTopicsList(topicsList);
        model.setReadmeFileContent(readmeFileContent);
        return model;
    }

}

