package io.egreen.seed.indexing.impl;

import io.egreen.seed.indexing.SolrIndexBuilder;
import io.egreen.seed.indexing.model.IndexSiteData;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.service.log.LogService;

import java.io.IOException;

/**
 * Created by dewmal on 12/10/15.
 */
//
@Component
@Instantiate
@Provides
public class SolrIndexBuilderImpl implements SolrIndexBuilder{

    @Requires
    private LogService logService;

    private SolrClient solrClient;

    public SolrIndexBuilderImpl() {
        try {
            solrClient = new HttpSolrClient("http://apps.egreenhive.com:8983/solr/cyloon_index_engine");//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    @Override
    public void indexing(IndexSiteData indexSiteData) throws IOException, SolrServerException {
        logService.log(LogService.LOG_DEBUG, indexSiteData + "");
//        System.out.println(indexSiteData);
//        solrClient.
        solrClient.addBean(indexSiteData);
        solrClient.commit();
    }
}
