package BusDisplay;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BusStopDisplaySimulator {
    //Time the simulation is being run at, if running the simulation after ~9pm, not enough buses are running to fill the display
    private static LocalTime time = LocalTime.now();


    public static void main(String[] args) {
        BusStopDisplay display = new BusStopDisplay()
                    .create("stop_info.csv",
                            "routes.csv",
                            "timetable.csv");

        //Checks if it's early enough to fill the display with scheduled buses.
        if (time.isAfter(LocalTime.of(21, 0))){
            System.out.println("You're running the simulation after 9pm. There is not enough buses running to populate the display. \n" +
                    "The time will be automatically reassigned to 8:00am.");
            time = LocalTime.of(8, 0);
        }

        //Demo loop running display function then simulate every 15 seconds
        while (true){
            display.display(time);
            simulate(display);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static void simulate(BusStopDisplay display){
        System.out.println();
        //Used to randomly generate a number
        Random random = new Random();


        ExpectedBus randomBus = display.expectedBuses.get(random.nextInt(10));

        if (time.isAfter(randomBus.time.plusMinutes(randomBus.delay + 3))){
            randomBus.setStatus(BusStatus.departed);
        }

        int canceledBuses = 0;

        for(ExpectedBus bus: display.expectedBuses){
            if(bus.status == BusStatus.cancelled){
                canceledBuses++;
            }
        }
        //Chance of a bus being randomly delayed or canceled, default is 6 which is a 33% chance every iteration
        int randomEvent = random.nextInt(2);

        switch (randomEvent){
            case 0:
                //Limits max canceled buses on the screen to 4 max delayed to 3 
                if (randomBus.status != BusStatus.cancelled && canceledBuses < 4) {
                    randomBus.setStatus(BusStatus.cancelled);
                    System.out.printf("%s route %s bus to %s canceled\n", randomBus.time, randomBus.routeNo, randomBus.destination);
                }
                break;
            case 1:
                if (randomBus.status == BusStatus.onTime && display.delayedBuses.size() < 3) {
                    randomBus.setDelay(random.nextInt(29) + 1);
                    randomBus.setStatus(BusStatus.delayed);
                    System.out.printf("%s route %s bus to %s delayed by %s minutes\n", randomBus.time, randomBus.routeNo, randomBus.destination, randomBus.delay);
                }
                break;
        }

    }
}
