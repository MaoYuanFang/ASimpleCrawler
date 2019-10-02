package crawler;

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

public class ParseUrl {



    public static void main(String[] args) throws IOException {
        String url = "https://news.sina.cn/global/szzx/2019-09-29/detail-iicezzrq9104996.d.html";
        HashSet<String> set = new HashSet<>();
        set.add(url);
        Queue<String> queue = new LinkedList<>();
        queue.add(url);
        init(set,queue);

    }

    private static boolean filter(String url){
        return url.matches("http(.*).sina.cn/(.*)-(.*)-(.*)") && !url.matches("(.*)passport(.*)") &&
                !url.matches("(.*)gaokao(.*)") && !url.matches("(.*)games(.*)");
    }



    private static void init(HashSet<String> linkList, Queue<String> queue) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        if (queue.isEmpty()){

        }
        String url = queue.remove();
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        try (CloseableHttpResponse response = httpclient.execute(get)) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity);

            Document document = Jsoup.parse(html);
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
                if (filter(href) ) {
                    queue.add(href);
                    //添加到库
                }
            }
        }
        init(linkList,queue);
        System.out.println("到底了");
    }
}
