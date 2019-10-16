package crawler.view;

import crawler.dao.News;
import crawler.dao.NewsDao;
import crawler.dao.SingleNewsDao;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToElasticsearch {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
        List<News> newsList =SingleNewsDao.getInstance().findAll();
        for (News news : newsList) {
            IndexRequest request = new IndexRequest("news");
            Map<String, Object> data = new HashMap<>();
            data.put("id", news.getId());
            data.put("title", news.getTitle());
            String content = news.getContent();
            data.put("content", content.length() > 20 ? content.substring(0, 20) : content);
            data.put("date", new Date(news.getDate().getTime()));
            data.put("url", news.getUrl());
            request.source(data, XContentType.JSON);
            IndexResponse index = client.index(request, RequestOptions.DEFAULT);
            System.out.println(index.status().getStatus());
        }


        client.close();
    }
}
