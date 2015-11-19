package JJPModel;

import java.util.Date;


public class TripStop 
{
	private TransitStop m_transitStop;
	private Date m_arriveTime;
	private Date m_departTime;
	private String m_sequenceNumber;

	public void setTransitStop(TransitStop transitLocation) 
	{
		m_transitStop = transitLocation;
	}

	public TransitStop getTransitStop() 
	{
		return m_transitStop;
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

	public void setSequenceNumber(String sequenceNumber) 
	{
		m_sequenceNumber = sequenceNumber;
	}

	public String getSequenceNumber() 
	{
		return m_sequenceNumber;
	}

}
