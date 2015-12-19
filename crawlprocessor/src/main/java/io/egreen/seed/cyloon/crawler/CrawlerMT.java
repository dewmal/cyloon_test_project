package io.egreen.seed.cyloon.crawler;

import org.osgi.service.log.LogService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Jakob Jenkov and Emmanuel John
 */
public class CrawlerMT {
    private final String name;
    protected final LogService logService;

    protected IUrlFilter urlFilter = null;
    private IWebPageProcessor webPageProcessor;

    protected Set<String> crawledUrls = new HashSet<String>();
    private ScheduledExecutorService crawlService;
    private DBHelper dbHelper;

    //    protected final LinkedBlockingQueue<String> linksQueue = new LinkedBlockingQueue();
    protected CyclicBarrier barrier = new CyclicBarrier(2);

    private boolean isCrawlerRun = false;
    private boolean crawlerRunning = false;


    public CrawlerMT(String name, LogService logService, DBHelper dbHelper, IUrlFilter urlFilter, IWebPageProcessor webPageProcessor) {
        this.name = name;

        this.logService = logService;
        this.dbHelper = dbHelper;
        this.urlFilter = urlFilter;
        this.webPageProcessor = webPageProcessor;
        if (crawlService == null) {
            crawlService = Executors.newScheduledThreadPool(10);
        }
    }


    public void addUrl(String... url) throws InterruptedException {

//        System.out.println(url);
        dbHelper.add(url);
        if (!crawlerRunning && isCrawlerRun) {
            crawl();
        }


    }

    public void crawl() {
//        logService.log(LogService.LOG_DEBUG, "Again" + dbHelper.linksQueue.size() + "");

//        System.out.println("Start again - " + name + " - " + dbHelper.linksQueue.size());
        isCrawlerRun = true;
        crawlerRunning = true;

        long startTime = System.currentTimeMillis();

        //create thread pool

        int count = 0;

        while (!dbHelper.isEmpty()) {
            String nextUrl = null;
            try {
//                synchronized (dbHelper) {
                nextUrl = dbHelper.take();
//                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (nextUrl == null)
                System.out.println("queue is null here");
            if (!shouldCrawlUrl(nextUrl)) continue; // skip this URL.
            this.crawledUrls.add(nextUrl);
            logService.log(LogService.LOG_DEBUG, "Visit" + nextUrl + "");

            CrawlJobMT crawlJob = new CrawlJobMT(nextUrl, this, webPageProcessor);
            crawlService.schedule(crawlJob, 100, TimeUnit.MILLISECONDS);

        }

        crawlerRunning = false;


        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

//        System.out.println("URL's crawled: " + count + " in " + totalTime);

    }

    private boolean shouldCrawlUrl(String nextUrl) {
        if (this.urlFilter != null && !this.urlFilter.include(nextUrl)) {
            return false;
        }
        if (this.crawledUrls.contains(nextUrl)) {
            return false;
        }
        if (nextUrl.startsWith("javascript:")) {
            return false;
        }
        if (nextUrl.startsWith("#")) {
            return false;
        }
        if (nextUrl.endsWith(".swf")) {
            return false;
        }
        if (nextUrl.endsWith(".pdf")) {
            return false;
        }
        if (nextUrl.endsWith(".png")) {
            return false;
        }
        if (nextUrl.endsWith(".gif")) {
            return false;
        }
        if (nextUrl.endsWith(".jpg")) {
            return false;
        }
        if (nextUrl.endsWith(".jpeg")) {
            return false;
        }

        return true;
    }


}
