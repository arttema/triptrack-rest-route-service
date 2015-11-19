package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;
import JJPModel.Route;

public class RouteLookupExample {

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                        Route Lookup Request                            ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Route lookup request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Route lookup response received:\n");
				
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
				
				for (int index = 0; index < routes.length(); index++ )
				{
					Route route = Route.FromJson(routes.getJSONObject(index));
					System.out.format("%2d: %s %s\n", index + 1, route.getCode(), route.getName());
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
		final String searchTerm = "103";
		
		return String.format
		(
				"/DataSets/%s/Routes?ApiKey=%s&SearchTerm=%s&format=json",				
				"Perth",
				API_KEY,
				searchTerm
		);
	}
	
	
	
	

}
