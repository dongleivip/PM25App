package com.thoughtworks.myapp.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dl on 2015/12/25.
 */
public class AQI {

    @SerializedName("aqi")
    private String aqi;

    @SerializedName("area")
    private String area;

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
