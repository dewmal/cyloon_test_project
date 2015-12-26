package io.egreen.cyloon.crawler.resources.manager;

import io.egreen.cyloon.crawler.SITEPolicy;
import io.egreen.cyloon.crawler.data.URLObject;
import io.egreen.cyloon.crawler.dbcon.SiteDataController;
import io.egreen.cyloon.crawler.process.ContentProcess;
import io.egreen.cyloon.crawler.process.ICrawlerResourceManager;
import io.egreen.cyloon.crawler.process.impl.UrlReource;
import io.egreen.cyloon.crawler.process.impl.WebPage;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * Created by dewmal on 12/19/15.
 */
public class URLManager implements ContentProcess<WebPage>, FeedUrls {


    private final LinkedBlockingQueue<String> urls = new LinkedBlockingQueue<String>();

    private final SeedURLGrabber seedURLGrabber = new SeedURLGrabber(this);

    private final ICrawlerResourceManager iCrawlerResouceManager;
    private SiteDataController siteDataController;


    private ExecutorService executorService = Executors.newCachedThreadPool();

    public URLManager(ICrawlerResourceManager iCrawlerResouceManager, SiteDataController siteDataController) {
        this.iCrawlerResouceManager = iCrawlerResouceManager;
        this.siteDataController = siteDataController;

        Iterator<URLObject> allLinks = siteDataController.getAllLinks();

        while (allLinks.hasNext()) {
            URLObject next = allLinks.next();
            iCrawlerResouceManager.pushNextResouce(new UrlReource(next.getLink()), false);
        }
        iCrawlerResouceManager.start();
    }

    @Override
    public void process(final WebPage content) throws Exception {

        seedURLGrabber.process(content);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                URLObject urlObject = new URLObject();
                urlObject.setLink(content.getUrl() + "");
                urlObject.setLastVisit(new Date());
                siteDataController.updateLink(urlObject);
            }
        });

    }

    @Override
    public boolean isProcessorRunning() {
        return seedURLGrabber.isProcessorRunning();
    }


    @Override
    public void addUrl(final String url) throws Exception {
        boolean shouldProcess = false;
        for (String policy : SITEPolicy.POLICIES) {
            if (Pattern.matches(policy, url)) {
                shouldProcess = true;
                break;
            }
        }

//        System.out.println(url + " " + shouldProcess);

        if (shouldProcess) {
            executorService.submit(new Runnable() {
                                       @Override
                                       public void run() {
                                           URLObject urlObject = new URLObject();
                                           urlObject.setLink(url);
                                           urlObject.setLastVisit(new Date());
                                           urlObject.setVisitCount(0);


                                           siteDataController.updateLink(urlObject);

                                           try {
                                               iCrawlerResouceManager.pushNextResouce(new UrlReource(url));
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                       }
                                   }

            );
        }
    }

    @Override
    public void addUrls(String[] urls) {

    }

    @Override
    public boolean canAccept(String url) {
        return true;
    }
}
