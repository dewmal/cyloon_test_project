package io.egreen.cyloon.crawler.process.custom;


import io.egreen.cyloon.crawler.analyser.IContentAnalyer;
import io.egreen.cyloon.crawler.data.SiteData;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by dewmal on 10/12/15.
 */
public class HitAdLKSiteDataProcessor extends SiteDataContentProcessor<SiteData> {


    public HitAdLKSiteDataProcessor(IContentAnalyer<SiteData> siteDataIContentAnalyer) {
        super(siteDataIContentAnalyer);
    }


    @Override
    public boolean accept(String pattern) {
        return Pattern.matches("^(http|https)://www.hitad.lk/(EN|en|En|eN)/.*./.*.", pattern);
    }

    public SiteData getCrawlerModel(Document doc) throws HttpStatusException {

//
//  page.$()

        SiteData siteData = new SiteData();
        
        // Title
        Elements newsHeadlines = doc.select(".adz-head").select("h4");
        String title = newsHeadlines.text();
        siteData.setTitle(title);
//        System.out.println("title " + title);


        //Date
        Elements dateElm = doc.select(".adz-head").select("span");
        String date = dateElm.text();
        siteData.setPostDateTimeS(date);
//         6 Oct 4:27 pm";
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy - HH:mm a");
        try {

            Date postDate = (Date) formatter.parse(date);
            postDate.setYear(Calendar.getInstance().getTime().getYear());
            siteData.setPostDateTime(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        fw_b fs_14 row

        Elements details = doc.select(".fw_b.fs_14.row").select(".col-lg-12");
//            System.out.println(details.get(0).html());

        List<String> keyWords = new ArrayList();
        for (Element detail : details) {
//                System.out.println(detail.html());
            if (detail != null && detail.text() != null && detail.text().toLowerCase().startsWith("published by :")) {
                String contactName = detail.text().toLowerCase().replace("published by :", "");
                siteData.setOwnerName(contactName);
            }

            if (detail != null && detail.text() != null && detail.text().toLowerCase().startsWith("location :")) {
                String location = detail.text().toLowerCase().replace("location :", "");
                siteData.setLocation(location);
            }
            keyWords.add(detail.text());

        }
        if (keyWords != null && !keyWords.isEmpty()) {
            siteData.setKeywords(Arrays.toString(keyWords.toArray()));
        }

        Elements script = doc.body().select("script");
        for (Element element : script) {
            String[] strings = element.html().split("var xCode =");
            if (strings.length == 2) {
                String value = strings[1].trim().split(";")[0];

                ScriptEngineManager factory = new ScriptEngineManager();
                ScriptEngine engine = factory.getEngineByName("JavaScript");

                Object eval = null;
                try {
                    eval = engine.eval("function unbox(s){var e={},i,k,v=[],r='',w=String.fromCharCode;var n=[[65,91],[97,123],[48,58],[43,44],[47,48]];for(z in n){for(i=n[z][0];i<n[z][1];i++){v.push(w(i));}}for(i=0;i<64;i++){e[v[i]]=i;}for(i=0;i<s.length;i+=72){var b=0,c,x,l=0,o=s.substring(i,i+72);for(x=0;x<o.length;x++){c=e[o.charAt(x)];b=(b<<6)+c;l+=6;while(l>=8){r+=w((b>>>(l-=8))%256);}}}return r;};unbox(" + value + ")");
                    siteData.setTpNumbers(new ArrayList<String>());
//            for (Element contactNumberElm : contactNumbers) {
                    String contactNumber = "+94" + eval;
                    siteData.getTpNumbers().add(contactNumber);

                    break;
                } catch (ScriptException e) {
                    e.printStackTrace();
                }


            }
        }
//


        Elements amountElement = doc.select(".Price p");
        String amount = amountElement.html();
        if (amount != null) {
            amount = amount.replace(",", "").replace("Rs.", "");
        }

        try {
            siteData.setPrice(Double.parseDouble(amount));
        } catch (Exception e) {

        }
        Elements descriptionElm = doc.select(".mar_t_20");
        String description = descriptionElm.text();
        siteData.setContent(description);

        List<String> images = new ArrayList();

        Elements imageElms = doc.select(".slides.slide-main li");
        L1:
        for (Element imgElm : imageElms) {
//                System.out.println(imgElm.html());
//                System.out.println();
            if (imgElm.select("img").attr("src") != null || !imgElm.select("img").attr("src").isEmpty())
                images.add(imgElm.select("img").attr("src"));
            if (imgElm.select("img").attr("data") != null || !imgElm.select("img").attr("data").isEmpty())
                images.add(imgElm.select("img").attr("data"));
        }


        siteData.setImages(images);
//        } catch (Exception e) {
//            System.out.println(siteData);
//            e.printStackTrace();
//            return null;
//        }
        return siteData;
    }


}
