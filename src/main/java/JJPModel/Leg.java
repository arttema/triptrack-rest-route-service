package JJPModel;

public abstract class Leg 
{
	protected int m_id;
	protected Location m_origin;
	protected Location m_destination;	
	protected int m_duration;
	
	public Leg(int id, Location origin, Location destination, String polyline, int duration)
	{
		m_id = id; 
		m_origin = origin;
		m_destination = destination;		
		m_duration = duration;
	}
	
	public int getId()
	{
		return m_id;
	}
	
	public Location getOrigin()
	{
		return m_origin;
	}
	
	public Location getDestination()
	{
		return m_destination;
	}
	
	public int getDuration()
	{
		return m_duration;
	}	
}
