package BusDisplay;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.String;


public class BusStopDisplay {

	private String name;
	private String id;
	ArrayList<ExpectedBus> expectedBusList;
	ArrayList<Route> routeList;


	public BusStopDisplay(String name, String id, ArrayList<Route> routeList) {
		this.name = name;
		this.id = id;
		this.routeList = routeList;
	}

	public BusStopDisplay() {

	}

	public BusStopDisplay create(String routesInfo, String stopInfo) throws IOException {

		String name = RoutesAndStopInfoParser.parseBusStop(stopInfo)[1];
		String id = RoutesAndStopInfoParser.parseBusStop(stopInfo)[0];
		ArrayList<Route> routeList = RoutesAndStopInfoParser.parseRouteList(routesInfo);

	    BusStopDisplay stopDisplay = new BusStopDisplay(name, id, routeList);

        stopDisplay.addScheduledToExpected();

        return stopDisplay;
	}


    List<Route> getCallingRoutes() {
        return Collections.unmodifiableList(this.routeList);
    }


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

    public void addScheduledToExpected (){
		ArrayList<ExpectedBus> busArrayList = new ArrayList<>();
		List<Route> callingRoutes = getCallingRoutes();


		for (int routeNum = 0; routeNum < callingRoutes.size(); routeNum += 1){
			for (int timeNum = 0; timeNum < callingRoutes.get(routeNum).schedule.size(); timeNum += 1){
			    busArrayList.add(new ExpectedBus(callingRoutes.get(routeNum).routeNo, BusStatus.onTime, 0,
                        callingRoutes.get(routeNum).destination, routeNum, callingRoutes.get(routeNum).schedule.get(timeNum)));
            }
		}

		this.expectedBusList = busArrayList;
	}

	public LocalTime getTimeOfNextBus(LocalTime afterTime, Integer routeNumber) {
		LocalTime nextBusTime = afterTime;
		List<Route> callingRoutes = getCallingRoutes();


		for (int i = 0; i < callingRoutes.size(); i++){
		    if (callingRoutes.get(i).routeNo == routeNumber)

                for (int x = 0; x < callingRoutes.get(i).schedule.size(); x += 1){
                    if (callingRoutes.get(i).schedule.get(x).isAfter(afterTime)){
                        nextBusTime = callingRoutes.get(i).schedule.get(x);
                    }
                }
        }

		return nextBusTime;
	}

	public void display(LocalTime currentTime) {
		List<ExpectedBus> busArrayList = new ArrayList<>(this.expectedBusList);
        int busListSize = this.expectedBusList.size();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		for (int i = 0; i < busListSize; i += 1){
		    ExpectedBus currentBus = this.expectedBusList.get(i);

            if (currentBus.time.plus(Duration.ofMinutes(currentBus.delay + 3)).isBefore(currentTime)){
                busArrayList.remove(currentBus);
            }
		    else if (currentBus.status == BusStatus.canceled && currentBus.time.isBefore(currentTime)){
                busArrayList.remove(i);
            }
		    else if (busListSize < 10){
		        addScheduledToExpected();
            }
        }

		Collections.sort(busArrayList, new Comparator<ExpectedBus>() {
			@Override
			public int compare(ExpectedBus o1, ExpectedBus o2) {
				int compareResult;

				if (o1.time.isAfter(o2.time)){
					compareResult = 1;
				} else if (o1.time.isBefore(o2.time)){
					compareResult = -1;
				} else {
					compareResult = 0;
				}
				return compareResult;
			}
		});

		this.expectedBusList = (ArrayList<ExpectedBus>) busArrayList;

		for (Integer i = 0; i.compareTo(0) == 1 && i.compareTo(10) == -1 && i.compareTo(busListSize) == -1 || i.compareTo(0) == 0; i++){
            busListSize = this.expectedBusList.size();
		    ExpectedBus currentBus = this.expectedBusList.get(i);

		    String number = String.valueOf(i + 1);
		    String routeNo = String.valueOf(currentBus.routeNo);
		    String destination = currentBus.destination;
		    String dueAt = currentBus.time.format(dateTimeFormatter);
		    String status = currentBus.status + " " + currentBus.delay + " minute delay";

			System.out.printf("%s    %s    %s    %s    %s\n", number, routeNo, destination, dueAt, status);

		}
	}
}
