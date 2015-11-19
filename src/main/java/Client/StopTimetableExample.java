package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;
import JJPModel.StopTimetable;
import JJPModel.StopTimetableTrip;


public class StopTimetableExample 
{

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                     Stop Timetable Request                             ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Stop Timetable request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Stop Timetable Response received:\n");
				
				StopTimetable st = ParseStopTimetable(RESTClient.getResponse());
				
				if (st != null)
				{		
					PrintStopTimetable(st);
				}
			}
		}	
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());			
		}		
	}
	
	private StopTimetable ParseStopTimetable(String jsonStopTimetable) throws Exception
	{
		StopTimetable st = new StopTimetable();
		
		try 
		{
			JSONObject j = new JSONObject(jsonStopTimetable);
			
			if (!Utils.CheckResponseStatus(j))
			{
				return null;
			}
			
			JSONArray trips = j.getJSONArray("Trips");
			JSONObject requestedStop = j.getJSONObject("RequestedStop");			
			
			st.setRequestedStop(requestedStop);
			st.addTripsFromJSONArray(trips);
					
			return st;			
		} 
		catch (Exception e)
		{			
			throw e;
		}		
	}	

	private void PrintStopTimetable(StopTimetable stopTimetable) 
	{
		System.out.println(String.format("Transit Stop Details:\n"));
		System.out.println(String.format("Stop Name: %s", stopTimetable.getRequestedStop().getDescription()));
		System.out.println(String.format("Stop UID: %s		Stop Code: %s		Stop Position: %s",
										stopTimetable.getRequestedStop().getStopUid(),
										stopTimetable.getRequestedStop().getStopCode(),
										stopTimetable.getRequestedStop().getPosition())
						  );	
		
		System.out.println("\nTrip Summary:\n");
		
		if (stopTimetable.getTrips() == null || stopTimetable.getTrips().isEmpty())
		{
			System.out.println("No trips departing or arriving this stop at this time.\n");
		}
		
		for (StopTimetableTrip stopTimetableTrip : stopTimetable.getTrips())
		{
			 // Output arrival and departure time for each trip
			System.out.println(
								String.format(
												"Arriving: %s Departing: %s ",
												Utils.getSimpleTimeString(stopTimetableTrip.getArriveTime()),
												Utils.getSimpleTimeString(stopTimetableTrip.getDepartTime())
											  )
							  );

            // Output the summary information for each trip.
			System.out.println
						(
							String.format(
											"UID:%s %s RouteName: %s RouteCode: %s",
											stopTimetableTrip.getSummary().getTripUid(),
											stopTimetableTrip.getSummary().getMode(),
											stopTimetableTrip.getSummary().getRouteName(),
											stopTimetableTrip.getSummary().getRouteCode()
										  )
						);
		}
	}

	private String MakeRequest(String API_KEY) 
	{		
		return String.format
		(
				"DataSets/%s/StopTimetable?ApiKey=%s&StopUid=%s&TripFilter=Both&Time=%s&TimeBand=120&MaxTripCount=20&format=json",
				"Perth",
				API_KEY,				
				"Perth:10021",					
				Utils.GetCurrentDateTime()
		);
		

        // Trips returned by the stop timetable request can be filtered by route, by supplying a "routes" parameter with a
		// semi-colon delimited list of RouteUids. 
        // A list of routes and their Uids are obtained by using the ReferenceData request. See ReferenceDataExample.java.

	}
}
