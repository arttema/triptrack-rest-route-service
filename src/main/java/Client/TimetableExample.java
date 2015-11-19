package Client;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.json.JSONObject;

import Common.Utils;

public class TimetableExample {

	public void Run(String API_KEY) {
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("                         Timetable Request                              ");
		System.out.println("************************************************************************");

		final String dataSet = "Perth";
		final String from = "Perth:99906";
		final String to = "Perth:99172";
		final String date = Utils.getSimpleDateString(Calendar.getInstance().getTime());
		try {
			String request = MakeRequest(API_KEY, dataSet, from, to, "", date, "", true);

			System.out.println("Performing Timetable request...\n");

			if (RESTClient.DoRESTQuery(request)) {
				System.out.println("Timetable Response received:\n");

				JJPModel.Timetable response = ParseTimetableResponse(RESTClient.getResponse());
				if (response != null) {
					PrintTimetableResponse(response);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	private JJPModel.Timetable ParseTimetableResponse(String response) throws Exception {
		JSONObject jsonResponse = new JSONObject(response);

		if (!Utils.CheckResponseStatus(jsonResponse)) {
			return null;
		}

		JJPModel.Timetable timetable = JJPModel.Timetable.FromJson(jsonResponse);

		return timetable;
	}

	private void PrintTimetableResponse(JJPModel.Timetable timetable) {
		String startDate = Utils.getSimpleDateString(timetable.getStartDate());
		String endDate = Utils.getSimpleDateString(timetable.getEndDate());
		System.out.format("%d trips returned, which run between %s and %s\n", 
							timetable.getTimetableTrips().size(), startDate, endDate);

		JJPModel.TimetableTrip tt = timetable.getTimetableTrips().firstElement();
		
		System.out.println("An example trip from the response:");
		System.out.println();
		
		System.out.format(" Trip UID: %s", tt.getTripUid());
		System.out.println();
		
		JJPModel.Route route = tt.getRoute();
		System.out.format(" Trip belongs to %s route %s (%s).\n", route.getTransportMode(), route.getCode(), route.getName());
		System.out.println();
		
		System.out.println(" Trip runs on the following dates:");
		String datePattern = tt.getRunningDatePattern();
		for (int i = 0; i < datePattern.length(); i++)
        {
            if (datePattern.charAt(i) != '-')
            {
            	Date runningDate = AddDays(timetable.getStartDate(), i);
            	System.out.format("   %s\n", Utils.getSimpleDateString(runningDate));
            }
        }
		System.out.println();
		
		System.out.println(" Arrival and departure times at each stop:");
		System.out.println(" (Note that times >= 24:00 may be present, and these indicate trip stops");
		System.out.println("  that occur on the day after the base date of the trip.)");
		System.out.println();
		
		Vector<JJPModel.TransitStop> stopPatterns = tt.getStopPattern();
		Vector<JJPModel.TripStopTiming> stopTimings = tt.getTripStopTimings();
		
		System.out.format("   %-30s    %6s  %6s\n", "TRIP STOP", "ARRIVE", "DEPART");
		System.out.println("   ------------------------------------------------");
		for (int i = 0; i < stopPatterns.size(); i++)
        {
			JJPModel.TransitStop stop = stopPatterns.get(i);
			JJPModel.TripStopTiming timing = stopTimings.get(i);
			
            String stopName = stop.getDescription();
            String arrivalTime = timing.getArrivalTime();
            String departureTime = timing.getDepartureTime();

            System.out.format("   %-30s    %6s  %6s\n", stopName, arrivalTime, departureTime);
        }
		System.out.println();

	}

	private String MakeRequest(String API_KEY, String dataSet, String from, String to, String route, String startDate,
			String endDate, boolean includeIntermediateStops) {
		return String.format("DataSets/%s/Timetable?ApiKey=%s&FromStopUid=%s&ToStopUid=%s&Route=%s"
				+ "&StartDate=%s&EndDate=%s&IncludeIntermediateStops=%b&Format=json", dataSet, API_KEY, from, to,
				route, startDate, endDate, includeIntermediateStops);
	}

	private static Date AddDays(Date date, int numDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, numDays);
		return cal.getTime();
	}
}
