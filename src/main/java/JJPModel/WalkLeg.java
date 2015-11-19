package JJPModel;

public class WalkLeg extends Leg 
{
	private int m_walkDistance;
	
	public int getWalkDistance() 
	{
		return m_walkDistance;
	}

	public void setWalkDistance(int walkDistance) 
	{
		m_walkDistance = walkDistance;
	}

	public WalkLeg
	(
		int id, 
		Location origin, 
		Location destination, 
		String polyline, 
		int duration,
		int walkDistance
	)
	{
		super(id, origin, destination, polyline, duration);
		m_walkDistance = walkDistance;
	}
}
