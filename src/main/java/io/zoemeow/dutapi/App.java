package io.zoemeow.dutapi;

import io.zoemeow.dutapi.objects.NewsType;


public class App {
    public static void main(String[] args) {
        System.out.println(News.getNews(NewsType.Global, 1));

    }
}
