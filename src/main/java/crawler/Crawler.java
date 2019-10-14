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
     * 线程数量.
     */
    private int threadNumber;

    /**
     * @param threadNumber 线程数量
     */
    public Crawler(int threadNumber) {
        this.threadNumber = threadNumber;
        service = Executors.newFixedThreadPool(threadNumber);
    }

    /**
     * 往线程池提交任务.
     */
    public void start() {
        for (int i = 1; i < threadNumber; i++) {
            service.submit(() -> {
                ParseHtml parseHtml1 = new ParseHtml();
                parseHtml1.parseUrl();
            });
        }
    }
}
