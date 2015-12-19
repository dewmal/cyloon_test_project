package io.egreen.cyloon.crawler.process.custom;

import io.egreen.cyloon.crawler.process.CrawledResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dewmal on 12/20/15.
 */
public class SiteDataPolicy {


    private final List<CrawledResource> dataPolicies = new ArrayList<CrawledResource>();


    public void addPolicy(CrawledResource dataPolicy) {
        dataPolicies.add(dataPolicy);
    }

    public void removePolicy(CrawledResource dataPolicy) {
//        throw new UnexpectedException("Need to impliment before use");
    }

}
