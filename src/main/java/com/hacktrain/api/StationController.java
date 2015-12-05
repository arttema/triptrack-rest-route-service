package com.hacktrain.api;

import Client.JourneyPlanExample;
import JJPModel.GeoPosition;
import JJPModel.TripStop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Vector;

@Controller
class StationController {
    @Value("${silversearch.apikey}")
    private String apiKey;


    @RequestMapping(value = "api/train", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public JorneyDetails accounts(@RequestParam(value = "from") final String from,
                                  @RequestParam(value = "to") final String to,
                                  @RequestParam(value = "departTime") final String departTime) {
        JourneyPlanExample b = new JourneyPlanExample();
//        JorneyDetails res = b.Run(API_KEY, "OXF", "PAD", "2015-11-21T08:53");
        JorneyDetails res = b.Run(apiKey, from, to, departTime);
        res.getTrip();

        Vector<TripStop> stops = res.getTrip().getStops();
        GeoPosition st = stops.get(0).getTransitStop().getPosition();
        GeoPosition end = stops.get(stops.size() - 1).getTransitStop().getPosition();
        res.getTrip().resetSummary();
        res.distance = (int) distance(st.getLtitude(), end.getLtitude(), st.getLongitude(), end.getLongitude(), 0D, 0D);

        res.citiesCrossed = res.getTrip().getStops().size();
        return res;
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
