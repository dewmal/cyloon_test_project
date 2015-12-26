package io.egreen.cyloon.crawler.process.impl;

import io.egreen.cyloon.crawler.process.ContentProcess;
import io.egreen.cyloon.crawler.process.ICrawler;
import io.egreen.cyloon.crawler.process.Resource;
import io.egreen.cyloon.crawler.util.JLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * HTML Web Page Crawler implementation
 * <p>
 * Created by dewmal on 12/19/15.
 */
public class HtmlCrawler implements ICrawler, ContentProcess<Resource<URL>> {

    private static final JLogger LOGGER = JLogger.getInstance(HtmlCrawler.class);

    // Content processor for let them know when process data arrived
    private List<ContentProcess<WebPage>> contentProcesses = new ArrayList<ContentProcess<WebPage>>();

    // Add All Crawler process to Queue for process one by one
    public final Queue<Resource<URL>> resources = new LinkedBlockingQueue<Resource<URL>>();


    private static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);

    public HtmlCrawler() {
        EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);
    }

    @Override
    public void pushNextResouce(Resource resource) throws InterruptedException, IOException {
//        LOGGER.log(resource, JLogger.Level.DEBUG);


        pushNextResouce(resource, true);
    }

    @Override
    public void pushNextResouce(Resource resource, boolean shouldStart) {
        resources.add(resource);
        if (!isProcessorRunning() && shouldStart) {
            try {
                process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        try {
            process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerProcess(ContentProcess process) {
        contentProcesses.add(process);
    }


    private boolean processorIsRunning = false;

    /**
     * @return
     */
    public boolean isProcessorRunning() {
        return processorIsRunning;
    }

    private void process() throws Exception {
        processorIsRunning = true;
        Resource<URL> resource = resources.poll();
//        LOGGER.log(resource, JLogger.Level.DEBUG);

        while (resource != null && !(EXECUTOR_SERVICE.isShutdown() || EXECUTOR_SERVICE.isTerminated())) {
            final Resource<URL> processableResource = resource;

            EXECUTOR_SERVICE.schedule(new Runnable() {
                @Override
                public void run() {
                    for (ContentProcess<WebPage> contentProcess : contentProcesses) {
                        try {
                            process(processableResource);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            },1, TimeUnit.NANOSECONDS);
            resource = resources.poll();
//            LOGGER.info("mve to next " + resource);
        }
        processorIsRunning = false;
    }

    @Override
    public void process(Resource<URL> content) throws Exception {
        URL url = content.getResource();
//        LOGGER.log(url, JLogger.Level.DEBUG);
        URLConnection urlConnection = null;

//        LOGGER.log(urlConnection, JLogger.Level.DEBUG);

        urlConnection = url.openConnection();
//        LOGGER.log(urlConnection, JLogger.Level.DEBUG);

        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        InputStream input = urlConnection.getInputStream();
        Document doc = Jsoup.parse(input, "UTF-8", "");
//        LOGGER.log(doc, JLogger.Level.DEBUG);


        for (ContentProcess<WebPage> contentProcess : contentProcesses) {
            contentProcess.process(new WebPage(url, doc));
        }


    }

    @Override
    public void stopCrawling() {
        EXECUTOR_SERVICE.shutdownNow();
    }
}
