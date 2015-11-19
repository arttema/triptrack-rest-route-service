package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public class RouteMapExample {

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                      Route Map Request                           ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Route Map request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Route Map response received:\n");
				
				PrintResponse(RESTClient.getResponse());
			}
		}	
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());			
		}	
		
	}
	
	private void PrintResponse(String response) 
	{
		try 
		{
			JSONObject j = new JSONObject(response);	
			
			if (Utils.CheckResponseStatus(j))
			{			
				JSONArray routes = j.getJSONArray("Routes");
				JSONArray stopPatterns = j.getJSONArray("StopPatterns");
				JSONArray mapSegments = j.getJSONArray("MapSegments");
				JSONArray transitStops = j.getJSONArray("TransitStops");
							
				System.out.println(String.format("Number of Routes in Response       : %4d", routes.length()));
				System.out.println(String.format("Number of Stop Patterns in Response: %4d", stopPatterns.length()));
				System.out.println(String.format("Number of Map Segments in Response : %4d", mapSegments.length()));
				System.out.println(String.format("Number of Transit Stops in Response: %4d", transitStops.length()));
			}
		} 
		catch (Exception e)
		{			
			System.out.println(String.format("Exception during extraction of json response: %s", e.getMessage()));
		}				
	}

	private String MakeRequest(String API_KEY) 
	{		
		return String.format
		(
				"/DataSets/%s/RouteMap?ApiKey=%s&Route=%s&MappingDataRequired=true&format=json",				
				"Perth",
				API_KEY,				
				"Perth:RTG_10"	// A Route Timetable Group ID, or the RouteUid of a route from the Route Timetable Group of interest
		);
	}
}
