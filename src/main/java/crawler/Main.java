package crawler;


import crawler.dao.NewsDao;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new CrawlerThread().start();
        }

    }
}
