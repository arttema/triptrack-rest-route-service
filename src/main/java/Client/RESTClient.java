package Client;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



final class RESTClient 
{
	private static int m_responseCode;
	private static String m_reasonMessage;	
	private static String m_response;
	
	public static int getResponseCode()
	{
		return m_responseCode;	
	}
	
	public static String getReasonMessage()
	{
		return m_reasonMessage;	
	}
	
	public static String getResponse()
	{
		return m_response;	
	}
	
	public static boolean DoRESTQuery(String url) 
    {
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpGet httpget = new HttpGet(String.format("http://journeyplanner.silverrailtech.com/journeyplannerservice/v2/REST/%s", url));
    	HttpResponse httpResponse;
    	
    	try
    	{    		
    		httpResponse = httpClient.execute(httpget);
    		m_responseCode = httpResponse.getStatusLine().getStatusCode();
    		m_reasonMessage = httpResponse.getStatusLine().getReasonPhrase();	
    	 
    		
    		HttpEntity entity = httpResponse.getEntity();
    		
    		if (entity != null)
    		{
    			InputStream instream = entity.getContent();
    			m_response = ConvertStreamToString(instream);
    			instream.close();
    			return true;
    		}
    		
    	} 
    	catch (Exception e)
    	{    	
    		httpClient.getConnectionManager().shutdown();
    		System.out.println(e.getMessage());
    	}    	
    	
    	return false;    
    }
	
	
    private static String ConvertStreamToString(InputStream is) throws Exception 
    {   	 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try 
        {
            while ((line = reader.readLine()) != null) 
            {
                sb.append(line + "\n");
            }
        }
        catch (Exception e) 
        {
            throw e;
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch (Exception e) 
            {
                throw e;
            }
        }
        return sb.toString();
    }

}
