package io.egreen.seed.dbsearchbridge;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import java.io.IOException;

/**
 * Created by dewmal on 12/2/15.
 */


public class IndexBuilder {

    //
//    @Requires
//    HttpService httpService;

//    @Property("core.cyloon.crawler.path")
//    String crawlerDb;


    public void starting(IndexSiteData siteData) throws IOException, SolrServerException, NamespaceException {
        SolrClient solrClient = new HttpSolrClient("http://apps.egreenhive.com:8983/solr/cyloon_index_engine");
        solrClient.addBean(siteData);
        solrClient.commit();
    }

    /**
     * Stopping.
     */
//    @Invalidate
    public void stopping() {
        System.out.println("NOW stopping");

    }


}
