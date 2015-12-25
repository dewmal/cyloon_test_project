package io.egreen.cyloon.crawler.data;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

/**
 * Created by dewmal on 12/24/15.
 */
public class URLObject {
    @MongoObjectId
    private String _id;
    private String link;
    private Date lastVisit;
    private int visitCount;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public String toString() {
        return "URLObject{" +
                "ID='" + _id + '\'' +
                ", link='" + link + '\'' +
                ", lastVisit=" + lastVisit +
                ", visitCount=" + visitCount +
                '}';
    }
}
