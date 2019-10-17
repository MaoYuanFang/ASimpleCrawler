package crawler;

import crawler.view.ParseHtml;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 开启线程池.
 */
class Crawler {

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
    Crawler(int threadNumber) {
        this.threadNumber = threadNumber;
        service = Executors.newFixedThreadPool(threadNumber);
    }

    /**
     * 往线程池提交任务.
     */
    void start() {
        for (int i = 0; i < threadNumber; i++) {
            service.submit(() -> {
                ParseHtml parseHtml = new ParseHtml();
                parseHtml.parseUrl();
            });
        }
    }

    /**
     * 只读取新闻，不取链接
     */
    void justRead(){
        for (int i = 0; i < threadNumber; i++) {
            service.submit(() -> {
                ParseHtml parseHtml = new ParseHtml();
                parseHtml.readNews();
            });
        }
    }
}
