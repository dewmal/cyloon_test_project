package io.egreen.seed.crawler.manager;

import io.egreen.seed.crawler.manager.analysing.IAnalyser;
import io.egreen.seed.crawler.manager.processors.HitAddProcessor;
import io.egreen.seed.crawler.manager.processors.IkmanLkProcessor;
import io.egreen.seed.cyloon.crawler.ICrawler;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dewmal on 12/8/15.
 */
@Component
@Instantiate
public class Mainager5 {

    @Requires
    private ICrawler iCrawler;

    @Requires
    private IAnalyser iAnalyser;


    @Requires
    private HttpService httpService;

//    @Requires
//    private SolrIndexBuilder solrIndexBuilder;


    @Validate
    private void start() {

        try {
            iCrawler.crawl("ikman", "http://ikman.lk/", new IkmanLkProcessor(iAnalyser), "http://ikman.lk/", "http://ikman.lk/en/ads/ads-in-sri-lanka");
            iCrawler.crawl("hitad", "http://www.hitad.lk/", new HitAddProcessor(iAnalyser), "http://www.hitad.lk/", "http://www.hitad.lk/EN/property");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
//        try {
//            httpService.registerServlet("/start", new HttpServlet() {
//                @Override
//                protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                    String ok = "";
//                    try {
//                        iCrawler.crawl("ikman", "http://ikman.lk/", new IkmanLkProcessor(iAnalyser), "http://ikman.lk/", "http://ikman.lk/en/ads/ads-in-sri-lanka");
//                        iCrawler.crawl("hitad", "http://www.hitad.lk/", new HitAddProcessor(iAnalyser), "http://www.hitad.lk/", "http://www.hitad.lk/EN/property");
//                    } catch (InterruptedException e) {
//                        ok = e.getMessage();
//                    } catch (Exception e) {
//                        ok = e.getMessage();
//                    }
////                    super.doGet(req, resp);
//
//                    resp.getWriter().write("Start Crawler " + ok);
//                }
//
//            }, null, null);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (NamespaceException e) {
//            e.printStackTrace();
//        }


    }
}
