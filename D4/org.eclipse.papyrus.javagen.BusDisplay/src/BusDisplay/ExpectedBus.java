package BusDisplay;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
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

    //Equals, hashCode and compareTo overrides from the Comparable interface for easily comparing buses when trying to find if buses are
	//present in the removed buses list of bus stop display.
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}

		final ExpectedBus passedObj = (ExpectedBus) obj;

		return this.routeNo == passedObj.routeNo && this.destination.equals(passedObj.destination) && this.time == passedObj.time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(routeNo, destination, time);
	}

	@Override
	public int compareTo(ExpectedBus bus) {
		return this.time.compareTo(bus.time);
	}

	//Function overrides to implement the observer design pattern allowing to add and delete observers as well as notify of changes
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
