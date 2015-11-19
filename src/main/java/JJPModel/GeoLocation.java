package JJPModel;

import org.json.JSONObject;

public class GeoLocation  extends Location 
{
	public GeoLocation
	(
		String dataSet,
		GeoPosition position,		
		String description
	)
	{
		super(dataSet, position, description);
	}
	
	public static GeoLocation FromJson(JSONObject loc) throws Exception {
		String dataSet = loc.getString("DataSet");
		String description = loc.optString("Description");
		String positionString = loc.getString("Position");
		GeoPosition pos = new GeoPosition(positionString);
		
		return new GeoLocation(dataSet, pos, description);
	}
}
