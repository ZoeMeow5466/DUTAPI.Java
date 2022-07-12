package io.zoemeow.dutapi;

import io.zoemeow.dutapi.objects.LinkItem;
import io.zoemeow.dutapi.objects.NewsGlobalItem;
import io.zoemeow.dutapi.objects.NewsType;
import io.zoemeow.dutapi.objects.customrequest.CustomResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class News {
    public static ArrayList<NewsGlobalItem> getNews(NewsType newsType, Integer page) throws Exception {
        String url = "";

        switch (newsType) {
            case Global:
                url = String.format(Variables.URL_NEWS, "CTRTBSV", page);
                break;
            case Subject:
                url = String.format(Variables.URL_NEWS, "CTRTBGV", page);
                break;
        }

        CustomResponse response = CustomRequest.get(null, url);
        if (response.getReturnCode() < 200 || response.getReturnCode() >= 300)
            throw new Exception("Server was returned with code "+ response.getReturnCode() + ".");

        // https://www.baeldung.com/java-with-jsoup

        Document webData = Jsoup.parse(response.getContentHtmlString());
        webData.outputSettings().charset(StandardCharsets.UTF_8);
        for (Element el: webData.getElementsByTag("br")) {
            el.remove();
        }

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
            newsItem.setContentString(content.wholeText());

            // Find links and set to item
            ArrayList<LinkItem> links = new ArrayList<>();
            int position = 0;
            String temp1 = content.wholeText();
            Elements temp2 = content.getElementsByTag("a");
            for (Element item: temp2) {
                if (temp1.contains(item.wholeText())) {
                    position += temp1.indexOf(item.wholeText());
                    LinkItem item1 = new LinkItem(
                            item.wholeText(),
                            item.attr("abs:href"),
                            position
                    );
                    links.add(item1);
                    position += item.wholeText().length();

                    // https://stackoverflow.com/questions/24220509/exception-when-replacing-brackets
                    String[] temp3 = temp1.split(Pattern.quote(item.wholeText()),2);
                    if (temp3.length > 1)
                        temp1 = temp3[1];
                }
            }
            newsItem.setLinks(links);

            newsList.add(newsItem);
        }

        return newsList;
    }
}
