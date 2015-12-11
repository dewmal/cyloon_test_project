package processors;

import io.egreen.seed.crawler.manager.analysing.IAnalyser;
import io.egreen.seed.crawler.manager.analysing.impl.Analyser;
import io.egreen.seed.crawler.manager.processors.IkmanLkProcessor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dewmal on 12/10/15.
 */
public class TestProcessor {

    //    private static String urlDoc = "http://ikman.lk/en/ad/samsung-s3-replica-for-sale-kurunegala-3";
    private static String urlDoc = "http://ikman.lk/en/ad/samsung-s3-mini-original-for-sale-kurunegala-30";
    private static Analyser anaylyser;

    public static void main(String[] args) {
        anaylyser = new Analyser();
        anaylyser.registered(null);

        IkmanLkProcessor ikmanLkProcessor = new IkmanLkProcessor(anaylyser);

//        Connection connect = Jsoup.connect();

        URLConnection urlConnection = null;
        try {
            URL url = new URL(urlDoc);

            urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

            InputStream input = urlConnection.getInputStream();
            Document doc = Jsoup.parse(input, "UTF-8", "");

            ikmanLkProcessor.process(url.toString(), doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
