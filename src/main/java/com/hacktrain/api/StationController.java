package com.hacktrain.api;

import Client.JourneyPlanExample;
import Client.TimetableExample;
import Common.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
class StationController {
    @Value("${silversearch.apikey}")
    private String apiKey;
    private static final String API_KEY = "1333ecbd-2a86-08a5-7168-d325c905a731";


    @RequestMapping(value = "api/train", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public JorneyDetails accounts(@RequestParam(value = "from") final String from,
                                  @RequestParam(value = "to") final String to,
                                  @RequestParam(value = "departTime") final String departTime) {
        JourneyPlanExample b = new JourneyPlanExample();
//        JorneyDetails res = b.Run(API_KEY, "OXF", "PAD", "2015-11-21T08:53");
        JorneyDetails res = b.Run(API_KEY, from, to, departTime);

        return res;
    }

    public static void main(String[] args) {

        JourneyPlanExample b = new JourneyPlanExample();

        b.Run(API_KEY,"OXF", "PAD", "2015-11-21T08:53");
    }
}
