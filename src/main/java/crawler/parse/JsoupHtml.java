package crawler.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupHtml {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://mil.sina.cn/zgjq/2019-10-02/detail-iicezzrq9745419.d.html?cre=tianyi&mod=whome&loc=14&r=25&rfunc=10&tj=cx_wap_whome&tr=188").header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0").get();
        Elements elements = doc.select("a");
    }
}
