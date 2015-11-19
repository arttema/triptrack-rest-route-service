package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;
import JJPModel.GeoLocation;
import JJPModel.Journey;
import JJPModel.JourneyPlan;
import JJPModel.Leg;
import JJPModel.Location;
import JJPModel.TransferLeg;
import JJPModel.TransitStop;
import JJPModel.TripLeg;
import JJPModel.FareId;
import JJPModel.WalkLeg;


public class JourneyPlanExample 
{
	public void Run(String API_KEY)
	{		
		System.out.println();
        System.out.println("************************************************************************");
		System.out.println("                     Journey Planning Request                           ");
		System.out.println("************************************************************************");
		
		final String dataSet = "Perth";
		final String from = "Perth:10001";
		final String to = "Perth:10021";
		JJPModel.JourneyPlan jp = null;
		try 
		{
			String request = MakeRequest(API_KEY, dataSet, from, to);
			
			System.out.println("Performing Journey Plan...\n");			
			
			if (RESTClient.DoRESTQuery(request))
			{
				System.out.println("Journey Plan Response received:\n");				
				jp = ParseJourneyPlan(RESTClient.getResponse());
				if (jp != null)
				{		
					PrintJourneyPlan(jp);
				}
			}
		} 	
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
				   	
    	//Demonstrate use of Trip Request to show all stops on one of the returned trip legs
		if (jp != null)
		{
			TripExample trip = new TripExample();
	    	trip.Run(API_KEY, dataSet, jp);
		}
		else
		{
			System.out.println("Journey planning response does not exist so demonstration of trip request is not possible.");
		}
	}

	private void PrintJourneyPlan(JourneyPlan jp)
	{
		// Check whether there are journeys found or not.
        if (jp.getJourneys().isEmpty())
        {
        	System.out.println("No journeys found.");        	
        }
        
        // Display found journeys
        for (Journey jny : jp.getJourneys())
        {
        	String strOrigin = FormatLocation(jny.getOrigin());
        	String strDestination = FormatLocation(jny.getDestination());
        	
        	System.out.format(
        						"Journey %d: %s depart at %s -> %s arrive at %s  %d minutes\n", 
                    				jny.getId(),
                    				strOrigin,
                    				Utils.getSimpleDateTimeString(jny.getDepartTime()),
                    				strDestination, 
                    				Utils.getSimpleDateTimeString(jny.getArriveTime()),
                    				jny.getDuration()
                    		 );
        	
        	for (Leg leg : jny.getLegList())
        	{
        		if (leg instanceof TripLeg)
        		{
        			TripLeg tripLeg = (TripLeg)leg;

                    String serviceProviderName = "N/A";
                    if (tripLeg.getServiceProvider() != null)
                    {                        
                        serviceProviderName = String.format(" %s", tripLeg.getServiceProvider().getName());                        
                    }

                    System.out.println(
                        String.format("\tLeg %d: %s depart at %s  ->  %s arrive at %s  %d mins", 
                        		leg.getId(),
                        		FormatLocation(tripLeg.getOrigin()),
                        		Utils.getSimpleTimeString(tripLeg.getDepartTime()),       
                        		FormatLocation(tripLeg.getDestination()),
                        		Utils.getSimpleTimeString(tripLeg.getArriveTime()),
                            	tripLeg.getDuration()
                        ));
                    System.out.println(
                        String.format("\t\t%s %s  Route %s %s    Provider:  %s",
                        				tripLeg.getMode(),
                        				tripLeg.getHeadsign(),
                        				tripLeg.getRouteCode().length() == 0 ? "N/A" : tripLeg.getRouteCode(),
                        				tripLeg.getRouteName().length() == 0 ? "N/A" : tripLeg.getRouteName(),
                        				serviceProviderName
                        			));        			
        		}
        		else if (leg instanceof TransferLeg)
        		{
        			TransferLeg transferLeg = (TransferLeg)leg;
        			System.out.println(String.format("\tLeg %d: %s -> %s  %s  %d mins", 
        									leg.getId(),
        									FormatLocation(transferLeg.getOrigin()),      
        									FormatLocation(transferLeg.getDestination()), 
                                            transferLeg.getMode(),
                                            transferLeg.getDuration()
                                        ));
        		}
        		else if (leg instanceof WalkLeg)
        		{
        		    WalkLeg walkLeg = (WalkLeg)leg;
        		    System.out.println(String.format("\tLeg %d: %s -> %s  WALK %d metres  %d mins",
                                            leg.getId(),
                                            FormatLocation(walkLeg.getOrigin()),
                                            FormatLocation(walkLeg.getDestination()),
                                            walkLeg.getWalkDistance(),
                                            leg.getDuration()
                                        ));
        		}
        		else
        		{
        			System.out.println(String.format("Error: Unknown Leg :  %d", leg.getId()));        			
        		}
        		
        	}   	
        	
        	System.out.println();
        	
        	// display fare information
        	if (jny.getValidFareList() != null && jny.getValidFareList().size() > 0)
        	{
        		System.out.println("        Fares:");	
        		for (FareId validFare : jny.getValidFareList())
        		{
        			JJPModel.Fare fare = jp.getFare(validFare.getId());
        			
        			if (fare != null)
        			{         				
        				
        				String fareDescription = fare.getDescription() == null ? "No description" : fare.getDescription();        				
                        System.out.println(String.format("          [%7s %s] %25s (Identifier: %s) %s", 
                        															fare.getAmount(),
                        															fare.getCurrency() ,
                        															fareDescription,
                        															fare.getIdentifier(),
                        															validFare.applicableLegs())
                        								);
                        // Note: further information about a fare may also be included in its Attributes array.
        			}
        		}
        		System.out.println();
        	}
        	
        }		
	}

	private String MakeRequest(String API_KEY, String dataSet, String from, String to) 
	{		
		return String.format
		(
				"DataSets/%s/JourneyPlan?ApiKey=%s&from=%s&to=%s&date=%s&timeMode=LeaveAfter&returnFareData=true&routes=&format=json",
				dataSet,
				API_KEY,				
				from,
				to,				
				Utils.GetCurrentDateTime()
		);		
	}
	
	private JJPModel.JourneyPlan ParseJourneyPlan(String jsonJourneyPlan) throws Exception
	{
		JJPModel.JourneyPlan jp = new JJPModel.JourneyPlan();
		
		try 
		{
			JSONObject j = new JSONObject(jsonJourneyPlan);
			
			if (!Utils.CheckResponseStatus(j))
			{
				return null;
			}			
			
			JSONArray journeys = j.getJSONArray("Journeys");
			JSONArray serviceProviders = j.getJSONArray("ServiceProviderReferenceData");
			JSONArray locations = j.getJSONArray("Locations");
			jp.addLocationsFromJSONArray(locations);
			jp.addServiceProvidersFromJSONArray(serviceProviders);
			jp.addJourneysFromJSONArray(journeys);
			
			if (j.has("Fares"))
			{
				JSONArray fares = j.getJSONArray("Fares");
				jp.addFaresFromJSONArray(fares);
			}
			
			return jp;			
		} 
		catch (Exception e)
		{			
			throw e;
		}		
	}	
	
	private String FormatLocation(Location location)
    {            
        if (location instanceof TransitStop)
        {
        	TransitStop transitStop = (TransitStop)location;

            if (transitStop.getStopCode().length() == 0)
            {
                return transitStop.getDescription();
            }

            return String.format("%s (%s)", transitStop.getDescription(), transitStop.getStopCode());
        }
        else if (location instanceof GeoLocation)
        {
            GeoLocation geoLocation = (GeoLocation)location;
            return geoLocation.getPosition().toString();
        }

        return "";        
    }

}
