package io.egreen.cyloon.crawler.data;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by dewmal on 12/26/15.
 */
public class IndexSiteData extends SiteData {
    @Field
    private String id;

    public IndexSiteData() {
    }

    public IndexSiteData(SiteData content) {
        this.set_id(content.get_id());
        this.setId(content.get_id());
        this.setTitle(content.getTitle());
        this.setContent(content.getContent());
        this.setPrice(content.getPrice());
        this.setPostDateTimeS(content.getPostDateTimeS());
        this.setPostDateTime(content.getPostDateTime());
        this.setKeywords(content.getKeywords());
        this.setCurruncy(content.getCurruncy());
        this.setLocation(content.getLocation());
        this.setLink(content.getLink());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
