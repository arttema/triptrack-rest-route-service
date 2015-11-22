package JJPModel;

import com.fasterxml.jackson.annotation.JsonInclude;

// For historical reasons, the JJP BaseLocation does not include Position and Description,
// but these attributes do appear on all types of location (TransitStop, GeoLocation, Landmark),
// so we'll include them here on our own Location base class.   
public abstract class Location 
{
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private String m_dataSet;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private GeoPosition m_position;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private String m_description;

	public String getDescription() 
	{
		return m_description;
	}
	
	public void setDescription(String desc) 
	{
		this.m_description = desc;
	}
	
	public String getDataSet() 
	{
		return m_dataSet;
	}
	
	public void setDataSet(String dataSet) 
	{
		this.m_dataSet = dataSet;
	}
	
	public GeoPosition getPosition() 
	{
		return m_position;
	}

	public void setPosition(GeoPosition position) 
	{
		this.m_position = position;
	}
	
	public Location(String dataSet, GeoPosition position, String desc) 
	{
		this.m_dataSet = dataSet;
		this.m_position = position;
		this.m_description = desc;
	}
}
