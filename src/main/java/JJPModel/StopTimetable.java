package JJPModel;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public class StopTimetable 
{
    private TransitStop requestedStop;
    private Vector<StopTimetableTrip> m_trips;   
    

    public StopTimetable
    (      
           TransitStop requestedStop,
           StopTimetableTrip[] trips
    )
    {    
           this.requestedStop = requestedStop;
           this.m_trips = new Vector<StopTimetableTrip>();     
    }  

    
    public StopTimetable() 
    {
    	requestedStop = null;
        m_trips = new Vector<StopTimetableTrip>();
	}


	public TransitStop getRequestedStop() 
    {
        return requestedStop;
    }
    
    public void setRequestedStop(TransitStop requestedStop) 
    {
        this.requestedStop = requestedStop;
    }

    public Vector<StopTimetableTrip> getTrips() 
    {
        return m_trips;
    }
    
    public void setRequestedStop(JSONObject requestedStop) throws Exception 
    {
		TransitStop location = TransitStop.FromJson(requestedStop);
		setRequestedStop(location);
	}	


	public void addTripsFromJSONArray(JSONArray trips) throws Exception 
	{
		for(int i = 0; i < trips.length(); i++)
		{			
			JSONObject jsonTrip = trips.getJSONObject(i);
			
			JSONObject jsonSummary = jsonTrip.getJSONObject("Summary");
			
			StopTimetableTrip stopTimetableTrip = new StopTimetableTrip();
			stopTimetableTrip.setArriveTime(Utils.getDateFromString(jsonTrip.getString("ArriveTime")));
			stopTimetableTrip.setDepartTime(Utils.getDateFromString(jsonTrip.getString("DepartTime")));
						
			TripSummary summary = new TripSummary
										(
											jsonSummary.getString("Mode"),	
											jsonSummary.getString("TripUid"),
											jsonSummary.getString("TripCode"),
											jsonSummary.getString("RouteName"),
											jsonSummary.getString("RouteCode"),
											jsonSummary.getString("Headsign")
										);
			
			
			stopTimetableTrip.setSummary(summary);	
			
			m_trips.add(stopTimetableTrip);
		}		
	}
}
