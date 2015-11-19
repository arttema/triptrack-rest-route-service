package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public class NearbyTransitStopExample 
{

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                   Nearby Transit Stops Request                         ");
		System.out.println("************************************************************************");	
		
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Nearby Transit Stops request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Nearby Transit Stops response received:\n");
				
				PrintNearbyTransitStops(RESTClient.getResponse());
			}
		}	
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());			
		}		
	}

	private String MakeRequest(String API_KEY) 
	{		
		return String.format
		(
				"DataSets/%s/NearbyTransitStops?ApiKey=%s&GeoCoordinate=%s&MaximumDistanceInMetres=&WalkSpeed=&MaximumStopsToReturn=&ReturnPolylineInformation=False&format=json",
				"Perth",
				API_KEY,				
				"-31.83,115.85"
		);
	}
	
	private void PrintNearbyTransitStops(String jsonResponse) throws Exception 
	{		
		JSONObject j = new JSONObject(jsonResponse);	
		
		if (Utils.CheckResponseStatus(j))
		{		
			System.out.println("Nearby Transit Stops:");
			JSONArray jsonStopPaths = j.getJSONArray("TransitStopPaths");
	        if (jsonStopPaths == null || jsonStopPaths.length() == 0)
	        {
	        	System.out.println("No Nearby Transit Stops found.\n");
	            return;        
	        }
	
	        for (int i=0; i < jsonStopPaths.length(); i++)
	        {
	        	JSONObject jsonStopPath = jsonStopPaths.getJSONObject(i);
	        	JSONObject jsonTransitStop = jsonStopPath.getJSONObject("TransitStop");
	        	System.out.println();
	        	System.out.println(String.format("%s (%s)", jsonTransitStop.getString("Code"), jsonTransitStop.getString("StopUid")));
	        	System.out.println(String.format("%s (%s)", jsonTransitStop.getString("Description"), jsonTransitStop.getString("Position")));
	        	System.out.println(String.format("Distance: %dm Time to walk: %dmin", Integer.parseInt(jsonStopPath.getString("Duration")), Integer.parseInt(jsonStopPath.getString("Distance"))));
	        }
		}
	}

}
