package io.egreen.seed.cyloon.crawler.dbclient.db.controller;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.Iterator;

/**
 * Created by dewmal on 12/3/15.
 */

public class SiteDataModelController {

    public Iterator<SiteData> getSiteData(String key) {
        DB db = new MongoClient("208.43.16.210", 27017).getDB("crawler_date_classified");

        Jongo jongo = new Jongo(db);
        MongoCollection crawler_site_row_data = jongo.getCollection("crawler_site_row_data");
//        System.out.println(.count());
        MongoCursor<SiteData> all = crawler_site_row_data.find("{ownerName: 'Eranga'}").sort("{postDateTime:-1}").as(SiteData.class);
//        System.out.println(all);
        return all.iterator();
    }
}
