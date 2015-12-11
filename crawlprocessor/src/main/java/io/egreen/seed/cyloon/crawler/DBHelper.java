package io.egreen.seed.cyloon.crawler;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dewmal on 12/9/15.
 */
public class DBHelper {
    protected final LinkedBlockingQueue<String> linksQueue = new LinkedBlockingQueue();




    public void add(String... urls) throws InterruptedException {
//        System.out.println("App " + linksQueue + " " + Arrays.toString(urls));
        for (String url : urls) {
            if (url != null){
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