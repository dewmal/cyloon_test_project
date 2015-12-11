package io.egreen.seed.cyloon.crawler.impl;

import io.egreen.seed.cyloon.crawler.CrawlerMT;
import io.egreen.seed.cyloon.crawler.ICrawler;
import io.egreen.seed.cyloon.crawler.IWebPageProcessor;
import io.egreen.seed.cyloon.crawler.SameWebsiteOnlyFilter;
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
    public void crawl(String baseurl, IWebPageProcessor webPageProcessor, String... seeds) throws InterruptedException {
        PROCESSORS.add(webPageProcessor);
//        System.out.println(PROCESSORS);
        String url = baseurl;
        CrawlerMT crawler = new CrawlerMT(logService, new SameWebsiteOnlyFilter(url), this.WebPageProcessor);
        crawler.addUrl(seeds);
        crawler.crawl();
    }


}
