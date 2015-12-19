package io.egreen.cyloon.crawler.resources.manager;

import io.egreen.cyloon.crawler.process.ContentProcess;
import io.egreen.cyloon.crawler.process.ICrawlerResourceManager;
import io.egreen.cyloon.crawler.process.impl.UrlReource;
import io.egreen.cyloon.crawler.process.impl.WebPage;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dewmal on 12/19/15.
 */
public class URLManager implements ContentProcess<WebPage>, FeedUrls {

    private final LinkedBlockingQueue<String> urls = new LinkedBlockingQueue<String>();

    private final SeedURLGrabber seedURLGrabber = new SeedURLGrabber(this);

    private final ICrawlerResourceManager iCrawlerResouceManager;

    public URLManager(ICrawlerResourceManager iCrawlerResouceManager) {
        this.iCrawlerResouceManager = iCrawlerResouceManager;
    }

    @Override
    public void process(WebPage content) throws Exception {
        seedURLGrabber.process(content);
    }

    @Override
    public boolean isProcessorRunning() {
        return seedURLGrabber.isProcessorRunning();
    }


    @Override
    public void addUrl(String url) throws Exception {
        iCrawlerResouceManager.pushNextResouce(new UrlReource(url));
    }

    @Override
    public void addUrls(String[] urls) {

    }

    @Override
    public boolean canAccept(String url) {
        return true;
    }
}
