package BusDisplay;

/************************************************************/

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.String;

/**
 * 
 */
public class BusStopDisplay {
	public ArrayList<ExpectedBus> expectedBuses;
	public ArrayList<Route> callingRoutes;
	public BusStopId id;
	public String name;

//	create is the constructor for the BusStopDisplay, it creates an example state of the system so it involves
//	creating routes and their timetables and involves parsing the given configuration files
	public BusStopDisplay create(Data stopInfo, Data rsInfo, Data ttInfo) {
		expectedBuses.add(new ExpectedBus(3,
				BusStatus.onTime,
				0,
				"Centennial park",
				0,
				LocalTime.of(8, 11)));
		expectedBuses.add(new ExpectedBus(3,
				BusStatus.onTime,
				0,
				"Centennial park",
				1,
				LocalTime.of(9, 11)));
		expectedBuses.add(new ExpectedBus(25,
				BusStatus.onTime,
				0,
				"Topaz Lane",
				2,
				LocalTime.of(7, 27)));
		expectedBuses.add(new ExpectedBus(25,
				BusStatus.onTime,
				0,
				"Topaz Lane",
				3,
				LocalTime.of(7, 57)));



	}
	//Adds scheduled buses from each route calling at the bus stop to the expected buses list sorting them in ascending time
	public void addScheduledToExpected (){
		ArrayList<ExpectedBus> expectedBusList = this.expectedBuses;
		ArrayList<Route> callingRoutes = getCallingRoutes();

		for(Route r: callingRoutes){
			for(LocalTime t: r.schedule)
				expectedBusList.add(new ExpectedBus(r.routeNo,
						BusStatus.onTime,
						0,
						r.destination,
						r.schedule.indexOf(t) + 1,
						t));
		}

		Collections.sort(expectedBusList);
		this.expectedBuses = expectedBusList;
	}


	public ArrayList<Route> getCallingRoutes() {
		return (ArrayList<Route>) Collections.unmodifiableList(this.callingRoutes);
	}

	/**
	 *Gets departure times for the route with the routeNo passed as a parameter and returns them as unmodifiable list
	 */
	public ArrayList<LocalTime> getDepartureTimes(Integer routeNo) {
		ArrayList<LocalTime> departureTimes = new ArrayList<>();
		for(Route r: getCallingRoutes()) {
			if (r.routeNo.equals(routeNo)){
				departureTimes = r.schedule;
			}
		}
		return (ArrayList<LocalTime>) Collections.unmodifiableList(departureTimes);
	}

	/**
	 *Gets time of the next scheduled bus after the passed time for the route with the passed route number
	 */
	public LocalTime getTimeOfNextBus(Integer routeNo, LocalTime time) {
		LocalTime nextBusTime = LocalTime.now();
		for (Route r: getCallingRoutes()){
			if(r.routeNo.equals(routeNo)){
				LocalTime finalNextBusTime = nextBusTime;
				nextBusTime = r.schedule.stream().filter(x -> finalNextBusTime.isAfter(time)).findFirst().get();
			}
		}
		return nextBusTime;
	}

	/**
	 * Function to display bus times
	 */
	public void display(LocalTime time) {
		String[][] display = new String[9][4];
		for (ExpectedBus expectedBus: this.expectedBuses){
			if(expectedBus.status == BusStatus.cancelled && expectedBus.time.isBefore(time)){
				this.expectedBuses.remove(expectedBus);
			} else if (expectedBus.time.plusMinutes(expectedBus.delay + 3).isBefore(time)) {
				this.expectedBuses.remove(expectedBus);
			} else if (this.expectedBuses.size() < 10){
				addScheduledToExpected();
			}
		}

        /*Display is represented as a grid of cells with rows and columns, the display can show up to 10 rows of buses
        * and information about the bus like it's time and route number is each assigned to it's own column
        * (represented by the second dimension of the 'display' array). The for loop fills these rows and columns in
        * with variables from this BusStopDisplay object 
        */
		for (int i = 0; i >= 0 && i < 10; i++){
		    ExpectedBus thisBus = this.expectedBuses.get(i);

			display[i][0] = Integer.toString(i + 1);
            display[i][1] = Integer.toString(thisBus.routeNo);
            display[i][2] = thisBus.destination;
            display[i][3] = thisBus.time.toString();
            display[i][4] = thisBus.status == BusStatus.onTime ?
                    thisBus.delay + " minutes delay" : thisBus.status.toString();
		}
	}

	/**
	 * 
	 * @param stopInfo 
	 * @param rsInfo 
	 * @return 
	 * @param ttInfo 
	 */

}
