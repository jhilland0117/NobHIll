package net.effect117.mapping;

/**
 * Created by JHilland on 8/31/2015.
 */

public class AssetsReader {

    public String title;
    public String latitude;
    public String longitude;
    public String buildingAbbr;

    public String getBuildingAbbr() {
        return buildingAbbr;
    }
    public void setBuildingAbbr(String buildingAbbr) {
        this.buildingAbbr = buildingAbbr;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}