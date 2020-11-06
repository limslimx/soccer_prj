package com.soccer.demo.crawling;

import com.soccer.demo.dto.ScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleCrawling {

//    public static void main(String[] args) throws IOException {
//        getSoccerSchedule("2020-08-15");
//    }

    public List<ScheduleDto> getSoccerSchedule(String date) throws IOException {
        List<ScheduleDto> scheduleDtoList = new ArrayList<ScheduleDto>();
        String url = "https://www.goal.com/kr/%ED%94%84%EB%A6%AC%EB%AF%B8%EC%96%B4-%EB%A6%AC%EA%B7%B8/%EC%9D%BC%EC%A0%95-%EA%B2%B0%EA%B3%BC/" + date +"/2kwbbcootiqqgmrzs6o5inle5";

        Document doc = Jsoup.connect(url).get();

        if (!doc.select("div.match-status").text().equals("")) {

            Elements matchInfo = doc.select("div.widget-competition-matches");

            Elements matchElements = matchInfo.select(" > div.match-row");

            for (Element element : matchElements) {
                String matchStatus = element.select("div.match-status").text();

                Elements matchData = element.select("div.match-data");
                String homeScore = matchData.select("div.team-home span.goals").text();
                String homeName = matchData.select("div.team-home span.team-name").text();

                String[] homeImgHtml = matchData.select("div.team-home span.crest noscript").html().split("src=");
                String[] homeImgHtml2 = homeImgHtml[1].split(" ");
                String homeImg = homeImgHtml2[0].substring(1, homeImgHtml2[0].length() - 1);

                String awayScore = matchData.select("div.team-away span.goals").text();
                String awayName = matchData.select("div.team-away span.team-name").text();
                String[] awayImgHtml = matchData.select("div.team-away span.crest noscript").html().split("src=");
                String[] awayImgHtml2 = awayImgHtml[1].split(" ");
                String awayImg = awayImgHtml2[0].substring(1, awayImgHtml2[0].length() - 1);

                if (matchStatus.contains("FT") || matchStatus.contains("연장")) {
                    matchStatus = "경기 종료";
                } else {
                    homeScore = " ";
                    awayScore = " ";
                    matchStatus += " 경기 예정";
                }
                ScheduleDto scheduleDto = new ScheduleDto();
                scheduleDto.setMatchStatus(matchStatus);
                scheduleDto.setHomeName(homeName);
                scheduleDto.setHomeScore(homeScore);
                scheduleDto.setHomeImg(homeImg);
                scheduleDto.setAwayName(awayName);
                scheduleDto.setAwayScore(awayScore);
                scheduleDto.setAwayImg(awayImg);
                scheduleDtoList.add(scheduleDto);
            }
        } else {
            scheduleDtoList = null;
        }

        return scheduleDtoList;
    }
}
