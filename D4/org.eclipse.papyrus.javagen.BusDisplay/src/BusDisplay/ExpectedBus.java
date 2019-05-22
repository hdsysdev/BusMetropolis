package BusDisplay;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 */
public class ExpectedBus extends Observable implements Comparable<ExpectedBus> {

    private ArrayList<Observer> observers = new ArrayList<>();

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

	public ExpectedBus(int routeNo, BusStatus status, int delay, String destination, int journeyNo, LocalTime time, Observer o) {
		this.routeNo = routeNo;
		this.status = status;
		this.delay = delay;
		this.destination = destination;
		this.journeyNo = journeyNo;
		this.time = time;
        //Add observer to newly created bus
		observers.add(o);
	}

    public void setStatus(BusStatus status) {
        this.status = status;
        notifyObservers();
    }

    public void setDelay(int delay) {
        this.delay = delay;
        notifyObservers();
    }

    @Override
	public int compareTo(ExpectedBus bus) {
		return this.time.compareTo(bus.time);
	}

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers){
            o.update(this, null);
        }
    }
}
