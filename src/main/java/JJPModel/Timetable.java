package JJPModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import Common.Utils;

public final class Timetable {
	private Date m_startDate;
	private Date m_endDate;
	private Vector<TimetableTrip> m_timetableTrips;
	
	public static Timetable FromJson(JSONObject jsonResponse) throws Exception 
	{
		Timetable tt = new Timetable();
		
		tt.m_startDate = Utils.getDateFromSimpleString(jsonResponse.getString("StartDate"));
		tt.m_endDate = Utils.getDateFromSimpleString(jsonResponse.getString("EndDate"));

		JSONArray jsonTransitStops = jsonResponse.getJSONArray("TransitStops");
		HashMap<String, TransitStop> transitStopsByUid = makeTransitStops(jsonTransitStops);
		
		JSONArray jsonRoutes = jsonResponse.getJSONArray("Routes");
		HashMap<String, Route> routesByUid = makeRoutes(jsonRoutes);
		
		JSONArray jsonStopPatterns = jsonResponse.getJSONArray("StopPatterns");
		HashMap<Integer, Vector<TransitStop>> stopPatternsById = makeStopPatterns(jsonStopPatterns, transitStopsByUid);
		
		JSONArray jsonRunningDatePatterns = jsonResponse.getJSONArray("RunningDatePatterns");
		HashMap<Integer, String> runningDatePatternsById = makeRunningDatePatterns(jsonRunningDatePatterns);
		
		JSONArray jsonTimetableTrips = jsonResponse.getJSONArray("TimetableTrips");
		tt.setTimetableTrips(jsonTimetableTrips, routesByUid, stopPatternsById, runningDatePatternsById);
		
		return tt;
	}
	
	public Vector<TimetableTrip> getTimetableTrips() {
		return m_timetableTrips;
	}

	public Date getStartDate() {
		return m_startDate;
	}

	public Date getEndDate() {
		return m_endDate;
	}
	
	private static HashMap<String, TransitStop> makeTransitStops(JSONArray transitStops) throws Exception {
		HashMap<String, TransitStop> result = new HashMap<String, TransitStop>();
		for (int i=0; i < transitStops.length(); i++) {
			TransitStop transitStop = TransitStop.FromJson(transitStops.getJSONObject(i));
			result.put(transitStop.getStopUid(), transitStop);
		}
		return result;
	}
	
	private static HashMap<Integer, Vector<TransitStop>> makeStopPatterns(JSONArray jsonStopPatterns, HashMap<String, TransitStop> transitStopsByUid) throws Exception {
		HashMap<Integer, Vector<TransitStop>> result = new HashMap<Integer, Vector<TransitStop>>();
		for (int i=0; i < jsonStopPatterns.length(); i++) {
			JSONObject jsonStopPattern = jsonStopPatterns.getJSONObject(i);
			int id = jsonStopPattern.getInt("StopPatternId");
			
			Vector<TransitStop> transitStops = new Vector<TransitStop>();
			JSONArray stopUids = jsonStopPattern.getJSONArray("TransitStopUids");
			for (int j=0; j < stopUids.length(); j++) {
				String stopUid = stopUids.getString(j);
				transitStops.add(transitStopsByUid.get(stopUid));
			}
			
			result.put(id, transitStops);
		}
		
		return result;
	}
	
	private static HashMap<String, Route> makeRoutes(JSONArray jsonRoutes) throws Exception {
		HashMap<String, Route> result = new HashMap<String, Route>();
		for (int i=0; i < jsonRoutes.length(); i++) {
			Route route = Route.FromJson(jsonRoutes.getJSONObject(i));
			result.put(route.getRouteUid(), route);
		}
		return result;
	}
	
	private static HashMap<Integer, String> makeRunningDatePatterns(JSONArray jsonRunningDatePatterns) throws Exception {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		for (int i=0; i < jsonRunningDatePatterns.length(); i++) {
			JSONObject rdp = jsonRunningDatePatterns.getJSONObject(i);
			int id = rdp.getInt("RunningDatePatternId");
			String pattern = rdp.getString("Pattern");
			
			result.put(id, pattern);
		}
		return result;
	}

	
	private void setTimetableTrips(
		JSONArray jsonTimetableTrips,
		HashMap<String, Route> routesByUid,
		HashMap<Integer, Vector<TransitStop>> stopPatternsById,
		HashMap<Integer, String> runningDatePatternsById
	) throws Exception 
	{
		m_timetableTrips = new Vector<TimetableTrip>();
		
		for(int i = 0; i < jsonTimetableTrips.length(); i++)
		{
			//Get details from JSON object.
			JSONObject tt = jsonTimetableTrips.getJSONObject(i);
			m_timetableTrips.add(TimetableTrip.FromJson(tt, routesByUid, stopPatternsById, runningDatePatternsById));
		}
	}	
}
