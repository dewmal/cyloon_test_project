package io.egreen.seed.cyloon.crawler.dbclient;

import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;

/**
 * Created by dewmal on 12/10/15.
 */
public interface IDBClient {
    String save(SiteData siteData);
}
