package io.egreen.cyloon.crawler.resources.manager;

import io.egreen.cyloon.crawler.process.ContentProcess;
import io.egreen.cyloon.crawler.process.impl.WebPage;
import io.egreen.cyloon.crawler.util.JLogger;
import io.egreen.cyloon.crawler.util.UrlNormalizer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by dewmal on 12/19/15.
 */
public class SeedURLGrabber implements ContentProcess<WebPage> {

    private static final JLogger LOGGER = JLogger.getInstance(SeedURLGrabber.class);
    private boolean isProcessorRunnging = false;

    private final FeedUrls feedUrls;


    public SeedURLGrabber(FeedUrls feedUrls) {
        this.feedUrls = feedUrls;

    }


    @Override
    public void process(WebPage content) throws Exception {
        isProcessorRunnging = true;
        Elements aTags = content.getDocument().select("a");
        URL url = content.getUrl();
        String baseUrl = url.getProtocol() + "://" + url.getHost();
        for (Element aTag : aTags) {
            String normalize = UrlNormalizer.normalize(aTag.attr("href"), baseUrl);
            if (normalize != null && normalize.startsWith(baseUrl) && feedUrls.canAccept(normalize)) {
                feedUrls.addUrl(normalize);
            }
        }
        LOGGER.log(url.getHost() + " - " + aTags.size(), JLogger.Level.INFO);
        isProcessorRunnging = false;
    }

    @Override
    public boolean isProcessorRunning() {
        return isProcessorRunnging;
    }
}
