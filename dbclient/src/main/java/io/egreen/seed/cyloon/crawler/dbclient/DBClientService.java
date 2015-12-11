package io.egreen.seed.cyloon.crawler.dbclient;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.PostRegistration;
import org.apache.felix.ipojo.annotations.Provides;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.osgi.framework.ServiceReference;

/**
 * Created by dewmal on 12/3/15.
 * //
 */
@Component
@Provides(strategy = "SERVICE")
@Instantiate
public class DBClientService implements IDBClient {


    private Jongo jongo;
    private DB db;

    public DBClientService() {

        db = new MongoClient("208.43.16.210", 27017).getDB("crawler_date_classified");
        jongo = new Jongo(db);
    }

    @PostRegistration
    public void registered(ServiceReference ref) {
//        dbClientService = new DBClientService();
//        dbClientService.init();

//        solrIndexBuilder = new SolrIndexBuilder();

    }

    public String save(SiteData siteData) {
        String id = null;
//        System.out.println(siteData);

        MongoCollection sitedata=null;
        try {
            sitedata = jongo.getCollection("sitedata");
        }catch (Exception e){
            e.printStackTrace();
        }

        SiteData data = sitedata.findOne("{link: '" + siteData.getLink() + "'}").as(SiteData.class);
        System.out.println(data);


        if (data != null) {
            sitedata.update(new ObjectId(data.get_id())).with(siteData);
        } else {
            WriteResult save = sitedata.save(siteData);
            System.out.println(save);

        }

//        System.out.println(siteData);
//

        return siteData.get_id();
    }
}
