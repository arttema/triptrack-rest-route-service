package Client;


import org.json.JSONArray;
import org.json.JSONObject;
import Common.Utils;
import JJPModel.Trip;
import JJPModel.TripStop;
import JJPModel.TripSummary;

public class TripExample {

	public void Run(String API_KEY, String dataSet, JJPModel.JourneyPlan journeyPlan) 
	{
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                              Trip Request                              ");
		System.out.println("************************************************************************");
					
		try 
		{
			String request = MakeRequest(API_KEY, dataSet, journeyPlan);
			
			if (request != null)
			{
				System.out.println("Performing Trip request...\n");	
				
				if (RESTClient.DoRESTQuery(request))
				{
					System.out.println("Trip Response received:\n");
					
					Trip t = ParseTrip(RESTClient.getResponse());
					
					if (t != null)
					{		
						PrintTrip(t);
					}
				}
			}
		}	
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());			
		}		
		
	}
	
	private String MakeRequest(String API_KEY, String dataSet, JJPModel.JourneyPlan journeyPlan) 
	{			
		JJPModel.TripLeg tripLeg = FindFirstTripLeg(journeyPlan);
		if (tripLeg == null)
        {
			System.out.println("Note: this trip request uses trip details from the previous journey plan.");
			System.out.println("At the time of execution, there seem to be no trips in that response");
			System.out.println("(probably because the timetables in Perth have changed).");
			System.out.println("If this happens to you, try modifying the journey planning request,");
			System.out.println("until you find a response that includes a TripLeg.");
            return null;
        }
		return String.format
		(
				"DataSets/%s/Trip?ApiKey=%s&TripUid=%s&FromStop=%s&ToStop=%s&TripDate=%s&TransactionId=&format=json",
				dataSet,
				API_KEY,				
				tripLeg.getTripUid(),
				tripLeg.getOriginStopSequenceNumber(),
				tripLeg.getDestinationStopSequenceNumber(),
				tripLeg.getTripStartTime()
		);
	}
	
	private JJPModel.TripLeg FindFirstTripLeg(JJPModel.JourneyPlan journeyPlan)
    {		
        // Since we are querying live data here in the demo program, rather than hard-coding trip details that
        // might change, we will pull trip details from a journey planning response we've just received.  
        // All we have to do is find a TripLeg somewhere in the response, so we can demonstrate the TripRequest.  
        // In a real-world application you might want to do Trip Requests for all TripLegs in a Journey Planning Response,
        // or for a leg that your end-user clicked on.  

        for (JJPModel.Journey journey : journeyPlan.getJourneys())
        {
            for (JJPModel.Leg leg : journey.getLegList())
            {
            	if (leg instanceof JJPModel.TripLeg)
            	{
            		return (JJPModel.TripLeg) leg;
            	}              
            }
        }

        return null;
    }
	
	private void PrintTrip(Trip trip) 
	{
		System.out.println("Trip Details:");
        System.out.println(String.format("TripUid: %s", trip.getSummary().getTripUid()));

        TripSummary tripSummary = trip.getSummary();

        System.out.println(String.format("Trip Code:%s", tripSummary.getTripCode()));
        System.out.println(String.format("Headsign:%s", tripSummary.getHeadsign()));
        System.out.println(String.format("Mode:%s", tripSummary.getMode()));
        System.out.println(String.format("Route Code:%s", tripSummary.getRouteCode()));
        System.out.println(String.format("Route Name:%s", tripSummary.getRouteName()));
        System.out.println(String.format("Service Provider:%s", tripSummary.getServiceProvider().getName()));

        System.out.println("Trip Stops:");
       
        if (trip.getStops() == null || trip.getStops().isEmpty())
        {
            System.out.println("No trip stops found.\n");
            return;
        }

        for (TripStop tripStop : trip.getStops())
        {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(String.format("Trip Stop Sequence: %s", tripStop.getSequenceNumber()));
            System.out.println(String.format("Trip Stop: %s   (%s, %s)", tripStop.getTransitStop().getDescription(), tripStop.getTransitStop().getStopUid(), tripStop.getTransitStop().getStopCode()));
            System.out.println(String.format("Position: %s", tripStop.getTransitStop().getPosition()));
            System.out.println(String.format("Arriving: %s Departing: %s", Utils.getSimpleDateTimeString(tripStop.getArriveTime()), Utils.getSimpleDateTimeString(tripStop.getDepartTime())));
        }
		
	}
	
	private Trip ParseTrip(String jsonTrip) throws Exception
	{
		Trip t = new Trip();
		
		try 
		{
			JSONObject j = new JSONObject(jsonTrip);	
			
			if (!Utils.CheckResponseStatus(j))
			{
				return null;
			}
			
			JSONArray stops = j.getJSONArray("TripStops");
			JSONObject jsonSummary = j.getJSONObject("Summary");
			
			t.setSummary(jsonSummary);
			t.addStopsFromJSONArray(stops);
					
			return t;			
		} 
		catch (Exception e)
		{			
			throw e;
		}		
	}
}
