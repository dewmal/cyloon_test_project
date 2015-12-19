package io.egreen.cyloon.crawler.process;

/**
 * Created by dewmal on 12/19/15.
 */
public interface Resource<T> {

    /**
     *  Get resource
     * @return
     */
    T getResource() throws Exception;
}
