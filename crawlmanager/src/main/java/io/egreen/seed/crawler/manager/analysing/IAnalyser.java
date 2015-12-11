package io.egreen.seed.crawler.manager.analysing;


import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;

/**
 * Created by dewmal on 12/10/15.
 */
public interface IAnalyser {

    void analyse(SiteData siteData);
}
