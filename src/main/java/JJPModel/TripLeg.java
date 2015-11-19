package JJPModel;

import java.util.Date;

import Common.Utils;

public class TripLeg  extends Leg 
{

	public Date getDepartTime() 
	{
		return m_departTime;
	}

	public void setDepartTime(Date departTime) 
	{
		m_departTime = departTime;
	}

	public Date getArriveTime() 
	{
		return m_arriveTime;
	}

	public void setArriveTime(Date arriveTime) 
	{
		m_arriveTime = arriveTime;
	}

	public String getMode() 
	{
		return m_mode;
	}

	public void setMode(String mode) 
	{
		m_mode = mode;
	}

	public String getTripUid() 
	{
		return m_tripUid;
	}

	public void setTripUid(String tripUid) 
	{
		m_tripUid = tripUid;
	}

	public String getTripCode() 
	{
		return m_tripCode;
	}

	public void setTripCode(String tripCode) 
	{
		m_tripCode = tripCode;
	}

	public String getRouteName() 
	{
		return m_routeName;
	}

	public void setRouteName(String routeName) 
	{
		m_routeName = routeName;
	}

	public String getRouteCode() 
	{
		return m_routeCode;
	}

	public void setRouteCode(String routeCode) 
	{
		m_routeCode = routeCode;
	}

	public ServiceProvider getServiceProvider() 
	{
		return m_serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProviderUid) 
	{
		m_serviceProvider = serviceProviderUid;
	}

	public String getHeadsign() 
	{
		return m_headsign;
	}

	public void setHeadsign(String headsign) 
	{
		m_headsign = headsign;
	}
	
	public void setTripStartTime(String tripStartTime) 
	{
		m_tripStartTime = tripStartTime;
	}

	public String getTripStartTime() 
	{
		return m_tripStartTime;
	}

	public void setOriginStopSequenceNumber(String originStopSequenceNumber) 
	{
		m_originStopSequenceNumber = originStopSequenceNumber;
	}

	public String getOriginStopSequenceNumber() 
	{
		return m_originStopSequenceNumber;
	}

	public void setDestinationStopSequenceNumber(String destinationStopSequenceNumber) 
	{
		m_destinationStopSequenceNumber = destinationStopSequenceNumber;
	}

	public String getDestinationStopSequenceNumber() 
	{
		return m_destinationStopSequenceNumber;
	}

	private Date m_departTime;
	private Date m_arriveTime;
	private String m_mode;
	private String m_tripUid;
	private String m_tripCode;
	private String m_routeName;
	private String m_routeCode;
	private ServiceProvider m_serviceProvider;
	private String m_headsign;
	private String m_tripStartTime;
	private String m_originStopSequenceNumber;
	private String m_destinationStopSequenceNumber;
	
	public TripLeg
	(
		int id, 
		Location origin, 
		Location destination, 
		String polyline, 
		int duration,
		Date departTime, 
		Date arriveTime,
		String mode,
		String tripUid,
		String tripCode,
		String routeName,
		String routeCode,
		ServiceProvider serviceProvider,
		String headsign
	)
	{
		super(id, origin, destination, polyline, duration);
		m_departTime = departTime;
		m_arriveTime = arriveTime;
		m_mode = mode;
		m_tripUid = tripUid;
		m_tripCode = tripCode;
		m_routeName = routeName;
		m_routeCode = routeCode;
		m_serviceProvider = serviceProvider;
		m_headsign = headsign;
	}


}
