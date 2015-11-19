package JJPModel;

public class Landmark extends Location
{
	private String m_landmarkUid;		
	private String m_typeId;
	private String m_typeDescription;
	
	public String getLandmarkUid()
	{
		return m_landmarkUid;
	}
	
	public void setLandmarkUid(String landmarkUid)
	{
		m_landmarkUid = landmarkUid;
	}
	
	public String getTypeId()
	{
		return m_typeId;
	}
	
	public void setTypeId(String typeId)
	{
		m_typeId = typeId;
	}
	
	public String getTypeDescription(String typeDescription)
	{
		return m_typeDescription;
	}
	
	public void setTypeDescription(String typeDescription)
	{
		m_typeDescription = typeDescription;
	}
	

	public Landmark
	(
		String dataSet,
		GeoPosition position,
		String description,
		String landmarkUid,
		String typeId,
		String typeDescription
	) 
	{
		super(dataSet, position, description);
		this.m_landmarkUid = landmarkUid;
		this.m_typeId = typeId;
		this.m_typeDescription = typeDescription;
	}

}

