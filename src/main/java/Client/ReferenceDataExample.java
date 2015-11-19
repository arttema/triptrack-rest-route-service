package Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Common.Utils;

public class ReferenceDataExample {
	
	public void Run(String API_KEY)
	{		
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("              Reference Data Request (TransitStops, Routes)             ");
		System.out.println("************************************************************************");
		
		final String dataSet = "Perth";
		
		try 
		{			
			/* 
			 *  At the time of writing this sample code there were 6 reference data categories
			 *	(TransitStops, Landmarks, ServiceProviders, TransportModes, Routes, RouteTimetableGroups)
			 *	Enable the following line to get the current list of available reference data categories */
			//PrintReferenceDataCategories(API_KEY, dataSet);
			
			// The following code shows getting reference data for the TransitStops, Landmarks and Routes categories			
			System.out.println("Performing Reference Data Request (TransitStops)...\n");			
			String transitStopsRequest = MakeRequest(API_KEY, dataSet, "TransitStops");
			if (RESTClient.DoRESTQuery(transitStopsRequest))
			{
				System.out.println("Transit Stops Reference Data response received:\n");				
								
				JSONObject j = new JSONObject(RESTClient.getResponse());				
				if (Utils.CheckResponseStatus(j))
				{
					JSONArray transitStops = j.getJSONArray("TransitStopReferenceData");				
					System.out.println(String.format("Number of Stops in Response: %d\n", transitStops.length()));				
				}				
			}
			
			System.out.println("Performing Reference Data Request (Landmarks)...\n");			
			String landmarksRequest = MakeRequest(API_KEY, dataSet, "Landmarks");
			if (RESTClient.DoRESTQuery(landmarksRequest))
			{
				System.out.println("Landmarks Reference Data response received:\n");				
								
				JSONObject j = new JSONObject(RESTClient.getResponse());				
				if (Utils.CheckResponseStatus(j))
				{
					JSONArray landmarks = j.getJSONArray("LandmarkReferenceData");				
					System.out.println(String.format("Number of Landmarks in Response: %d\n", landmarks.length()));				
				}				
			}
			
			System.out.println("Performing Reference Data Request (Routes)...\n");			
			String routesRequest = MakeRequest(API_KEY, dataSet, "Routes");
			if (RESTClient.DoRESTQuery(routesRequest))
			{
				System.out.println("Routes Reference Data response received:\n");				
								
				JSONObject j = new JSONObject(RESTClient.getResponse());				
				if (Utils.CheckResponseStatus(j))
				{			
					JSONArray routes = j.getJSONArray("RouteReferenceData");					
					System.out.println(String.format("Number of Routes in Response: %d\n", routes.length()));
				}				
			}
		} 	
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}		   	
    	
	}

	private void PrintReferenceDataCategories(String API_KEY, final String dataSet) throws JSONException 
	{
		// Find available reference data categories	 
		if (RESTClient.DoRESTQuery(String.format("DataSets/%s/ReferenceData?ApiKey=%s&format=json", dataSet, API_KEY)))
		{
			JSONObject j = new JSONObject(RESTClient.getResponse());			
			System.out.println("List of reference data categories:");
			JSONArray referenceDataCategories = j.getJSONArray("ReferenceDataList");
			for(int index = 0; index < referenceDataCategories.length(); index++)
			{
				System.out.println(String.format("%d %s", index + 1, referenceDataCategories.getString(index)));
			}									
				
		}
	}
	
	private String MakeRequest(String API_KEY, String dataSet, String referenceDataCategory) 
	{		
		return String.format
		(
				"DataSets/%s/ReferenceData/%s?ApiKey=%s&format=json",
				dataSet,
				referenceDataCategory,
				API_KEY
		);		
	}
	
	
	
	
}
