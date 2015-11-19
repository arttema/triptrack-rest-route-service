package Client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.net.*;
import java.io.*;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import Common.Utils;
import JJPModel.ServiceProvider;

public class AvailableReferenceDataExample {

	public void Run(String API_KEY) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                Available Reference Data Lookup Request                 ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY);
			
			System.out.println("Performing Available Reference Data request...\n");	
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Available Reference Data response received:\n");
				
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
		Map<String, String> referenceDataCategories = new HashMap<String, String>();
		referenceDataCategories.put("0", "TransitStops");
		referenceDataCategories.put("1", "Landmarks");
		referenceDataCategories.put("2", "ServiceProviders");
		referenceDataCategories.put("3", "TransportModes");
		referenceDataCategories.put("4", "Routes");
		referenceDataCategories.put("5", "RouteTimetableGroups");
		referenceDataCategories.put("6", "ParkAndRides");
		
		try 
		{
			JSONObject jResponse = new JSONObject(response);	
			
			if (Utils.CheckResponseStatus(jResponse))
			{			
				JSONArray jAvailableReferenceDataList = jResponse.getJSONArray("AvailableReferenceDataList");
				
				System.out.println("Reference Data retrieved for the following categories:");
				
				JSONObject jServiceProvidersAvailableReferenceData = null;
				for (int index = 0; index < jAvailableReferenceDataList.length(); index++ )
				{
					JSONObject jAvailableReferenceData = jAvailableReferenceDataList.getJSONObject(index);
					String category = referenceDataCategories.get(jAvailableReferenceData.getString("Category"));
					System.out.println(String.format("%2d: %s", index + 1, category));
					
					if (category == "ServiceProviders")
					{
						jServiceProvidersAvailableReferenceData = jAvailableReferenceData;
					}
				}

				if (jServiceProvidersAvailableReferenceData != null)
				{
					System.out.println("\nInformation available for category Service Providers:\n");
					
					System.out.println(String.format("XmlChecksum:     %s\n", jServiceProvidersAvailableReferenceData.getString("XmlChecksum")));
		            // The checksum can be stored, and compared with subsequent retrievals, to determine if the data has changed.
					System.out.println(String.format("XmlUnZippedUrl:  %s\n", jServiceProvidersAvailableReferenceData.getString("XmlUnzippedUrl")));
					System.out.println(String.format("XmlZippedUrl:    %s\n", jServiceProvidersAvailableReferenceData.getString("XmlZippedUrl")));
					System.out.println(String.format("JsonChecksum:    %s\n", jServiceProvidersAvailableReferenceData.getString("JsonChecksum")));
					System.out.println(String.format("JsonUnZippedUrl: %s\n", jServiceProvidersAvailableReferenceData.getString("JsonUnzippedUrl")));
					System.out.println(String.format("JsonZippedUrl:   %s\n", jServiceProvidersAvailableReferenceData.getString("JsonZippedUrl")));	
				
					// Download and deserialize the Service Providers - the other categories can be processed in a similar way.
					System.out.println("\nDownload and deserialization for Service Providers:\n");
					try 
					{
						URL url = new URL(jServiceProvidersAvailableReferenceData.getString("JsonZippedUrl"));
					    GZIPInputStream zipin = new GZIPInputStream(url.openStream());					    							
						String jsonTxt = new Scanner(zipin, "UTF-8").useDelimiter("\\A").next();
						JSONObject jServiceProviderReferenceData = new JSONObject(jsonTxt);
						
						Vector<ServiceProvider> serviceProviders = new Vector<ServiceProvider>();
						JSONArray serviceProvidersArray = jServiceProviderReferenceData.getJSONArray("ServiceProviderReferenceData");
						for (int i=0; i < serviceProvidersArray.length(); i++)
						{
							JSONObject jServiceProvider = serviceProvidersArray.getJSONObject(i);
							ServiceProvider serviceProvider = new ServiceProvider(
																					jServiceProvider.getString("ServiceProviderUid"), 
																					jServiceProvider.getString("Name"), 
																					jServiceProvider.getString("TimeZone"), 
																					jServiceProvider.getString("PhoneNumber"), 
																					jServiceProvider.getString("Url")
																				 );
							serviceProviders.add(serviceProvider);
						}

						System.out.println(String.format("Service Providers reference data has been downloaded and deserialized into %d Service Provider objects", serviceProviders.size()));

					} 
					catch (MalformedURLException e) 
					{
						e.printStackTrace();
					}
					catch (IOException e) 
					{
						e.printStackTrace();
					}
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
				"/DataSets/%s/AvailableReferenceData?ApiKey=%s&format=json",				
				"Perth",
				API_KEY		
		);
	}
}


