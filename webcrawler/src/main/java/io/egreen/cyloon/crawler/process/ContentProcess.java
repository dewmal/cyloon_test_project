package io.egreen.cyloon.crawler.process;

import java.io.IOException;

/**
 * Created by dewmal on 12/19/15.
 */
public interface ContentProcess<T> {


    void process(T content) throws Exception;


    /**
     * Process
     *
     * @return
     */
    boolean isProcessorRunning();

}
