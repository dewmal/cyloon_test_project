package io.egreen.seed.crawler.manager.analysing.impl;

import io.egreen.seed.crawler.manager.analysing.IAnalyser;
import io.egreen.seed.cyloon.crawler.dbclient.DBClientService;
import io.egreen.seed.cyloon.crawler.dbclient.IDBClient;
import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;
import io.egreen.seed.indexing.SolrIndexBuilder;
import io.egreen.seed.indexing.model.IndexSiteData;
import org.apache.felix.ipojo.annotations.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.framework.ServiceReference;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dewmal on 12/10/15.
 */
@Component
@Instantiate
@Provides
public class Analyser implements IAnalyser {


    @Requires
    private IDBClient dbClientService;

    @Requires
    private SolrIndexBuilder solrIndexBuilder;

    private final ExecutorService executorService;

//    @PostRegistration
//    public void registered(ServiceReference ref) {
////        dbClientService = new DBClientService();
////        dbClientService.init();
//
////        solrIndexBuilder = new SolrIndexBuilder();
//    }


    public Analyser() {
        executorService = Executors.newFixedThreadPool(5);
    }


    @Override
    public synchronized void analyse(final SiteData siteData) {
        executorService.submit(new AnalysingProcess(siteData));
    }

    /**
     * Preform Analysing process with Indexing and Saving
     */
    private class AnalysingProcess implements Runnable {
        final SiteData siteData;

        private AnalysingProcess(SiteData siteData) {
            this.siteData = siteData;
        }

        @Override
        public void run() {
            System.out.println(siteData);
            IndexSiteData indexSiteData = new IndexSiteData();
            try {
                String id = dbClientService.save(siteData);
//                System.out.println(id);
                indexSiteData.setPost_last_update_time(siteData.getPostDateTime());
                indexSiteData.setTitle(siteData.getTitle());
                indexSiteData.setDescription(siteData.getContent());
                indexSiteData.setLocation(siteData.getLocation());
                indexSiteData.setPrice(siteData.getPrice());
                indexSiteData.setKeywords(siteData.getKeywords());
                indexSiteData.setPost_id(id);
//
                solrIndexBuilder.indexing(indexSiteData);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
        }
    }
}
