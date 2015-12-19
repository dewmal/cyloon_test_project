package io.egreen.seed.cyloon.crawler;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dewmal on 12/9/15.
 */
public class DBHelper {

    private static final String BASE = "";
    protected final LinkedBlockingQueue<String> linksQueue;
    private String dbName;


    public DBHelper(String dbName) {
        this.dbName = dbName;

        linksQueue = new LinkedBlockingQueue<String>();

    }

    public void add(String... urls) throws InterruptedException {
//        System.out.println("App " + linksQueue + " " + Arrays.toString(urls));
        for (String url : urls) {
            if (url != null) {
                linksQueue.add(url);
            }
        }
    }

    public boolean isEmpty() {
        return linksQueue.isEmpty();
    }

    public String take() throws InterruptedException {
//        System.out.println("GET " + linksQueue.size());
        return linksQueue.take();
    }

    public int getCount() {
        return linksQueue.size();
    }
}
