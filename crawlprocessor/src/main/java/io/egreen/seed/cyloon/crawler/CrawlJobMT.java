package io.egreen.seed.cyloon.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.osgi.service.log.LogService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jakob Jenkov and Emmanuel John
 */
public class CrawlJobMT implements Runnable {

    protected CrawlerMT crawler = null;
    private IWebPageProcessor webPageProcessor;
    protected String urlToCrawl = null;

    public CrawlJobMT(String urlToCrawl, CrawlerMT crawler, IWebPageProcessor webPageProcessor) {
        this.urlToCrawl = urlToCrawl;
        this.crawler = crawler;
        this.webPageProcessor = webPageProcessor;
    }

    @Override
    public void run() {
        try {
            crawl();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void crawl() throws IOException {

        URL url = new URL(this.urlToCrawl);

        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            try {
                InputStream input = urlConnection.getInputStream();
                Document doc = Jsoup.parse(input, "UTF-8", "");


                // Process Web Page
                if (webPageProcessor != null) {
                    if (webPageProcessor.accept(this.urlToCrawl)) {
                        webPageProcessor.process(this.urlToCrawl, doc);
                    }
                }


                Elements elements = doc.select("a");
//                System.out.println(elements.size());
                String baseUrl = url.toExternalForm();
                String[] urls = new String[elements.size()];
                int i = 0;
                for (Element element : elements) {
                    String linkUrl = element.attr("href");
                    String normalizedUrl = UrlNormalizer.normalize(linkUrl, baseUrl);
                    urls[i] = normalizedUrl;
                    i++;
//                    System.out.println(" - " + normalizedUrl);

                }
                crawler.logService.log(LogService.LOG_DEBUG, "Processed URL " + url);
//                synchronized (crawler) {
                crawler.addUrl(urls);
//                }
                if (crawler.barrier.getNumberWaiting() == 1) {
                    crawler.barrier.await();
                }

            } catch (IOException e) {
                throw new RuntimeException("Error connecting to URL " + url.toString(), e);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlJobMT.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(CrawlJobMT.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException("Error connecting to URL", e);
        }
    }

}
