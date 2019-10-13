package crawler;

import crawler.view.ParseHtml;

/**
 * 单个的线程.
 */
public class CrawlerThread extends Thread {

    /**
     * 线程任务.
     */
    @Override
    public void run() {
        ParseHtml parseHtml1 = new ParseHtml();
        parseHtml1.parseUrl();
    }

}
