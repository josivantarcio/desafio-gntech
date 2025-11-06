package com.weather.weather.api.dto;
import lombok.Data;
import java.util.List;

@Data
public class MeteomaticsResponse {
    private String version;
    private String user;
    private String dateGenerated;
    private String status;
    private List<DataItem> data;
    @Data
    public static class DataItem {
        private String parameter;
        private List<Coordinate> coordinates;
    }
    @Data
    public static class Coordinate {
        private double lat;
        private double lon;
        private List<DateValue> dates;
    }
    @Data
    public static class DateValue {
        private String date;
        private double value;
    }
}
