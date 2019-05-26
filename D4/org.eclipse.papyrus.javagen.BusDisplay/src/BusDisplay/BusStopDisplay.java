package BusDisplay;

import java.time.LocalTime;
import java.util.*;
import java.lang.String;
import java.util.stream.Collectors;

public class BusStopDisplay implements Observer {
	ArrayList<ExpectedBus> expectedBuses = new ArrayList<>();
	ArrayList<ExpectedBus> removedBuses = new ArrayList<>();
	ArrayList<ExpectedBus> delayedBuses = new ArrayList<>();
	ArrayList<Route> callingRoutes;
	private String id;
	private String name;


	BusStopDisplay create(String stopInfo, String rsInfo, String ttInfo) {
	    BusStopDisplay busStopDisplay = new BusStopDisplay();

        busStopDisplay.id = RoutesStopTimetableParser.parseStopInfo(stopInfo)[0];
        busStopDisplay.name = RoutesStopTimetableParser.parseStopInfo(stopInfo)[1];

        busStopDisplay.callingRoutes = RoutesStopTimetableParser.parseRoutes(rsInfo, ttInfo);
        busStopDisplay.addScheduledToExpected();


        return busStopDisplay;
	}

	void addScheduledToExpected(){
		ArrayList<ExpectedBus> expectedBusList = new ArrayList<>();
		List<Route> callingRoutes = getCallingRoutes();

		for(Route r: callingRoutes){
			for(LocalTime t: r.schedule) {
				ExpectedBus currentBus = new ExpectedBus(r.routeNo,
						BusStatus.onTime,
						0,
						r.destination,
						r.schedule.indexOf(t) + 1,
						t, this);

				if (!removedBuses.contains(currentBus) && !delayedBuses.contains(currentBus)){
					expectedBusList.add(currentBus);
				}

			}
		}
		expectedBusList.addAll(delayedBuses);
		//Sort expectedBusList using compareTo method in expected bus
		Collections.sort(expectedBusList);
		expectedBuses = expectedBusList;
	}

	//Returning new array list with the same objects as the old one because the old one is still modifiable
	List<Route> getCallingRoutes() {
		return Collections.unmodifiableList(new ArrayList<>(callingRoutes));
	}

	/**
	 *Gets departure times for the route with the routeNo passed as a parameter and returns them as unmodifiable list
	 */
	List<LocalTime> getDepartureTimes(Integer routeNo) {
		List<LocalTime> departureTimes = new ArrayList<>();
		for(Route r: getCallingRoutes()) {
			if (r.routeNo.equals(routeNo)){
				departureTimes = r.schedule;
			}
		}
		return Collections.unmodifiableList(new ArrayList<>(departureTimes));
	}

	/**
	 *Gets time of the next scheduled bus after the passed time for the route with the passed route number
	 */
	LocalTime getTimeOfNextBus(Integer routeNo, LocalTime time) {
		LocalTime nextBusTime = time;
		for (Route r: getCallingRoutes()){
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
		for (ExpectedBus delayedBus: new ArrayList<>(delayedBuses)){
			if (delayedBus.status == BusStatus.cancelled){
				delayedBuses.remove(delayedBus);
				removedBuses.add(delayedBus);
			}
		}

		for (ExpectedBus expectedBus: new ArrayList<>(expectedBuses)){
			//Buses removed if they've been canceled and it is over 1 minute past their due time
			if(expectedBus.status == BusStatus.cancelled && time.isAfter(expectedBus.time.plusMinutes(1))){
				expectedBuses.remove(expectedBus);
				removedBuses.add(expectedBus);
			}
			//Buses removed if they are marked as departed
			else if (expectedBus.status == BusStatus.departed){
				expectedBuses.remove(expectedBus);
				removedBuses.add(expectedBus);
			}
			//Buses marked as departed and removed if the current time is past the bus due time + delay time + 3 minutes
			else if (time.isAfter(expectedBus.time.plusMinutes(expectedBus.delay + 3))) {
				expectedBus.setStatus(BusStatus.departed);
				expectedBuses.remove(expectedBus);
				removedBuses.add(expectedBus);
			}
			//If the bus is marked as delayed, add it to delayedBuses to be carried over to the next iteration
			else if (expectedBus.status == BusStatus.delayed){
				if (!delayedBuses.contains(expectedBus)){
					delayedBuses.add(expectedBus);
				}
			}
			//Loads new buses if the number of buses on the display drops below 10
			else if (expectedBuses.size() < 10){
				addScheduledToExpected();
			}
		}

		expectedBuses = expectedBuses.stream().limit(10).collect(Collectors.toCollection(ArrayList::new));

		for(ExpectedBus bus: expectedBuses){
			String busStatus = bus.status == BusStatus.onTime ? "On Time" :
					(bus.status == BusStatus.delayed ? bus.delay + " Minutes Delay" : bus.status.toString());

			System.out.printf("| %2d | %2d | %18s | %5s | %16s |\n",
					expectedBuses.indexOf(bus) + 1,
					bus.routeNo,
					bus.destination,
					bus.time.toString(),
					busStatus);
		}
	}

	//Update function replaces
    @Override
    public void update(Observable o, Object arg) {
	    expectedBuses.get(expectedBuses.indexOf(o)).delay = ((ExpectedBus) o).delay;
		expectedBuses.get(expectedBuses.indexOf(o)).status = ((ExpectedBus) o).status;
    }
}
