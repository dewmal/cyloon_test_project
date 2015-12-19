package io.egreen.cyloon.crawler;

import io.egreen.cyloon.crawler.analyser.IContentAnalyer;
import io.egreen.cyloon.crawler.data.SiteData;
import io.egreen.cyloon.crawler.process.ICrawler;
import io.egreen.cyloon.crawler.process.custom.SiteDataProcessor;
import io.egreen.cyloon.crawler.process.impl.HtmlCrawler;
import io.egreen.cyloon.crawler.process.impl.UrlReource;
import io.egreen.cyloon.crawler.resources.manager.URLManager;
import io.egreen.cyloon.crawler.util.JLogger;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;

import java.io.IOException;

/**
 * Created by dewmal on 12/17/15.
 */
@Component
@Instantiate
public class MainClass {

    private static final JLogger LOGGER = JLogger.getInstance(MainClass.class);

    private ICrawler iCrawler;


    private IContentAnalyer<SiteData> siteDataIContentAnalyer;


    private URLManager urlManager;


    public MainClass() {

        siteDataIContentAnalyer = new IContentAnalyer<SiteData>() {
            @Override
            public void preform(SiteData content) {

            }
        };

    }

    @Validate
    public void validate() {
        LOGGER.info("Working ");

        iCrawler = new HtmlCrawler();
        urlManager = new URLManager(iCrawler);


        iCrawler.registerProcess(new SiteDataProcessor(siteDataIContentAnalyer));

        iCrawler.registerProcess(urlManager);


        try {
            iCrawler.pushNextResouce(new UrlReource("http://www.hitad.lk/"));
            iCrawler.pushNextResouce(new UrlReource("http://ikman.lk/en/ads/ads-in-anuradhapura-1452"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Invalidate
    void invalidate() {
        iCrawler.stopCrawling();
        HtmlCrawler htmlCrawler = (HtmlCrawler) iCrawler;
        System.out.println(htmlCrawler.resources);
        ;
    }

}
