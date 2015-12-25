package io.egreen.cyloon.crawler.resources.manager;

import io.egreen.cyloon.crawler.data.URLObject;
import io.egreen.cyloon.crawler.dbcon.SiteDataController;
import io.egreen.cyloon.crawler.process.ContentProcess;
import io.egreen.cyloon.crawler.process.ICrawlerResourceManager;
import io.egreen.cyloon.crawler.process.impl.UrlReource;
import io.egreen.cyloon.crawler.process.impl.WebPage;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dewmal on 12/19/15.
 */
public class URLManager implements ContentProcess<WebPage>, FeedUrls {

//    static {
//
//        // load the sqlite-JDBC driver using the current class loader
//        try {
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }


    private final LinkedBlockingQueue<String> urls = new LinkedBlockingQueue<String>();

    private final SeedURLGrabber seedURLGrabber = new SeedURLGrabber(this);

    private final ICrawlerResourceManager iCrawlerResouceManager;
    private SiteDataController siteDataController;

    public URLManager(ICrawlerResourceManager iCrawlerResouceManager, SiteDataController siteDataController) {
        this.iCrawlerResouceManager = iCrawlerResouceManager;
        this.siteDataController = siteDataController;
    }

    @Override
    public void process(WebPage content) throws Exception {
        seedURLGrabber.process(content);

        URLObject urlObject = new URLObject();
        urlObject.setLink(content.getUrl() + "");
        urlObject.setLastVisit(new Date());

        siteDataController.updateLink(urlObject);

    }

    @Override
    public boolean isProcessorRunning() {
        return seedURLGrabber.isProcessorRunning();
    }


    @Override
    public void addUrl(String url) throws Exception {

        URLObject urlObject = new URLObject();
        urlObject.setLink(url);
        urlObject.setLastVisit(new Date());
        urlObject.setVisitCount(0);
        siteDataController.updateLink(urlObject);

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
