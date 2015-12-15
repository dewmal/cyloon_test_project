package io.egreen.seed.crawler.manager;

import io.egreen.seed.crawler.manager.analysing.IAnalyser;
import io.egreen.seed.crawler.manager.analysing.impl.Analyser;
import io.egreen.seed.crawler.manager.processors.HitAddProcessor;
import io.egreen.seed.crawler.manager.processors.IkmanLkProcessor;
import io.egreen.seed.cyloon.crawler.ICrawler;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

/**
 * Created by dewmal on 12/8/15.
 */
@Component
@Instantiate
public class Mainager5 {

    @Requires
    private ICrawler iCrawler;

    @Requires
    private IAnalyser iAnalyser;

//    @Requires
//    private SolrIndexBuilder solrIndexBuilder;


    @Validate
    private void start() {
        try {
            iCrawler.crawl("ikman", "http://ikman.lk/", new IkmanLkProcessor(iAnalyser), "http://ikman.lk/", "http://ikman.lk/en/ads/ads-in-sri-lanka");
            iCrawler.crawl("hitad", "http://www.hitad.lk/", new HitAddProcessor(iAnalyser), "http://www.hitad.lk/", "http://www.hitad.lk/EN/property");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
