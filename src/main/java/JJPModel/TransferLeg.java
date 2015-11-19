package JJPModel;

public class TransferLeg extends Leg 
{	
	private String m_mode;
	
	public TransferLeg(int id, Location origin, Location destination, String polyline, int duration, String mode)
	{
		super(id, origin, destination, polyline, duration);
		m_mode = mode;
	}
	
	public String getMode()
	{
		return m_mode;
	}
	
	public void setMode(String mode)
	{
		m_mode = mode;
	}	
}
