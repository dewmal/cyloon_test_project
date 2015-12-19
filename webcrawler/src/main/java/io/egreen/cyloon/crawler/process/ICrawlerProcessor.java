package io.egreen.cyloon.crawler.process;

/**
 * Created by dewmal on 12/19/15.
 */
public interface ICrawlerProcessor {
    /**
     * Register Processor
     *
     * @param process
     */
    void registerProcess(ContentProcess process);
}
