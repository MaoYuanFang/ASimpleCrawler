package crawler;

import crawler.view.ParseHtml;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 开启线程池.
 */
public class Crawler {

    /**
     * 线程池对象.
     */
    private ExecutorService service;

    /**
     * @param threadNub 线程数量
     */
    public Crawler(final int threadNub) {
        service = Executors.newFixedThreadPool(threadNub);
    }

    /**
     * 往线程池提交任务.
     */
    public void start() {
        service.submit(() -> {
            ParseHtml parseHtml1 = new ParseHtml();
            parseHtml1.parseUrl();
        });
    }
}
