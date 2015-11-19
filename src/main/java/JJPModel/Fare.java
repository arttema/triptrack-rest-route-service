package JJPModel;

public class Fare 
{
		private int m_id;
	    private String m_identifier;
	    private String m_description;
	    private String m_amount;	    
	    private String m_currency;   
	     
	        
	    public int getId() 
	    {
	        return m_id;
	    }
	    
	    public void setId(int id)
	    {
	        this.m_id = id;
	    }

	    public String getIdentifier() 
	    {
	        return m_identifier;
	    }

	    public void setIdentifier(String identifier) 
	    {
	        this.m_identifier = identifier;
	    }

	    public String getDescription() 
	    {
	        return m_description;
	    }


	    public void setDescription(String description) 
	    {
	        this.m_description = description;
	    }	    

	    public String getAmount() 
	    {
	        return m_amount;
	    }

	    public void setAmount(String amount) 
	    {
	        this.m_amount = amount;
	    }
	    
		public void setCurrency(String m_currency) 
		{
			this.m_currency = m_currency;
		}

		public String getCurrency() 
		{
			return m_currency;
		}

}
