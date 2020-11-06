package com.soccer.demo.web;

import com.soccer.demo.crawling.ScheduleCrawling;
import com.soccer.demo.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ScheduleController {

    private final ScheduleCrawling scheduleCrawling;

    @GetMapping("/soccer/schedule")
    public String schedule(Model model) throws IOException {
        return "soccer/schedule";
    }

    @PostMapping("/soccer/schedule")
    public @ResponseBody List<ScheduleDto> scheduleAjax(@RequestBody String date) throws IOException {
        log.info("scheduleAjax start!");
        List<ScheduleDto> scheduleDtoList = scheduleCrawling.getSoccerSchedule(date.substring(1, date.length() - 1));
        log.info("scheduleAjax end!");
        if (scheduleDtoList == null) {
            return new ArrayList<ScheduleDto>();
        }
        return scheduleDtoList;
    }
}
