package crawler.dao;

public class SingleNewsDao {

    private static class SingletonHolder {
        private static final NewsDao INSTANCE = new NewsDao();
    }
    private SingleNewsDao (){}
    public static NewsDao getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
