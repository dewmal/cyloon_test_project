package io.egreen.cyloon.crawler.resources.manager;

/**
 * Created by dewmal on 12/19/15.
 */
public interface FeedUrls {

    void addUrl(String url) throws InterruptedException, Exception;

    void addUrls(String[] urls);


    boolean canAccept(String url);

}
