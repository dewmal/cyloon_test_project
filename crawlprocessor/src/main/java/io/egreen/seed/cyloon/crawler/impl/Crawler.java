package io.egreen.seed.cyloon.crawler.impl;

import io.egreen.seed.cyloon.crawler.*;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.jsoup.nodes.Document;
import org.osgi.service.log.LogService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dewmal on 12/9/15.
 */
@Component
@Instantiate
@Provides
public class Crawler implements ICrawler {

    @Requires
    private LogService logService;


    private static final List<IWebPageProcessor> PROCESSORS = new ArrayList<IWebPageProcessor>();


    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    private ExecutorService executor = Executors.newCachedThreadPool();


    private IWebPageProcessor WebPageProcessor = new IWebPageProcessor() {
        @Override
        public boolean accept(String base) {
            return true;
        }

        @Override
        public void process(final String url, final Document document) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (IWebPageProcessor processor : PROCESSORS) {
                        if (processor.accept(url)) {
                            processor.process(url, document);
                        }
                    }
                }
            });
        }
    };

    @Override
    public void crawl(final String name, final String baseurl, final IWebPageProcessor webPageProcessor, final String... seeds) throws InterruptedException {
//        executor.submit(new Runnable() {
//            @Override
////            public void run() {
        PROCESSORS.add(webPageProcessor);
//        System.out.println(PROCESSORS);
        String url = baseurl;
        CrawlerMT crawler = new CrawlerMT(name, logService,new DBHelper(name), new SameWebsiteOnlyFilter(url), Crawler.this.WebPageProcessor);
        try {
            crawler.addUrl(seeds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        crawler.crawl();
//            }
//        });


    }


}
