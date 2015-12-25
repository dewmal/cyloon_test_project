package io.egreen.cyloon.crawler.data;

import io.egreen.cyloon.crawler.process.CrawledResource;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;
import java.util.List;

/**
 * Created by dewmal on 12/20/15.
 */
public class SiteData extends CrawledResource{

    @MongoObjectId
    private String _id;
    private String title;
    private String content;
    private String keywords;
    private List<String> images;
    private double price;
    private String curruncy;
    private String location;
    private String ownerName;
    private List<String> tpNumbers;
    private String email;
    private Date postDateTime;
    private String postDateTimeS;
    private String link;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurruncy() {
        return curruncy;
    }

    public void setCurruncy(String curruncy) {
        this.curruncy = curruncy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        this.link = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<String> getTpNumbers() {
        return tpNumbers;
    }

    public void setTpNumbers(List<String> tpNumbers) {
        this.tpNumbers = tpNumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getPostDateTime() {
        return postDateTime;
    }

    public void setPostDateTime(Date postDateTime) {
        this.postDateTime = postDateTime;
    }

    public String getPostDateTimeS() {
        return postDateTimeS;
    }

    public void setPostDateTimeS(String postDateTimeS) {
        this.postDateTimeS = postDateTimeS;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "SiteData{" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", keywords='" + keywords + '\'' +
                ", images=" + images +
                ", price=" + price +
                ", curruncy='" + curruncy + '\'' +
                ", location='" + location + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", tpNumbers=" + tpNumbers +
                ", email='" + email + '\'' +
                ", postDateTime=" + postDateTime +
                ", postDateTimeS='" + postDateTimeS + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
