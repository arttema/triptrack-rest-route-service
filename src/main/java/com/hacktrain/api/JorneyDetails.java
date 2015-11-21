package com.hacktrain.api;

import JJPModel.Trip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JorneyDetails {
    public String strOrigin;
    public String strDestination;
    public String provider;

    public Date departTime;
    public Date arriveTime;

//    public List<StopStation> stop = new ArrayList<>();
    public int duration; // mins
    private Trip trip;

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
        return trip;
    }
}
