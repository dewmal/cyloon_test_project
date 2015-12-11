package io.egreen.seed.indexing;

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

public interface SolrIndexBuilder {
    void indexing(IndexSiteData indexSiteData) throws IOException, SolrServerException;
}
