package JJPModel;

import java.text.DecimalFormat;

public class GeoPosition 
{
	private double m_latitude;
	private double m_longitude;
	
	public double getLtitude() 
	{
		return m_latitude;
	}
	public void setLatitude(double latitude) 
	{
		this.m_latitude = latitude;
	}
	
	public double getLongitude() 
	{
		return m_longitude;
	}
	
	public void setLongitude(double longitude) 
	{
		this.m_longitude = longitude;
	}
	
	public String toString()
	{		
        DecimalFormat df = new DecimalFormat("###.#######");        
        return String.format("%s, %s", df.format(m_latitude), df.format(m_longitude));
	}
	
	public GeoPosition(double latitude, double longitude)
	{
		m_latitude = latitude;
		m_longitude = longitude;		
	}	

	public GeoPosition(String position) throws Exception
	{
		String[] tempstring = position.split(",");
		if (tempstring.length != 2)
		{
			throw new Exception("Malformed Geo Position String: " + position);
		}
		
		m_latitude = Double.parseDouble(tempstring[0]);
		m_longitude = Double.parseDouble(tempstring[1]);	
	}	
}
