package JJPModel;

import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public final class TimetableTrip {
	private String m_tripUid;
	private String m_headsign;
	private Route m_route;
	private String m_runningDatePattern;
	private Vector<TransitStop> m_stopPattern;
	private Vector<TripStopTiming> m_tripStopTimings;
	
	
	public static TimetableTrip FromJson(
		JSONObject json, 
		HashMap<String, Route> routesByUid,
		HashMap<Integer, Vector<TransitStop>> stopPatternsById,
		HashMap<Integer, String> runningDatePatternsById
	) throws Exception {
		TimetableTrip tt = new TimetableTrip();
		
		tt.m_tripUid = json.getString("TripUid");
		tt.m_headsign = json.optString("Headsign");
		tt.m_route = routesByUid.get(json.getString("RouteUid"));
		tt.m_runningDatePattern = runningDatePatternsById.get(json.getInt("RunningDatePatternId"));
		tt.m_stopPattern = stopPatternsById.get(json.getInt("StopPatternId"));
		
		tt.m_tripStopTimings = new Vector<TripStopTiming>();
		JSONArray jsonTripStopTimings = json.getJSONArray("TripStopTimings");
		for (int i=0; i < jsonTripStopTimings.length(); i++) {
			TripStopTiming tst = TripStopTiming.FromJson(jsonTripStopTimings.getJSONObject(i));
			tt.m_tripStopTimings.add(tst);
		}
		
		return tt;
	}

	public String getTripUid() {
		return m_tripUid;
	}

	public String getHeadsign() {
		return m_headsign;
	}

	public Route getRoute() {
		return m_route;
	}

	public String getRunningDatePattern() {
		return m_runningDatePattern;
	}

	public Vector<TransitStop> getStopPattern() {
		return m_stopPattern;
	}
	
	public Vector<TripStopTiming> getTripStopTimings() {
		return m_tripStopTimings;
	}
}
