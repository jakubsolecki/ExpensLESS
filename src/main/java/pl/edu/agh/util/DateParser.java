package pl.edu.agh.util;

import java.time.Month;

public class DateParser {
    public static String parse(Month month){
        return switch (month) {
            case JANUARY -> "01";
            case FEBRUARY -> "02";
            case MARCH -> "03";
            case APRIL -> "04";
            case MAY -> "05";
            case JUNE -> "06";
            case JULY -> "07";
            case AUGUST -> "08";
            case SEPTEMBER -> "09";
            case OCTOBER -> "10";
            case NOVEMBER -> "11";
            case DECEMBER -> "12";
        };
    }

    public static String parseToNext(Month month){
        return switch (month) {
            case JANUARY -> "02";
            case FEBRUARY -> "03";
            case MARCH -> "04";
            case APRIL -> "05";
            case MAY -> "06";
            case JUNE -> "07";
            case JULY -> "08";
            case AUGUST -> "09";
            case SEPTEMBER -> "10";
            case OCTOBER -> "11";
            case NOVEMBER -> "12";
            case DECEMBER -> "01";
        };
    }
}
