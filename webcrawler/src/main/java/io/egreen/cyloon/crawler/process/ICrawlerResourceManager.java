package io.egreen.cyloon.crawler.process;

/**
 * Created by dewmal on 12/19/15.
 */
public interface ICrawlerResourceManager {
    /**
     * Push next resource to crawling
     *
     * @param resource
     */
    void pushNextResouce(Resource resource) throws Exception;
}
