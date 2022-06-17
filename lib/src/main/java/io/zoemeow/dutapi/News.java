package io.zoemeow.dutapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.zoemeow.dutapi.objects.NewsGlobal;
import io.zoemeow.dutapi.objects.NewsType;

public class News {
    public static ArrayList<NewsGlobal> getNews(NewsType newsType, Integer page) {
        ArrayList<NewsGlobal> newsList = null;
        HttpURLConnection client = null;
        
        try {
            newsList = new ArrayList<NewsGlobal>();
            String url = "http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=%s&PAGETB=%d&COL=TieuDe&NAME=&TAB=0";

            switch (newsType) {
                case Global:
                    url = String.format(url, "CTRTBSV", page);
                    break;
                case Subject:
                    url = String.format(url, "CTRTBGV", page);
                    break;
                default:
                    // TODO: Name exception here!
                    throw new Exception("1");
            }

            URL url2 = new URL(url);
            client = (HttpURLConnection) url2.openConnection();

            // TODO: Name exception here!
            if (client.getResponseCode() != 200 && client.getResponseCode() != 204)
                throw new Exception("2");
            
            // https://stackoverflow.com/questions/8616781/how-to-get-a-web-pages-source-code-from-java
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8")))
            {
                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(inputLine);
                }
            }

            // https://www.baeldung.com/java-with-jsoup
            Document webData = Jsoup.parse(stringBuilder.toString());
            
            // News General + News Subject
            Elements tbbox = webData.getElementsByClass("tbbox");
            for (Element tb1: tbbox) {
                NewsGlobal newsGeneralItem = new NewsGlobal();

                Element title = tb1.getElementsByClass("tbBoxCaption").get(0);
                String[] titleTemp = title.text().split(": ");
                Element content = tb1.getElementsByClass("tbBoxContent").get(0);

                if (titleTemp.length == 2) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    newsGeneralItem.setDate(LocalDate.parse(titleTemp[0], formatter));
                    newsGeneralItem.setTitle(titleTemp[1]);
                }
                else {
                    newsGeneralItem.setTitle(title.text());
                }

                newsGeneralItem.setContent(content.html());
                newsGeneralItem.setContentString(content.text());
                
                newsList.add(newsGeneralItem);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();

            newsList.clear();
            newsList = null;
        }

        return newsList;
    }


    
}
