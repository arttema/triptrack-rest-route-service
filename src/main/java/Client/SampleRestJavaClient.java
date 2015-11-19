package Client;

public class SampleRestJavaClient 
{
	public static void main(String[] args)
    {
    	final String API_KEY = "";		// insert your API key here
    	
    	// Perform Example Reference Data Request
    	ReferenceDataExample rd = new ReferenceDataExample();
    	rd.Run(API_KEY);
    	
    	// Perform Example Journey (includes example Trip Request)
    	JourneyPlanExample jp = new JourneyPlanExample();
    	jp.Run(API_KEY);
    	
    	// Perform Example Stop Timetable Request
    	StopTimetableExample stopTimetable = new StopTimetableExample();
    	stopTimetable.Run(API_KEY);
    	
    	// Perform Example Nearby Transit Stops Request
    	NearbyTransitStopExample nearbyTransitStop = new NearbyTransitStopExample();
    	nearbyTransitStop.Run(API_KEY);  
    	
    	// Perform Example Stop Lookup Request
    	StopLookupExample stopLookupExample = new StopLookupExample();
    	stopLookupExample.Run(API_KEY);
    	
    	// Perform Example Landmark Lookup Request
    	LandmarkLookupExample landmarkLookupExample = new LandmarkLookupExample();
    	landmarkLookupExample.Run(API_KEY);
    	
    	// Perform Example Locality Lookup Request
    	LocalityLookupExample localityLookupExample = new LocalityLookupExample();
    	localityLookupExample.Run(API_KEY);
    	
    	// Perform Example Route Lookup Request
    	RouteLookupExample routeLookupExample = new RouteLookupExample();
    	routeLookupExample.Run(API_KEY);
    	
    	// Perform Example Route Map Request
    	RouteMapExample routeMapExample = new RouteMapExample();
    	routeMapExample.Run(API_KEY);
    	
    	// Perform Example Isochrone Request
    	IsochroneExample isochroneExample = new IsochroneExample();
    	isochroneExample.Run(API_KEY);
    	
    	// Perform Example Available Reference Data Lookup Request
    	AvailableReferenceDataExample availableReferenceDataExample = new AvailableReferenceDataExample();
    	availableReferenceDataExample.Run(API_KEY);
    	
    	TimetableExample timetableExample = new TimetableExample();
    	timetableExample.Run(API_KEY);
    }  
}
