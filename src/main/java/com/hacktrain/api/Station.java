package com.hacktrain.api;

public class Station {
    private String name;
    private String city;
    private String code;
    private String geoLocation;

    public String getName() {
        return name;
    }

    public Station(String name, String city, String code, String geoLocation) {
        this.name = name;
        this.city = city;
        this.code = code;
        this.geoLocation = geoLocation;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public Station() {

    }
}
