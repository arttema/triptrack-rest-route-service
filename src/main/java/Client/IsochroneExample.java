package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public class IsochroneExample {

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                      Isochrone Request                           ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Isochrone request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Isochrone response received:\n");
				
				PrintResponse(RESTClient.getResponse());
			}
			else
			{
				System.out.println("An error occurred.\n");
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
				JSONArray reachedTransitStops = j.getJSONArray("ReachedStops");
				
				for (int index = 0; index < reachedTransitStops.length(); index++ )
				{
					JSONObject transitStop = reachedTransitStops.getJSONObject(index);
					
					System.out.println();
					System.out.println(String.format("Position: %s", transitStop.get("Position")));
					System.out.println(String.format("Stop UID: %s", transitStop.get("StopUid")));
					System.out.println(String.format("Duration: %s", transitStop.get("Duration")));
				}
				
				System.out.println();
				System.out.println("Reachable Regions:");
				JSONArray reachableRegions = j.getJSONArray("ReachableRegions");
				
				for (int index = 0; index < reachableRegions.length(); index++ )
				{
					System.out.println();
					String polyline = reachableRegions.getString(index);
					System.out.println(String.format("Polyline: %s", polyline));
				}
			}
		} 
		catch (Exception e)
		{			
			System.out.println(String.format("Exception during extraction of json response: %s", e.getMessage()));
		}				
	}

	private String MakeRequest(String API_KEY) 
	{
		final String dataSet = "NewYork";
		
		// Starting point for the request
		final String point = "40.711484,-73.994";   // In New York
		
		// Define the window of time to look for possible journeys in.
		final int windowSizeMinutes = 20;
		
		// Length of journey travel time to consider.
		final int maxDuration = 20;
		
		return String.format
		(
				"/DataSets/%s/Isochrones?apiKey=%s&point=%s&windowStartTime=%s&windowSizeMinutes=%d&maxDurationMinutes=%d&returnStops=true&returnRegions=true&format=json",
				dataSet,
                API_KEY,
                point,
                Utils.GetCurrentDateTime(),
                windowSizeMinutes,
                maxDuration
        );
	}
}
