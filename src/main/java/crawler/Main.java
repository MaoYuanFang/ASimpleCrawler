package crawler;


import crawler.dao.NewsDao;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler(10);
        crawler.start();

    }
}
