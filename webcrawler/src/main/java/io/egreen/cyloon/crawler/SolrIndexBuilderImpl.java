package io.egreen.cyloon.crawler;

import io.egreen.cyloon.crawler.data.IndexSiteData;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;

/**
 * Created by dewmal on 12/26/15.
 */
public class SolrIndexBuilderImpl {
    private SolrClient solrClient;

    private static int count = 0;

    public SolrIndexBuilderImpl() {
        String solrHost = System.getenv().get("SOLR_HOST");
        try {
            if (solrHost != null) {
                solrClient = new HttpSolrClient(solrHost);//
            } else {
                solrHost = "http://apps.egreenhive.com:8983/solr/cyloonengine";
                solrClient = new HttpSolrClient(solrHost);//

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    @Override
    public void indexing(IndexSiteData indexSiteData) throws IOException, SolrServerException {
//        logService.log(LogService.LOG_DEBUG, indexSiteData + "");
        solrClient.addBean(indexSiteData);
//        count++;
//        if (count > 5) {
//        count = 0;
        solrClient.commit();

//        }
    }
}
