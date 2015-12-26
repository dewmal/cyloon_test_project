package io.egreen.cyloon.crawler.index;

import io.egreen.cyloon.crawler.data.IndexSiteData;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.log.LogService;

import java.io.IOException;
import java.util.List;

/**
 * Created by dewmal on 12/10/15.
 */
//
@Component
@Instantiate
@Provides
public class SolrIndexBuilderImpl {

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

        SolrQuery query = new SolrQuery();
        query.setQuery(indexSiteData.getPost_id());
//        query.setSort(new SolrQuery.SortClause())
        QueryResponse query1 = solrClient.query(query);
        SolrDocumentList results = query1.getResults();
//        List<IndexSiteData> beans = query1.getBeans(IndexSiteData.class);
        if (results.getNumFound() == 0) {
            solrClient.addBean(indexSiteData);
        }
        solrClient.commit();
    }
}
