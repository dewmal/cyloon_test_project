package io.egreen.cyloon.crawler.process.impl;

import io.egreen.cyloon.crawler.process.Resource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by dewmal on 12/19/15.
 */
public class UrlReource implements Resource<URL> {

    private final String url;

    public UrlReource(String url) {
        this.url = url;
    }


    @Override
    public URL getResource() throws Exception {
        return new URL(url);
    }

    @Override
    public String toString() {
        return "UrlReource{" +
                "url='" + url + '\'' +
                '}';
    }
}
