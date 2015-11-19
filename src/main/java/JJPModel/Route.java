package JJPModel;

import org.json.JSONObject;

public class Route {
	private String m_routeUid;
	private String m_code;
	private String m_name;
	private String m_serviceProviderUid;
	private String m_transportMode;
	private String m_description;
	private String m_url;
	private String m_colour;
	private String m_textColour;
	private String m_routeTimetableGroupId;
	

	public static Route FromJson(JSONObject jsonObject) throws Exception {
		Route route = new Route();
		
		route.m_routeUid = jsonObject.getString("RouteUid");
		route.m_code = jsonObject.optString("Code");
		route.m_name = jsonObject.optString("Name");
		route.m_serviceProviderUid = jsonObject.getString("ServiceProviderUid");
		route.m_transportMode = jsonObject.getString("TransportMode");
		route.m_description = jsonObject.optString("Description");
		route.m_url = jsonObject.optString("Url");
		route.m_colour = jsonObject.optString("Colour");
		route.m_textColour = jsonObject.optString("TextColour");
		route.m_routeTimetableGroupId = jsonObject.getString("RouteTimetableGroupId");
		
		return route;
	}
	
	public String getRouteUid() {
		return m_routeUid;
	}
	
	public String getCode() {
		return m_code;
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getServiceProviderUid() {
		return m_serviceProviderUid;
	}
	
	public String getTransportMode() {
		return m_transportMode;
	}
	
	public String getDescription() {
		return m_description;
	}
	
	public String getUrl() {
		return m_url;
	}
	
	public String getColour() {
		return m_colour;
	}
	
	public String getTextColour() {
		return m_textColour;
	}
	
	public String getRouteTimetableGroupId() {
		return m_routeTimetableGroupId;
	}
}
