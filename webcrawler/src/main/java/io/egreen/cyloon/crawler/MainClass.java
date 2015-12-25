package io.egreen.cyloon.crawler;

import io.egreen.cyloon.crawler.analyser.IContentAnalyer;
import io.egreen.cyloon.crawler.data.IndexSiteData;
import io.egreen.cyloon.crawler.data.SiteData;
import io.egreen.cyloon.crawler.dbcon.SiteDataController;
import io.egreen.cyloon.crawler.index.SolrIndexBuilderImpl;
import io.egreen.cyloon.crawler.process.ICrawler;
import io.egreen.cyloon.crawler.process.custom.HitAdLKSiteDataProcessor;
import io.egreen.cyloon.crawler.process.custom.IkmanLKSiteDataProcessor;
import io.egreen.cyloon.crawler.process.impl.HtmlCrawler;
import io.egreen.cyloon.crawler.process.impl.UrlReource;
import io.egreen.cyloon.crawler.resources.manager.URLManager;
import io.egreen.cyloon.crawler.util.JLogger;
import org.apache.felix.ipojo.annotations.*;
import org.apache.solr.client.solrj.SolrServerException;

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

    @Requires
    private SolrIndexBuilderImpl solrIndexBuilder;


    public MainClass() {

        SiteDataController siteDataController = new SiteDataController();

        siteDataIContentAnalyer = new IContentAnalyer<SiteData>() {
            @Override
            public void preform(SiteData content) {

                String id = siteDataController.save(content);
                content.set_id(id);
                IndexSiteData indexSiteData = new IndexSiteData();
                indexSiteData.build(content);
                try {
                    solrIndexBuilder.indexing(indexSiteData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    @Validate
    public void validate() {


        iCrawler = new HtmlCrawler();
        urlManager = new URLManager(iCrawler, new SiteDataController());


        iCrawler.registerProcess(new IkmanLKSiteDataProcessor(siteDataIContentAnalyer));
        iCrawler.registerProcess(new HitAdLKSiteDataProcessor(siteDataIContentAnalyer));

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
