package io.egreen.seed.indexing.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by dewmal on 12/4/15.
 */
public class IndexSiteData {




    @Field(child = false)
    private String post_id;
    @Field(child = false)
    private String id;
    @Field(child = false)
    private String title;
    @Field(child = false)
    private double price;
    @Field(child = false)
    private String location;
    @Field(child = false)
    private String ownerName;
    @Field(child = false)
    private String description;
    @Field(child = false)
    private String keywords;
    @Field()
    private Date post_last_update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getPost_last_update_time() {
        return post_last_update_time;
    }

    public void setPost_last_update_time(Date post_last_update_time) {
        this.post_last_update_time = post_last_update_time;
    }

    @Override
    public String toString() {
        return "IndexSiteData{" +
                "post_id='" + post_id + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", post_last_update_time=" + post_last_update_time +
                '}';
    }
}
