package io.egreen.seed.cyloon.crawler;

/**
 * Created by dewmal on 12/9/15.
 */
public interface ICrawler{

        void crawl(String name,String baseurl, IWebPageProcessor webPageProcessor, String... seeds) throws InterruptedException;
}
