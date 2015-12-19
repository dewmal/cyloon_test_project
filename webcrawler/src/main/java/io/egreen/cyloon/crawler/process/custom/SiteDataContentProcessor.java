package io.egreen.cyloon.crawler.process.custom;

import io.egreen.cyloon.crawler.analyser.IContentAnalyer;
import io.egreen.cyloon.crawler.data.SiteData;
import io.egreen.cyloon.crawler.process.CrawledResource;
import io.egreen.cyloon.crawler.process.impl.HtmlContentProcessor;
import io.egreen.cyloon.crawler.process.impl.WebPage;
import org.jsoup.nodes.Document;

/**
 * Created by dewmal on 12/20/15.
 */
public abstract class SiteDataContentProcessor<T extends CrawledResource> extends HtmlContentProcessor {


    protected SiteDataContentProcessor(IContentAnalyer<T> siteDataIContentAnalyer) {
        this.siteDataIContentAnalyer = siteDataIContentAnalyer;
    }

    protected abstract T getCrawlerModel(Document document) throws Exception;

    protected abstract boolean accept(String url);


    private final IContentAnalyer<T> siteDataIContentAnalyer;


    @Override
    protected boolean shouldAccept(WebPage webPage) {
        System.out.println(webPage.getUrl());
        return accept(webPage.getUrl().toString());
    }


    @Override
    protected void processWebPage(WebPage webPage) throws Exception {
        T siteData = getCrawlerModel(webPage.getDocument());
        siteData.setLocation(webPage.getUrl() + "");
        siteDataIContentAnalyer.preform(siteData);
    }

}
