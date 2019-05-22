package BusDisplay;


import java.time.LocalTime;

public class ExpectedBus  {
	public int routeNo;
	/**
	 * 
	 */
	public BusStatus status;
	/**
	 * 
	 */
	public int delay;
	/**
	 * 
	 */
	public String destination;
	/**
	 * 
	 */
	public int journeyNo;
	/**
	 * 
	 */
	public LocalTime time;

	public ExpectedBus(int routeNo, BusStatus status, int delay, String destination, int journeyNo, LocalTime time) {
		this.routeNo = routeNo;
		this.status = status;
		this.delay = delay;
		this.destination = destination;
		this.journeyNo = journeyNo;
		this.time = time;
	}
}
