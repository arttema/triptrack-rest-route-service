package JJPModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;


public class TripStop 
{
	private TransitStop m_transitStop;
    @JsonInclude(Include.NON_NULL)
    private Date m_arriveTime;
    @JsonInclude(Include.NON_NULL)
	private Date m_departTime;
    @JsonInclude(Include.NON_NULL)
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
