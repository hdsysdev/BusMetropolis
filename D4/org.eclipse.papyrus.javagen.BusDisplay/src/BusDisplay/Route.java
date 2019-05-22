// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package BusDisplay;

/************************************************************/

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * 
 */
public class Route {

	/**
	 * 
	 */
	public Integer routeNo;
	/**
	 * 
	 */
	public String destination;
	/**
	 * 
	 */
	public String origin = "";
	/**
	 * 
	 */
	public ArrayList<LocalTime> schedule;

	public Route(Integer routeNo, String destination, String origin, ArrayList<LocalTime> schedule) {
		this.routeNo = routeNo;
		this.destination = destination;
		this.origin = origin;
		this.schedule = schedule;
	}

	//Equals method for testing
	@Override
	public boolean equals(Object obj) {
		if( obj == this){
			return true;
		}

		if (!(obj instanceof Route)) {
			return false;
		}

		return this.routeNo.equals(((Route) obj).routeNo)
				&& this.destination.equals(((Route) obj).destination)
				&& this.origin.equals(((Route) obj).origin)
				&& this.schedule.equals(((Route) obj).schedule);
	}
};
