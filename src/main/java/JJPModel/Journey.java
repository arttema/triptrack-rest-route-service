package JJPModel;

import java.util.Date;
import java.util.Vector;


public class Journey 
{
	private int m_id;
	private Location m_origin;
	private Location m_destination;
	private Date m_departTime;
	private Date m_arriveTime;
	private Vector<Leg> m_legList;
	private int m_duration;	
	private Vector<FareId> m_validFareList;
	public void setDuration(int duration)
	{
		m_duration = duration;
	}
	
	public int getDuration()
	{
		return m_duration;
	}
	
	public void setId(int id)
	{
		m_id = id;
	}
	
	public int getId()
	{
		return m_id;
	}
	
	public void setOrigin(Location origin)
	{
		m_origin = origin;
	}
	
	public Location getOrigin()
	{
		return m_origin;
	}
		
	public void setDestination(Location destination)
	{
		m_destination= destination;
	}
	
	public Location getDestination()
	{
		return m_destination;
	}	
		
	public void setDepartTime(Date departTime)
	{
		m_departTime = departTime;
	}
	
	public Date getDepartTime()
	{
		return m_departTime;
	}
		
	public void setArriveTime(Date arriveTime)
	{
		m_arriveTime = arriveTime;
	}
	
	public Date getArriveTime()
	{
		return m_arriveTime;
	}
		
	public void setLegList(Vector<Leg> legList)
	{
		m_legList = legList;
	}
	
	public void addLegToList(Leg leg)
	{
		m_legList.add(leg);
	}
	
	public void addValidFare(FareId fare)
	{
		m_validFareList.add(fare);
	}
		
	public Vector<Leg> getLegList()
	{
		return m_legList;
	}
	
	public Vector<FareId> getValidFareList()
	{
		return m_validFareList;
	}
	
	public Journey(int id, Location origin, Location destination, Date departTime, Date arriveTime, int duration)
	{
		m_id = id;
		m_origin = origin;
		m_destination = destination;
		m_departTime = departTime;
		m_arriveTime = arriveTime;
		m_duration = duration;
		m_legList = new Vector<Leg>();
		m_validFareList = new Vector<FareId>();
	}	
}
