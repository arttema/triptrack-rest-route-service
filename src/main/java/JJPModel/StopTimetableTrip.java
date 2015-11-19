package JJPModel;

import java.util.Date;

public class StopTimetableTrip 
{
	private TripSummary m_summary;

    private Date m_arriveTime;

    private Date m_departTime;

    public StopTimetableTrip() 
    {
    }

    public StopTimetableTrip
    (
           TripSummary summary,
           Date arriveTime,
           Date departTime
    ) 
    {
    	m_summary = summary;
        m_arriveTime = arriveTime;
        m_departTime = departTime;
    }
    
    public TripSummary getSummary() 
    {
        return m_summary;
    }
    
    public void setSummary(TripSummary summary) 
    {
    	m_summary = summary;
    }
    
    public Date getArriveTime() 
    {
        return m_arriveTime;
    }
   
    public void setArriveTime(Date arriveTime) 
    {
        m_arriveTime = arriveTime;
    }
   
    public Date getDepartTime() 
    {
        return m_departTime;
    }
    
    public void setDepartTime(Date departTime) 
    {
        m_departTime = departTime;
    }

}
