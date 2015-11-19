package JJPModel;

import org.json.JSONObject;

public final class TripStopTiming {
	private String m_arrivalTime;
	private String m_departureTime;
	private boolean m_canBoard;
	private boolean m_canAlight;
	
	public static TripStopTiming FromJson(JSONObject json) throws Exception {
		TripStopTiming tst = new TripStopTiming();
		
		tst.m_arrivalTime = json.optString("ArrivalTime");
		tst.m_departureTime = json.optString("DepartTime");
		tst.m_canBoard = json.getBoolean("CanBoard");
		tst.m_canAlight = json.getBoolean("CanAlight");
		
		return tst;
	}

	public String getArrivalTime() {
		return m_arrivalTime;
	}

	public String getDepartureTime() {
		return m_departureTime;
	}

	public boolean canBoard() {
		return m_canBoard;
	}

	public boolean canAlight() {
		return m_canAlight;
	}
}
