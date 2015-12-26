package io.egreen.cyloon.crawler;

import io.egreen.cyloon.crawler.analyser.IContentAnalyer;
import io.egreen.cyloon.crawler.data.IndexSiteData;
import io.egreen.cyloon.crawler.data.SiteData;
import io.egreen.cyloon.crawler.dbcon.SiteDataController;
import io.egreen.cyloon.crawler.process.ICrawler;
import io.egreen.cyloon.crawler.process.custom.HitAdLKSiteDataProcessor;
import io.egreen.cyloon.crawler.process.custom.IkmanLKSiteDataProcessor;
import io.egreen.cyloon.crawler.process.impl.HtmlCrawler;
import io.egreen.cyloon.crawler.process.impl.UrlReource;
import io.egreen.cyloon.crawler.resources.manager.URLManager;
import io.egreen.cyloon.crawler.util.JLogger;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Created by dewmal on 12/17/15.
 */
@Component
@Instantiate
public class MainClass {

    private static final JLogger LOGGER = JLogger.getInstance(MainClass.class);
    private final SiteDataController siteDataController;
    private final SolrIndexBuilderImpl solrIndexBuilder;

    private ICrawler iCrawler;


    private IContentAnalyer<SiteData> siteDataIContentAnalyer;


    private URLManager urlManager;


    private ExecutorService executor = Executors.newCachedThreadPool();


    public MainClass() {
        solrIndexBuilder = new SolrIndexBuilderImpl();
        siteDataController = new SiteDataController();

        siteDataIContentAnalyer = new IContentAnalyer<SiteData>() {
            @Override
            public void preform(final SiteData content) {


//                        System.out.println(content);
                String id = siteDataController.save(content);

                try {
                    solrIndexBuilder.indexing(new IndexSiteData(content));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }

//                        content.set_id(id);
//                        IndexSiteData indexSiteData = new IndexSiteData();
//                        indexSiteData.build(content);
//                        indexSiteData.setPost_id(id);
//                        try {
//                            solrIndexBuilder.indexing(indexSiteData);
//                        } catch (IOException e) {
//
//                            e.printStackTrace();
//                        } catch (SolrServerException e) {
//                            e.printStackTrace();
//                        } finally {
//                            System.out.println(indexSiteData);
//

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
            iCrawler.pushNextResouce(new UrlReource("http://www.hitad.lk/EN//any"));
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


    public static void main(String[] args) {
        boolean matches = Pattern.matches("^(http|https)://ikman.lk/en/ad/.*.[^/edit|/delete|/.*]$", "http://ikman.lk/en/ad/gtx-550-ti-gaming-graphic-card-for-sale-anuradhapura");
        System.out.println(matches);
    }
}
