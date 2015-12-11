package io.egreen.seed.cyloon.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by dewmal on 12/9/15.
 */
public interface IWebPageProcessor {

    boolean accept(String base);


    /**
     * Process Web page
     *
     * @param url
     * @param document
     */
    void process(String url, Document document);
}
