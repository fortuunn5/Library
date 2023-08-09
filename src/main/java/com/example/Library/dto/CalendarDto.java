package com.example.Library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {
    private int month;
    private int year;
    private int day=3;

    public CalendarDto(int month, int year) {
        this.month = month;
        this.year = year;
    }
}
