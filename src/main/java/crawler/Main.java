package crawler;


import crawler.dao.News;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {

    private Main() {
    }

    public static void main(String[] args) throws IOException {

        new Crawler(20).start();
        //new Crawler(20).justRead();    //链接太多就只用jsoup读新闻，不使用htmlunit解析链接


        News news = new News();
        news.setDate(null);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        while (true) {
            System.out.println("输入搜索关键字");
            String keyword = reader.readLine();
            if ("退出".equals(keyword)) {
                System.out.println("再次输入\"确认\"");
                if ("确认".equals(reader.readLine())) {
                    System.out.println("已退出搜索");
                    break;
                }
                System.out.println("已取消退出");
            } else {
                search(keyword);
            }
        }
    }

    private static void search(String keyword) throws IOException {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")))) {
            SearchRequest request = new SearchRequest("news");
            request.source(new SearchSourceBuilder().query(new MultiMatchQueryBuilder(keyword, "title", "content")));
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            response.getHits().forEach(hit -> System.out.println(hit.getSourceAsString()));
        }
    }
}
