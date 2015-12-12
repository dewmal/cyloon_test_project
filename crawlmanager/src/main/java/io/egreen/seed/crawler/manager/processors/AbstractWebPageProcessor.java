package io.egreen.seed.crawler.manager.processors;

import io.egreen.seed.crawler.manager.analysing.IAnalyser;
import io.egreen.seed.cyloon.crawler.IWebPageProcessor;
import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;

/**
 * Created by dewmal on 12/12/15.
 */
public abstract class AbstractWebPageProcessor implements IWebPageProcessor {
    
    protected abstract SiteData getCrawlerModel(Document doc, String url) throws HttpStatusException;

    private final IAnalyser iAnalyser;

    public AbstractWebPageProcessor(IAnalyser iAnalyser) {

        this.iAnalyser = iAnalyser;
    }


    @Override
    public void process(String url, Document document) {
        try {
            SiteData crawlerModel = getCrawlerModel(document, url);
            crawlerModel.setLink(url);

//            if (iAnalyser != null) {
            iAnalyser.analyse(crawlerModel);
//            }
//            System.out.println(crawlerModel);
        } catch (HttpStatusException e) {
            e.printStackTrace();
        }
    }

}
