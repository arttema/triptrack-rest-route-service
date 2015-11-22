package JJPModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONObject;

public class TransitStop extends Location
{
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private String m_stopCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private String m_stopUid;		
	
	public String getStopCode()
	{
		return m_stopCode;
	}
	
	public void setStopCode(String code)
	{
		m_stopCode = code;
	}
	
	public String getStopUid()
	{
		return m_stopUid;
	}
	
	public void setStopUid(String uid)
	{
		m_stopUid = uid;
	}
	
	public TransitStop
	(
		String dataSet,
		String description,
		GeoPosition position,
		String stopCode,
		String stopUid
	) 
	{
		super(dataSet, position, description);
		this.m_stopCode = stopCode;
		this.m_stopUid = stopUid;
	}
	
	public static TransitStop FromJson(JSONObject loc) throws Exception {
		String dataSet = loc.getString("DataSet");
		String description = loc.optString("Description");
		String stopUid = loc.optString("StopUid");
		String stopCode = loc.optString("Code");
		String positionString = loc.getString("Position");
		GeoPosition pos = new GeoPosition(positionString);
		
		return new TransitStop(dataSet, description, pos, stopCode, stopUid);
	}
}
