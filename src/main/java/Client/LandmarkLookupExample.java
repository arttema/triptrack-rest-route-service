package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public class LandmarkLookupExample {

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                      Landmark Lookup Request                           ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Landmark lookup request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Landmark lookup response received:\n");
				
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
				JSONArray landmarks = j.getJSONArray("Landmarks");
				
				for (int index = 0; index < landmarks.length(); index++ )
				{
					System.out.println(String.format("%2d: %s", index + 1, landmarks.getJSONObject(index).get("Description")));
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
		return String.format
		(
				"/DataSets/%s/Landmarks?ApiKey=%s&SearchTerm=%s&format=json",				
				"Perth",
				API_KEY,				
				"end"				
		);
	}
	
	
	
	

}
