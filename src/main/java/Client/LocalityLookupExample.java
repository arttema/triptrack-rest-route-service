package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;
import JJPModel.GeoPosition;
import JJPModel.Landmark;
import JJPModel.TransitStop;

public class LocalityLookupExample {

	public static final String TRANSIT_LOCATION = "TransitStop:http://www.jeppesen.com/journeyplanner";
	public static final String LANDMARK = "Landmark:http://www.jeppesen.com/journeyplanner";
	
	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                      Locality Lookup Request                           ");
		System.out.println("************************************************************************");
		
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Locality lookup request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Locality lookup response received:\n");
				
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
				JSONArray localities = j.getJSONArray("Localities");
				
				for (int index = 0; index < localities.length(); index++ )
				{
					JSONObject locality = localities.getJSONObject(index);
					String localityType = locality.getString("__type");
					GeoPosition geoPosition = new GeoPosition(locality.getString("Position"));
					
					if (localityType.compareTo(TRANSIT_LOCATION) == 0)
					{
						TransitStop location = new TransitStop(
																			locality.getString("DataSet"), 
																			locality.getString("Description"), 
																			geoPosition, 
																			locality.getString("Code"),
																			locality.getString("StopUid")
																	  );
						System.out.println(String.format("%2d: %11s:  %s", index + 1, "TransitStop", location.getDescription()));
					}
					else if (localityType.compareTo(LANDMARK) == 0)
					{
						Landmark landmark = new Landmark(
															locality.getString("DataSet"), 
															geoPosition, 
															locality.getString("Description"), 
															locality.getString("LandmarkUid"), 
															locality.getString("TypeId"),
															locality.getString("TypeDescription")
														);
						System.out.println(String.format("%2d: %11s:  %s", index + 1, "Landmark", landmark.getDescription()));
					}
					// no other Locality types implemented at this stage
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
				"/DataSets/%s/Localities?ApiKey=%s&SearchTerm=%s&format=json",				
				"Perth",
				API_KEY,				
				"end"				
		);
	}
}
