package JJPModel;

import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;


public class Trip 
{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TripSummary m_summary;
	private Vector<TripStop> m_stops;
	
	public Trip()
	{	
		m_stops = new Vector<TripStop>();
		m_summary = null;
	}

    public void resetSummary(){m_summary = null;
    for(TripStop s : m_stops){
        s.getTransitStop().setDataSet(null);
        s.getTransitStop().setStopUid(null);
    }
    }
	public void setSummary(TripSummary summary) 
	{
		m_summary = summary;
	}
	public TripSummary getSummary() 
	{
		return m_summary;
	}
	public void setStops(Vector<TripStop> stops) 
	{
		m_stops = stops;
	}
	
	public Vector<TripStop> getStops() 
	{
		return m_stops;
	}

	public void addStopsFromJSONArray(JSONArray stops) throws Exception
	{
		for(int i = 0; i < stops.length(); i++)
		{			
			JSONObject jsonStop = stops.getJSONObject(i);
			
			JSONObject jsonTransitStop = jsonStop.getJSONObject("TransitStop");			
			
			TransitStop transitStop = TransitStop.FromJson(jsonTransitStop);
			
			TripStop tripStop = new TripStop();
			
			tripStop.setTransitStop(transitStop);
			tripStop.setSequenceNumber(jsonStop.getString("SequenceNumber"));	
			
			if (jsonStop.has("ArrivalTime"))
			{
				tripStop.setArriveTime(Utils.getDateFromString(jsonStop.getString("ArrivalTime")));
			}
			
			if (jsonStop.has("DepartureTime"))
			{
				tripStop.setDepartTime(Utils.getDateFromString(jsonStop.getString("DepartureTime")));
			}
						
			m_stops.add(tripStop);
		}
		
	}
	
	public void setSummary(JSONObject jsonSummary) throws Exception 
	{
		JSONObject jsonServiceProvider = jsonSummary.getJSONObject("ServiceProvider");
		
		TripSummary summary = new TripSummary
								(
										jsonSummary.getString("Mode"),	
										jsonSummary.getString("TripUid"),
										jsonSummary.getString("TripCode"),
										jsonSummary.getString("RouteName"),
										jsonSummary.getString("RouteCode"),
										jsonSummary.getString("Headsign")
								);			
		
		ServiceProvider serviceProvider = new ServiceProvider
												(
														jsonServiceProvider.getString("ServiceProviderUid"),		
														jsonServiceProvider.getString("Name"),
														jsonServiceProvider.getString("TimeZone"),
														jsonServiceProvider.getString("PhoneNumber"),
														jsonServiceProvider.getString("Url")
												);
		
		summary.setServiceProvider(serviceProvider);
		
		setSummary(summary);		
	}

}
