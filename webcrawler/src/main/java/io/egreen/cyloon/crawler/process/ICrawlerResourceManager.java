package io.egreen.cyloon.crawler.process;

import io.egreen.cyloon.crawler.process.impl.UrlReource;

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

    void pushNextResouce(Resource urlReource, boolean shouldStart);

    void start();
}
