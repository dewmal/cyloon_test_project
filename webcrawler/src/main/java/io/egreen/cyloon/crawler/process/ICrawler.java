package io.egreen.cyloon.crawler.process;

/**
 * Created by dewmal on 12/19/15.
 */
public interface ICrawler extends ICrawlerProcessor, ICrawlerResourceManager {


    void stopCrawling();

}
