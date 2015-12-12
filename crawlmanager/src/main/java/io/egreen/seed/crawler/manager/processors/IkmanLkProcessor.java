package io.egreen.seed.crawler.manager.processors;

import io.egreen.seed.crawler.manager.analysing.IAnalyser;
import io.egreen.seed.cyloon.crawler.IWebPageProcessor;
import io.egreen.seed.cyloon.crawler.dbclient.db.entity.SiteData;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dewmal on 10/12/15.
 */
public class IkmanLkProcessor extends AbstractWebPageProcessor implements IWebPageProcessor {


    public IkmanLkProcessor(IAnalyser iAnalyser) {
        super(iAnalyser);
    }

    public SiteData getCrawlerModel(Document doc, String url) throws HttpStatusException {
        SiteData siteData = new SiteData();


//        URL urlLink = new URL(url);

//            Document doc = Jsoup.connect(url).userAgent("Chrome").get();


        // Title
        Elements newsHeadlines = doc.select("h1[itemprop=name]");
        String title = newsHeadlines.get(0).html();
        siteData.setTitle(title);
//        System.out.println("title " + title);


        //Contact Name
        Elements contactNameElm = doc.select(".poster span");
        String contactName = contactNameElm.get(0).html();
        siteData.setOwnerName(contactName);

//        System.out.println("contactName " + contactName);


        //Contact Number
        Elements contactNumbers = doc.select(".item-contact-more.is-showable ul li span");

        siteData.setTpNumbers(new ArrayList<String>());
        for (Element contactNumberElm : contactNumbers) {
            String contactNumber = contactNumberElm.html();
            siteData.getTpNumbers().add(contactNumber);
        }


        //Location
        Elements locationElm = doc.select(".location");
        String location = locationElm.html();
        siteData.setLocation("Sri lanka, " + location);

        //Date
        Elements dateElm = doc.select(".date");
        String date = dateElm.html();

        siteData.setPostDateTimeS(date);
//         6 Oct 4:27 pm";
        DateFormat formatter = new SimpleDateFormat("dd MMM HH:mm a");
        try {

            Date postDate = (Date) formatter.parse(date);
            postDate.setYear(Calendar.getInstance().getTime().getYear());
            siteData.setPostDateTime(postDate);
        } catch (ParseException e) {
//            e.printStackTrace();
        }

        Elements amountElement = doc.select(".amount");
        String amount = amountElement.html();
        if (amount != null) {
            amount = amount.replace(",", "");
        }

        try {
            siteData.setPrice(Double.parseDouble(amount));
        } catch (Exception e) {

        }
        Elements descriptionElm = doc.select("div[itemprop=description]");
        String description = descriptionElm.text();
        siteData.setContent(description);

        List<String> images = new ArrayList();

        Elements imageElms = doc.select("img[data-srcset]");
        L1:
        for (Element imgElm : imageElms) {

            String image = imgElm.attr("data-srcset");
            String[] rowImages = image.replace("//", "").split(",");
            for (String rowImage : rowImages) {
//                System.out.println(rowImage);
                if (rowImage.contains("i.ikman-st.com") && rowImage.contains("fitted")) {
                    String imageUrl = rowImage.replace("1x", "").trim();
                    images.add("http://" + imageUrl);
                }
                break;
            }
        }

        siteData.setImages(images);
        return siteData;
    }

    @Override
    public boolean accept(String base) {
        return Pattern.matches("^(http|https)://ikman.lk/(en|si)/(ads|ad)/.*", base);
    }


}
