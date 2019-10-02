package crawler.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

public class ParseHtml {


    public static void main(String[] args) throws IOException {
        String url = "https://mil.sina.cn/zgjq/2019-10-02/detail-iicezzrq9745419.d.html?cre=tianyi&mod=whome&loc=14&r=25&rfunc=10&tj=cx_wap_whome&tr=188";
        HashSet<String> set = new HashSet<>();
        set.add(url);
        HashSet<String> titleSet = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(url);

        long l = System.currentTimeMillis();
        parseU(set,queue,titleSet);
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);
    }

    private static boolean filter(String url) {
        return url.matches("http(.*).sina.cn/(.*)-(.*)-(.*)") && !url.matches("(.*)passport(.*)") &&
                !url.matches("(.*)video(.*)");
    }

    private static Document removeHtmlSpace(String str) {
        Document doc = Jsoup.parse(str);
        String result = doc.html().replace("&nbsp;", "").replace("&quot", "");
        return Jsoup.parse(result);
    }


    /*private static void init(HashSet<String> linkList, Queue<String> queue) throws IOException {
        Document document = ;
        String title = document.select("h1").text();
        String parts = document.select("p").text();
        if (!title.equals("")) {
            //存储
            System.out.println(title + "  " + url);
        }
        Elements links = document.select("a");
        for (Element element : links) {
            String href = element.attr("href");
            linkList.add(href);
            if (filter(href)) {
                queue.add(href);
                //添加到库
            }
        }

        init(linkList, queue);
        System.out.println("到底了");
    }

     */

        private static void parseU (HashSet<String> set, Queue<String> queue, HashSet<String> titleSet) throws IOException {
            String url = queue.remove();
            String html = new UrlToHtml(url).parse();
            Document document = Jsoup.parse(html);
            String title = document.select("h1").text();
            String parts = document.select("p").text();
            if (!title.equals("") && !titleSet.contains(title)) {
                //存储
                titleSet.add(title);
                System.out.println(title + "  " + url);
            }else if (!title.equals("") && titleSet.contains(title)){
                return;
            }
            Elements links = document.select("a");
            for (Element element : links) {
                String href = element.attr("href");
                if (filter(href) && !set.contains(href)) {
                    queue.add(href);
                    set.add(href);
                    //添加到库
                }
                String dataPclink = element.attr("data-pclink");
                if (filter(dataPclink) && !set.contains(dataPclink)) {
                    queue.add(dataPclink);
                    set.add(dataPclink);
                    //添加到库
                }
            }
            Elements div = document.select("div");
            for (Element element : div) {
                String content = element.attr("data-content");
                JsonContentOne one = JSON.parseObject(content,JsonContentOne.class);
                if (one != null) {
                    JSONArray jsonArray = JSON.parseArray(one.getDocs());
                    for (int i = 0; i < jsonArray.size(); i++){
                        JSONObject two = (JSONObject)jsonArray.get(i);
                        String uri = two.getString("url");
                    }
                    int a = 1;
                }
            }
            parseU(set,queue,titleSet);
        }

}
