package io.zoemeow.dutapi;

import io.zoemeow.dutapi.objects.customrequest.CustomResponse;
import io.zoemeow.dutapi.objects.LinkItem;
import io.zoemeow.dutapi.objects.NewsGlobalItem;
import io.zoemeow.dutapi.objects.NewsType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SuppressWarnings("SpellCheckingInspection")
public class News {
    public static ArrayList<NewsGlobalItem> getNews(NewsType newsType, Integer page) throws Exception {
        String url = switch (newsType) {
            case Global -> String.format(Variables.URL_NEWS, "CTRTBSV", page);
            case Subject -> String.format(Variables.URL_NEWS, "CTRTBGV", page);
        };

        CustomResponse response = CustomRequest.get(null, url);
        if (response.getReturnCode() < 200 || response.getReturnCode() >= 300)
            throw new Exception("Server was returned with code "+ response.getReturnCode() + ".");

        // https://www.baeldung.com/java-with-jsoup
        Document webData = Jsoup.parse(response.getContentHtmlString());

        // News General + News Subject
        Elements tbBox = webData.getElementsByClass("tbbox");

        ArrayList<NewsGlobalItem> newsList = new ArrayList<>();
        for (Element tb1: tbBox) {
            NewsGlobalItem newsItem = new NewsGlobalItem();

            Element title = tb1.getElementsByClass("tbBoxCaption").get(0);
            String[] titleTemp = title.text().split(":", 2);
            Element content = tb1.getElementsByClass("tbBoxContent").get(0);

            if (titleTemp.length == 2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(titleTemp[0], formatter);
                LocalTime time = LocalTime.parse("00:00:00");
                LocalDateTime dateTime = date.atTime(time);
                newsItem.setDate(dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
                newsItem.setTitle(titleTemp[1].trim());
            }
            else newsItem.setTitle(title.text().trim());

            newsItem.setContent(content.html());
            newsItem.setContentString(content.text());

            // Find links and set to item
            ArrayList<LinkItem> links = new ArrayList<>();
            int position = 0;
            String temp1 = content.text();
            Elements temp2 = content.getElementsByTag("a");
            for (Element item: temp2) {
                if (temp1.contains(item.text())) {
                    position = position + temp1.indexOf(item.text());
                    LinkItem item1 = new LinkItem(
                            item.text(),
                            item.attr("abs:href"),
                            position
                    );
                    links.add(item1);
                }
            }
            newsItem.setLinks(links);

            newsList.add(newsItem);
        }

        return newsList;
    }
}
