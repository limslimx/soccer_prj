package com.soccer.demo.dto;

import lombok.Data;

@Data
public class ScheduleDto {

    private String matchStatus;
    private String homeName;
    private String awayName;
    private String homeScore;
    private String awayScore;
    private String homeImg;
    private String awayImg;

}
