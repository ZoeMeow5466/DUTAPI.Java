package io.zoemeow.dutapi;

import io.zoemeow.dutapi.objects.LinkItem;
import io.zoemeow.dutapi.objects.NewsGlobalItem;
import io.zoemeow.dutapi.objects.NewsType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsTest {

    @Test
    void getNews() throws Exception {
        int page = 1;
        int pageMax = 5;

        while (page <= pageMax) {
            System.out.println("==================================");
            System.out.println("Page " + page);

            List<NewsGlobalItem> newsList = News.getNews(NewsType.Global, 1);
            System.out.println("Item count: " + newsList.size());

            for (NewsGlobalItem newsItem: newsList) {
                System.out.println(newsItem.getDate());
                System.out.println(newsItem.getTitle());
                System.out.println(newsItem.getContentString());
                for (LinkItem linkItem: newsItem.getLinks()) {
                    System.out.println("Links:");
                    System.out.println(" - " + linkItem.getText());
                    System.out.println(" - " + linkItem.getUrl());
                    System.out.println(" - " + linkItem.getPosition());
                }
            }
            page += 1;
        }
    }
}