package io.egreen.cyloon.crawler.process.impl;

import io.egreen.cyloon.crawler.process.ContentProcess;
import io.egreen.cyloon.crawler.util.JLogger;

/**
 * Created by dewmal on 12/19/15.
 */
public abstract class HtmlContentProcessor implements ContentProcess<WebPage> {

    private static final JLogger LOGGER = JLogger.getInstance(HtmlContentProcessor.class);

    private boolean isProcessing = false;


    @Override
    public void process(WebPage content) throws Exception {
        isProcessing = true;
        LOGGER.log(shouldAccept(content), JLogger.Level.INFO);
        if (shouldAccept(content)) {
            processWebPage(content);
        }

        isProcessing = false;
    }


    @Override
    public boolean isProcessorRunning() {
        return false;
    }


    protected abstract boolean shouldAccept(WebPage webPage);

    protected abstract void processWebPage(WebPage webPage) throws Exception;
}
