package com.thoughtworks.myapp.domain;

import com.google.gson.annotations.SerializedName;

public class PM25 {
    @SerializedName("aqi")
    private String aqi;
    @SerializedName("area")
    private String area;
    @SerializedName("pm2_5")
    private String pm25;
    @SerializedName("pm2_5_24h")
    private String pm25Per24h;
    @SerializedName("position_name")
    private String positionName;
    @SerializedName("primary_pollutant")
    private String primaryPollutant;
    @SerializedName("quality")
    private String quality;
    @SerializedName("station_code")
    private String stationCode;
    @SerializedName("time_point")
    private String timePoint;

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm25Per24h() {
        return pm25Per24h;
    }

    public void setPm25Per24h(String pm25Per24h) {
        this.pm25Per24h = pm25Per24h;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPrimaryPollutant() {
        return primaryPollutant;
    }

    public void setPrimaryPollutant(String primaryPollutant) {
        this.primaryPollutant = primaryPollutant;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }
}
