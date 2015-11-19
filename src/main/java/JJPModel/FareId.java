package JJPModel;

public class FareId 
{
	int m_id;
	int m_startLeg;
	int m_endLeg;
	
	public FareId(int id)
	{
		m_id = id;
		m_startLeg = 0;
		m_endLeg = 0;
	}
	
	public int getId()
	{
		return m_id;
	}
	
	public int getStartLeg()
	{
		return m_startLeg;
	}
	
	public int getEndLeg()
	{
		return m_endLeg;
	}	
	
	public void setStartLeg(int startLeg)
	{
		m_startLeg = startLeg;
	}
	
	public void setEndLeg(int endLeg)
	{
		m_endLeg = endLeg;
	}
	
	public String applicableLegs()
	{		 		
		if (m_startLeg == 0 && m_endLeg == 0)
		{	
			// The fare is valid for the whole journey
			return "";
		}
				
		if (m_startLeg == m_endLeg)
        {
			//The fare is only applicable to single leg of the Journey
            return String.format("Leg %d", m_startLeg);
        }
		
		// The fare is for multiple journey legs
        return String.format("Leg %d -> Leg %d", m_startLeg, m_endLeg);
	}
	
}
