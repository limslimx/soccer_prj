package com.soccer.demo.crawling;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

@Slf4j
public class RankCrawling {

    public static void main(String[] args) throws IOException {
        getSoccerRank();
    }

    public static void getSoccerRank() throws IOException {
        String url = "https://www.goal.com/kr/%ED%94%84%EB%A6%AC%EB%AF%B8%EC%96%B4%EB%A6%AC%EA%B7%B8/%EC%88%9C%EC%9C%84/2kwbbcootiqqgmrzs6o5inle5";
        Document doc = Jsoup.connect(url).get();
        Elements tableElements = doc.select("table.p0c-competition-tables__table tr.p0c-competition-tables__row");
        Iterator<Element> tableElementsIterator = tableElements.iterator();
        while (tableElementsIterator.hasNext()) {
            Element tableElement = tableElementsIterator.next();
            String rank = tableElement.select("td:nth-child(1)").html();
            String[] teamLogo = tableElement.select("td:nth-child(3)").html().split("src=");
            String teamName = tableElement.select("td.p0c-competition-tables__team span.p0c-competition-tables__team--full-name").html();

            log.info("rank: " + rank);
            log.info("teamLogo: " + teamLogo[0]);
            log.info("teamLogo: " + teamLogo[1]);
            log.info("teamName: " + teamName);
        }

    }
}
