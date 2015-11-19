package Common;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;


public class Utils 
{	
	public static String GetCurrentDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Calendar cal = Calendar.getInstance();				
		return dateFormat.format(cal.getTime());
	}		
	
	public static Date getDateFromString(String dateString) throws Exception
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		return df.parse(dateString);
	}
	
	public static Date getDateFromSimpleString(String dateString) throws Exception
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		return df.parse(dateString);
	}
	
	public static String getSimpleTimeString(Date date)
	{
		if (date == null)
		{
			return "??:??";
		}
		
		DateFormat df = new SimpleDateFormat("HH:mm");
		
		return df.format(date);
	}
	
	public static String getSimpleDateTimeString(Date date)
	{	
		if (date == null)
		{
			return "????-??-?? ??:??";
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		return df.format(date);
	}	
	
	public static String getSimpleDateString(Date date)
	{
		if (date == null)
		{
			return "????-??-??";
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		return df.format(date);
	}
	
	public static boolean CheckResponseStatus(JSONObject response)
	{	
		if (response == null)
		{
			System.out.println("Received JSON response is null.");
			return false;			
		}
		
		try
		{	
			JSONObject status = response.getJSONObject("Status");
			int statusCode = status.getInt("Severity"); 
		    if ( statusCode != 0)
		    {
		    	System.out.println( statusCode == 1 ? "Warning" : "Error");
		    	JSONArray details = status.getJSONArray("Details"); 
		        if(details != null)
		        {
		        	System.out.println("The following problems were encountered:");
		            for (int index = 0; index < details.length(); index++)
		            {
		            	JSONObject detail = details.getJSONObject(index);
		                System.out.println(detail.getString("Message"));
		            }
		        }
		        return false;
		    }
		}		
		catch(Exception exception)
		{
			System.out.format("Exception during response status check: %s", exception.getMessage());
			return false;
		}
		
		return true;
	}
	
}
