package io.egreen.seed.config;

import org.apache.felix.ipojo.annotations.Component;
import org.osgi.framework.BundleContext;

/**
 * Created by dewmal on 12/7/15.
 */
@Component
public class ConfigManager {


    private static final String CRAWLER_BASE_DIR = "core.cyloon.crawler.path";


    public Config getConfig(BundleContext bundleContext) {
        String crawlerBase = bundleContext.getProperty(CRAWLER_BASE_DIR);
        return new Config(crawlerBase);
    }


    public class Config {

        private String baseLocation;
        private String name;

        private Config(String baseLocation) {
            this.baseLocation = baseLocation;
        }

        public Config name(String name) {
            this.name = name;
            return Config.this;
        }

        public String getBaseLocation() {
            return baseLocation;
        }

        public void setBaseLocation(String baseLocation) {
            this.baseLocation = baseLocation;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
