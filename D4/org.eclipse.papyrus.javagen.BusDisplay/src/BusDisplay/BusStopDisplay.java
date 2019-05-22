package BusDisplay;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.String;

/**
 * 
 */
public class BusStopDisplay {
    //Changed
	private String name;
	private String id;
	ArrayList<ExpectedBus> expectedBusList;
	ArrayList<Route> routeList;

    String[][] display = new String[10][5];

	public BusStopDisplay(String name, String id, ArrayList<Route> routeList) {
		this.name = name;
		this.id = id;
		this.routeList = routeList;
	}

	public BusStopDisplay() {

	}


	//	create is the constructor for the BusStopDisplay, it creates an example state of the system so it involves
//	creating routes and their timetables and involves parsing the given configuration files
	public BusStopDisplay create(String routesInfo, String stopInfo) throws IOException {

		String name = RoutesAndStopInfoParser.parseBusStopInfo(stopInfo)[1];
		String id = RoutesAndStopInfoParser.parseBusStopInfo(stopInfo)[0];
		ArrayList<Route> routeList = RoutesAndStopInfoParser.parseRouteList(routesInfo);

	    BusStopDisplay stopDisplay = new BusStopDisplay(name, id, routeList);

        stopDisplay.addScheduledToExpected();

        return stopDisplay;
	}

	//Changed
    List<Route> getCallingRoutes() {
        return Collections.unmodifiableList(this.routeList);
    }

    /**
     *Gets departure times for the route with the routeNo passed as a parameter and returns them as unmodifiable list
     */
    //Changed

    public List<LocalTime> getDepartureTimes(Integer routeNumber) {
        List<LocalTime> departuresList = new ArrayList<>();
        List<Route> callingRouteList = getCallingRoutes();

        for (int i = 0; i < callingRouteList.size(); i += 1){
            if (callingRouteList.get(i).routeNo == routeNumber){
                departuresList = callingRouteList.get(i).schedule;
            }
        }

        return Collections.unmodifiableList(departuresList);
    }

	//Adds scheduled buses from each route calling at the bus stop to the expected buses list sorting them in ascending time
	//Changed
    public void addScheduledToExpected (){
		ArrayList<ExpectedBus> expectedBusList = new ArrayList<>();
		List<Route> callingRoutes = getCallingRoutes();

		//Changed
		for (int routeNum = 0; routeNum < callingRoutes.size(); routeNum += 1){
			for (int timeNum = 0; timeNum < callingRoutes.get(routeNum).schedule.size(); timeNum += 1){
			    expectedBusList.add(new ExpectedBus(callingRoutes.get(routeNum).routeNo, BusStatus.onTime, 0,
                        callingRoutes.get(routeNum).destination, routeNum, callingRoutes.get(routeNum).schedule.get(timeNum)));
            }
		}

		this.expectedBusList = expectedBusList;
	}


	/**
	 *Gets time of the next scheduled bus after the passed time for the route with the passed route number
	 */
	//Changed
	public LocalTime getTimeOfNextBus(Integer routeNumber, LocalTime afterTime) {
		LocalTime nextBusTime = afterTime;
		List<Route> callingRoutes = getCallingRoutes();

		//Iterate over all calling routes to find route matching the route number passed
		for (int i = 0; i < callingRoutes.size(); i++){
		    if (callingRoutes.get(i).routeNo == routeNumber)
                //Finds first time in the schedule list which is after the time passed
                for (int x = 0; x < callingRoutes.get(i).schedule.size(); x += 1){
                    if (callingRoutes.get(i).schedule.get(x).isAfter(afterTime)){
                        nextBusTime = callingRoutes.get(i).schedule.get(x);
                    }
                }
        }

		return nextBusTime;
	}

	/**
	 * Function to display bus times, does checks listed in Table 9 of D4 problem description PDF
	 */
	//Changed
	public void disp(LocalTime currentTime) {
		List<ExpectedBus> busArrayList = new ArrayList<>(this.expectedBusList);
        Integer busListSize = this.expectedBusList.size();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		for (int i = 0; i < busListSize; i += 1){
		    ExpectedBus currentBus = this.expectedBusList.get(i);

            if (currentBus.time.plus(Duration.ofMinutes(currentBus.delay + 3)).isBefore(currentTime)){
                busArrayList.remove(currentBus);
            }
		    else if (currentBus.status == BusStatus.canceled && currentBus.time.isBefore(currentTime)){
                busArrayList.remove(i);
            }
		    else if (busListSize.compareTo(10) == -1){
		        addScheduledToExpected();
            }
        }

		this.expectedBusList = (ArrayList<ExpectedBus>) busArrayList;

        /*Display is represented as a grid of cells with rows and columns, the display can show up to 10 rows of buses
        * and information about the bus like it's time and route number is each assigned to it's own column
        * (represented by the second dimension of the 'display' array). The for loop fills these rows and columns in
        * with variables from this BusStopDisplay object
        */
        //Changed
		for (Integer i = 0; i.compareTo(0) == 1 && i.compareTo(10) == -1 && i.compareTo(busListSize) == -1 || i.compareTo(0) == 0; i++){
            busListSize = this.expectedBusList.size();
		    ExpectedBus currentBus = this.expectedBusList.get(i);

		    //Loops over 4 columns in
		    for (int x = 0; x <= 4; x += 1){
		        if (x == 0)
                    display[i][x] = String.valueOf(i + 1);
		        else if (x == 1)
                    display[i][x] = String.valueOf(currentBus.routeNo);
		        else if (x == 2)
                    display[i][2] = currentBus.destination;
		        else if (x == 3)
                    display[i][x] = currentBus.time.format(dateTimeFormatter);
		        else
                    display[i][x] = currentBus.status + " " + currentBus.delay + " minute delay";
            }
		}
	}
}
