package crawler;


public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            new CrawlerThread().start();
        }

    }
}
