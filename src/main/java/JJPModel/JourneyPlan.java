package JJPModel;

import java.util.Date;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public class JourneyPlan 
{
	private Vector<Journey> m_journeys;
	private HashMap<String, ServiceProvider> m_serviceProviderMap;
	private HashMap<String, Location> m_allLocationMap;
	private HashMap<String, TransitStop> m_transitLocations;
	private HashMap<String, GeoLocation> m_geoLocations;
	private HashMap<Integer, Fare> m_fares; 
	
	// JSON type string definitions: 
	public static final String WALK_LEG = "WalkLeg:http://www.jeppesen.com/journeyplanner";
	public static final String TRANSFER_LEG = "TransferLeg:http://www.jeppesen.com/journeyplanner";
	public static final String TRIP_LEG = "TripLeg:http://www.jeppesen.com/journeyplanner";
	public static final String TRANSIT_LOCATION = "TransitStop:http://www.jeppesen.com/journeyplanner";
	public static final String GEO_LOCATION = "GeoLocation:http://www.jeppesen.com/journeyplanner";
	
	public JourneyPlan()
	{
		m_serviceProviderMap = new HashMap<String, ServiceProvider>();
		m_allLocationMap = new HashMap<String, Location>();
		m_transitLocations = new HashMap<String, TransitStop>();
		m_geoLocations = new HashMap<String, GeoLocation>();
		m_journeys = new Vector<Journey>();
		m_fares = new HashMap<Integer, Fare>();
	}
	
	public String toString()
	{
		String returnString = "Journey Count: " + this.m_journeys.size();
		return returnString;	
	}
	
	public Location getLocationById(String id)
	{
		if (m_allLocationMap.containsKey(id))
		{
			return (Location)m_allLocationMap.get(id);
		}
		else
		{
			return null;
		}
	}
	
	public TransitStop getTransitLocation(String locUid)
	{
		if (m_transitLocations.containsKey(locUid))
		{
			return (TransitStop)m_transitLocations.get(locUid);
		}
		else
		{
			return null;
		}
	}
	
	public GeoLocation getGeoLocationById(String id)
	{
		if (m_geoLocations.containsKey(id))
		{
			return (GeoLocation)m_geoLocations.get(id);
		}
		else
		{
			return null;
		}
	}
	
	public Boolean hasServiceProvider(String id)
	{
		return m_serviceProviderMap.containsKey(id);
	}
	
	public ServiceProvider getServiceProvider(String id)
	{
		if(hasServiceProvider(id))
		{
			return m_serviceProviderMap.get(id);
		}
		else
		{
			return null;
		}
	}
	
	public void addServiceProvider(ServiceProvider sp)
	{
		if (!hasServiceProvider(sp.getId()))
		{
			m_serviceProviderMap.put(sp.getId(), sp);
		}
	}
	
	private void addFare(Fare fare)
	{
		m_fares.put(fare.getId(), fare);
	}
	
	public Fare getFare(int id)
	{
		return m_fares.get(new Integer(id));
	}
	
	public void addLocation(Location loc, String id)
	{
		if (!m_allLocationMap.containsKey(id))
		{
			m_allLocationMap.put(id, loc);
		}

		if (loc instanceof TransitStop)
		{
			if (!m_transitLocations.containsKey(id))
			{
				m_transitLocations.put(id, (TransitStop)loc);
			}
		}
		else if (loc instanceof GeoLocation)
		{
			if (!m_geoLocations.containsKey(id))
			{
				m_geoLocations.put(id, (GeoLocation)loc);
			}
		}			
	}
	
	public void addFaresFromJSONArray(JSONArray fares) throws Exception	
	{
		try
		{
			for(int i = 0; i < fares.length(); i++)
			{
				//Get details from JSON object.
				JSONObject jsonfare = fares.getJSONObject(i);
				
				Fare fare = new Fare();				
				fare.setId(jsonfare.getInt("Id"));
				fare.setIdentifier(jsonfare.getString("Identifier"));
				
				if(jsonfare.has("Description"))
				{
					fare.setDescription(jsonfare.getString("Description"));
				}
				
				fare.setAmount(jsonfare.getJSONObject("Price").getString("Amount"));
				fare.setCurrency(jsonfare.getJSONObject("Price").getString("Currency"));
				
				this.addFare(fare);				
			} 
		}
		catch(Exception e)
		{
			throw new Exception("Exception in addFaresFromJSONArray: " + e.toString());
		}
		
	}
	
	public void setJourneys(Vector<Journey> journeys)
	{
		m_journeys = journeys;
	}
	
	public void addJourney(Journey journey)
	{
		m_journeys.add(journey);
	}
	
	public Vector<Journey> getJourneys()
	{
		return m_journeys;
	}
	
	// Parses a JSON array of journeys into journey objects. 
	public void addJourneysFromJSONArray(JSONArray journeys) throws Exception
	{
		try
		{
			for(int i = 0; i < journeys.length(); i++)
			{
				//Get details from JSON object.
				JSONObject journey = journeys.getJSONObject(i);
				int id = journey.getInt("Id");
				
				String departString = journey.getString("DepartTime");
				String arriveString = journey.getString("ArriveTime");
				
				Date departDateTime = Utils.getDateFromString(departString);
				Date arriveDateTime = Utils.getDateFromString(arriveString);
								
				String origin = journey.getString("OriginLocationId");
				Location originLoc = this.getLocationById(origin);
				String dest = journey.getString("DestinationLocationId");
				Location destLoc = this.getLocationById(dest);
				
				int duration = journey.getInt("DurationMinutes");
				
				// Create new journey
				Journey jny = new Journey(id, originLoc, destLoc, departDateTime, arriveDateTime, duration);
				
				// Add legs to journey
				JSONArray legs = journey.getJSONArray("Legs");
				addLegsToJourneyFromJSONArray(legs, jny);
				
				// Add valid fares to journey
				if (journey.has("ValidFares"))
				{
					JSONArray fares = journey.getJSONArray("ValidFares");
					addValidFaresToJourneyFromJSONArray(fares, jny);
				}
				
				this.addJourney(jny);
				
			} 
		}
		catch (Exception e)
		{
			throw new Exception("Exception in addJourneysFromJSONArray: " + e.toString());			
		}
		
	}
	
	private void addValidFaresToJourneyFromJSONArray(JSONArray validFares, Journey journey) throws Exception
	{
		try
		{
			for(int i = 0; i < validFares.length(); i++)
			{				
				JSONObject jsonValidFare = validFares.getJSONObject(i);
				FareId validFareId = new FareId(jsonValidFare.getInt("Id"));
				
				if (jsonValidFare.has("SubJourney"))
				{
					JSONObject jsonSubJourney = jsonValidFare.getJSONObject("SubJourney");
					validFareId.setStartLeg(jsonSubJourney.getInt("FirstLeg"));
					validFareId.setEndLeg(jsonSubJourney.getInt("LastLeg"));
				}
				
				journey.addValidFare(validFareId);		
			} 
		}
		catch (Exception e)
		{			
			throw new Exception("Exception in addValidFaresToJourneyFromJSONArray: " + e.toString());
		}	
		
	}

	public void addLegsToJourneyFromJSONArray(JSONArray legs, Journey journey) throws Exception
	{
		try
		{
			for(int i = 0; i < legs.length(); i++)
			{
				//Get base details from JSON object.
				JSONObject leg = legs.getJSONObject(i);
				String legType = leg.getString("__type");
				int id = leg.getInt("Id");
				Location origin = this.getLocationById(leg.getString("OriginLocationId"));
				Location dest = this.getLocationById(leg.getString("DestinationLocationId"));
				int duration = leg.getInt("DurationMinutes");
				String polyline = leg.optString("Polyline");
				
				if (legType.compareToIgnoreCase(WALK_LEG) == 0)
				{
					int walkDistance = leg.getInt("WalkDistanceMetres");
					WalkLeg walkLeg = new WalkLeg(id, origin, dest, polyline, duration, walkDistance);
					journey.addLegToList(walkLeg);
				} 
				else if (legType.compareToIgnoreCase(TRANSFER_LEG) == 0)
				{
					String mode = leg.getString("Mode");
					TransferLeg transferLeg = new TransferLeg(id, origin, dest, polyline, duration, mode);
					journey.addLegToList(transferLeg);
				}
				else if (legType.compareToIgnoreCase(TRIP_LEG) == 0)
				{
					Date departTime = Utils.getDateFromString(leg.getString("DepartTime")); 
					Date arriveTime = Utils.getDateFromString(leg.getString("ArriveTime"));
					String mode = leg.getString("Mode");
					String tripUid = leg.optString("TripUid");
					String tripCode = leg.optString("TripCode");
					String routeName = leg.optString("RouteName");
					String routeCode = leg.optString("RouteCode");
					ServiceProvider sp = this.getServiceProvider(leg.getString("ServiceProviderUid"));
					String headsign = leg.optString("Headsign");
					
					TripLeg tripLeg = new TripLeg(id, origin, dest, polyline, duration, 
							departTime, arriveTime, mode, tripUid, tripCode, routeName, routeCode,
							sp, headsign);
					
					tripLeg.setOriginStopSequenceNumber(leg.getString("OriginStopSequenceNumber"));
					tripLeg.setDestinationStopSequenceNumber(leg.getString("DestinationStopSequenceNumber"));
					
					if (leg.has("TripStartTime"))
					{				
						tripLeg.setTripStartTime(leg.getString("TripStartTime"));
					}
					
					journey.addLegToList(tripLeg);										
				}
				else
				{
					throw new Exception("Unexpected Leg Type String: " + legType);
				}
				
						
			} 
		}
		catch (Exception e)
		{			
			throw new Exception("Exception in addLegsToJourneyFromJSONArray: " + e.toString());
		}		
	}
	
	public void addServiceProvidersFromJSONArray(JSONArray serviceProviders) throws Exception
	{
		try
		{
			for(int i = 0; i < serviceProviders.length(); i++)
			{
				
				JSONObject sp = serviceProviders.getJSONObject(i); 
				String id = sp.getString("ServiceProviderUid");
				String name = sp.optString("Name");
				String timezone = sp.optString("TimeZone");
				String phoneNumber = sp.optString("PhoneNumber");
				String url = sp.optString("Url");
				
				ServiceProvider serviceProvider = new ServiceProvider(id, name, timezone, phoneNumber, url);
				
				this.addServiceProvider(serviceProvider);
								
			}
		} 
		catch (Exception e)
		{			
			throw new Exception("Add Service Provider Unexpected Exception: " + e.toString());
		}
	}
	
	public void addLocationsFromJSONArray(JSONArray locations) throws Exception
	{
		try
		{
			for(int i = 0; i < locations.length(); i++)
			{
				// Get Base Location properties. 
				String id = locations.getJSONObject(i).getString("Id");
				JSONObject loc = locations.getJSONObject(i).getJSONObject("Location");
				addLocation(makeLocation(loc), id);
			} 
		}
		catch (Exception e)
		{
			throw new Exception("Exception in addLocationsFromJSONArray: " + e.toString());
		}
	}

	static Location makeLocation(JSONObject loc) throws Exception {
		String locType = loc.getString("__type");
		
		if (locType.compareTo(TRANSIT_LOCATION) == 0) {
			return TransitStop.FromJson(loc);
		} else if (locType.compareTo(GEO_LOCATION) == 0) {
			return GeoLocation.FromJson(loc);
		} else {
			throw new Exception("Unexpected Location Type String: " + locType);
		}
	}
}
