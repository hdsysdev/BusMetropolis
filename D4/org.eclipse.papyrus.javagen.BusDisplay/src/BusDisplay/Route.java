package BusDisplay;

import java.time.LocalTime;
import java.util.ArrayList;


public class Route {


	public Integer routeNo;

	public String destination;

	public String origin;


	public ArrayList<LocalTime> schedule;

	public Route(Integer routeNo, String destination, String origin, ArrayList<LocalTime> schedule) {
		this.routeNo = routeNo;
		this.destination = destination;
		this.origin = origin;
		this.schedule = schedule;
	}
}
