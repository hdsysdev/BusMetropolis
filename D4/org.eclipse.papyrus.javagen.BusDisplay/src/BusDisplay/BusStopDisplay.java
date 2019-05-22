package BusDisplay;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.lang.String;

/**
 * 
 */
public class BusStopDisplay implements Observer {
	private String busName;
	private String busId;
	ArrayList<ExpectedBus> expectedBusList;
	ArrayList<Route> routeList;

    String[][] display = new String[10][5];

//	create is the constructor for the BusStopDisplay, it creates an example state of the system so it involves
//	creating routes and their timetables and involves parsing the given configuration files
	public BusStopDisplay create(String routesInfo, String timetableInfo, String stopInfo) throws IOException {
	    BusStopDisplay busStopDisplay = new BusStopDisplay();

        busStopDisplay.busId = RoutesAndStopInfoParser.parseStopInfo(stopInfo)[0];
        busStopDisplay.busName = RoutesAndStopInfoParser.parseStopInfo(stopInfo)[1];

        busStopDisplay.routeList = RoutesAndStopInfoParser.parseRoutes(routesInfo, timetableInfo);
        busStopDisplay.addScheduledToExpected();


        return busStopDisplay;
	}

	//Adds scheduled buses from each route calling at the bus stop to the expected buses list sorting them in ascending time
	public void addScheduledToExpected (){
		ArrayList<ExpectedBus> expectedBusList = new ArrayList<>();
		List<Route> callingRoutes = getRouteList();

		for(Route r: callingRoutes){
			for(LocalTime t: r.schedule) {
				ExpectedBus currentBus = new ExpectedBus(r.routeNo,
						BusStatus.onTime,
						0,
						r.destination,
						r.schedule.indexOf(t) + 1,
						t, this);

				expectedBusList.add(currentBus);
			}
		}

		Collections.sort(expectedBusList);
		this.expectedBusList = expectedBusList;
	}


	public List<Route> getRouteList() {
		return Collections.unmodifiableList(this.routeList);
	}

	/**
	 *Gets departure times for the route with the routeNo passed as a parameter and returns them as unmodifiable list
	 */
	public List<LocalTime> getDepartureTimes(Integer routeNo) {
		List<LocalTime> departureTimes = new ArrayList<>();
		for(Route r: getRouteList()) {
			if (r.routeNo.equals(routeNo)){
				departureTimes = r.schedule;
			}
		}
		return Collections.unmodifiableList(departureTimes);
	}

	/**
	 *Gets time of the next scheduled bus after the passed time for the route with the passed route number
	 */
	public LocalTime getTimeOfNextBus(Integer routeNo, LocalTime time) {
		LocalTime nextBusTime = time;
		for (Route r: getRouteList()){
			if(r.routeNo.equals(routeNo)){
				nextBusTime = r.schedule.stream().filter(x -> x.isAfter(time)).findFirst().get();
			}
		}
		return nextBusTime;
	}

	/**
	 * Function to display bus times
	 */
	public void display(LocalTime time) {
		ArrayList<ExpectedBus> tempBusList = new ArrayList<>();
		tempBusList.addAll(this.expectedBusList);
		for (ExpectedBus expectedBus: this.expectedBusList){
			if(expectedBus.status == BusStatus.cancelled && expectedBus.time.isBefore(time)){
				tempBusList.remove(expectedBus);
			} else if (expectedBus.time.plusMinutes(expectedBus.delay + 3).isBefore(time)) {
				tempBusList.remove(expectedBus);
			} else if (this.expectedBusList.size() < 10){
				addScheduledToExpected();
			}
		}
		this.expectedBusList = tempBusList;

        /*Display is represented as a grid of cells with rows and columns, the display can show up to 10 rows of buses
        * and information about the bus like it's time and route number is each assigned to it's own column
        * (represented by the second dimension of the 'display' array). The for loop fills these rows and columns in
        * with variables from this BusStopDisplay object
        */
		for (int i = 0; i >= 0 && i < 10 && i < this.expectedBusList.size(); i++){
		    ExpectedBus thisBus = this.expectedBusList.get(i);

			display[i][0] = Integer.toString(i + 1);
            display[i][1] = Integer.toString(thisBus.routeNo);
            display[i][2] = thisBus.destination;
            display[i][3] = thisBus.time.toString();
            display[i][4] = thisBus.status == BusStatus.onTime ?
                    thisBus.delay + " minutes delay" : thisBus.status.toString();
		}
	}

	//Update function replaces
    @Override
    public void update(Observable o, Object arg) {
	    //expectedBusList.set(expectedBusList.indexOf(o), (ExpectedBus) o);
	    expectedBusList.get(expectedBusList.indexOf(o)).delay = ((ExpectedBus) o).delay;
		expectedBusList.get(expectedBusList.indexOf(o)).status = ((ExpectedBus) o).status;
    }
}
