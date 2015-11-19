package JJPModel;

public class TripSummary
{
	private String m_mode;
    private String m_tripUid;
    private String m_tripCode;    
    private String m_routeName;
    private String m_routeCode;    
    private String m_headsign;  
    private ServiceProvider m_serviceProvider;

    public TripSummary() 
    {
    }

    public TripSummary
    (
           String mode,
           String tripUid,
           String tripCode,           
           String routeName,
           String routeCode,           
           String headsign           
    ) 
    {
           m_mode = mode;
           m_tripUid = tripUid;
           m_tripCode = tripCode;           
           m_routeName = routeName;
           m_routeCode = routeCode;           
           m_headsign = headsign;           
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

    public String getHeadsign() 
    {
        return m_headsign;
    }
   
    public void setHeadsign(String headsign) 
    {
        m_headsign = headsign;
    }

	public void setServiceProvider(ServiceProvider serviceProvider) 
	{
		m_serviceProvider = serviceProvider;
	}

	public ServiceProvider getServiceProvider() 
	{
		return m_serviceProvider;
	}



}
