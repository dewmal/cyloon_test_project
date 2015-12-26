package io.egreen.cyloon.crawler.util;

import io.egreen.cyloon.crawler.process.impl.HtmlContentProcessor;

import java.util.Date;

/**
 * Created by dewmal on 12/19/15.
 */
public class JLogger {

    private Class<?> aClass;

    public JLogger(Class<?> aClass) {
        this.aClass = aClass;
    }

    public void log(Object o, Level level) {
//        System.out.println(aClass.getSimpleName() + " - " + new Date() + " - " + level + " - " + o);
    }

    public void info(Object o) {
        log(o, Level.INFO);
    }

    public static JLogger getInstance(Class<?> aClass) {
        return new JLogger(aClass);
    }


    public enum Level {

        INFO,
        DEBUG,
        ERROR

    }
}
