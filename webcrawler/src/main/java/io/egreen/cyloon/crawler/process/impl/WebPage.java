package io.egreen.cyloon.crawler.process.impl;

import org.jsoup.nodes.Document;

import java.net.URL;

/**
 * Created by dewmal on 12/19/15.
 */
public class WebPage {


    private final URL url;
    private final Document document;

    public WebPage(URL url, Document document) {
        this.url = url;
        this.document = document;
    }

    public URL getUrl() {
        return url;
    }

    public Document getDocument() {
        return document;
    }
}
