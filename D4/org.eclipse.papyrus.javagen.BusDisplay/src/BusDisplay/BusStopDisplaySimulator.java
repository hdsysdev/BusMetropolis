package BusDisplay;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BusStopDisplaySimulator {
    //Time the simulation is being run at
    private static LocalTime time = LocalTime.now();

    public static void main(String[] args) {
        BusStopDisplay display = new BusStopDisplay()
                    .create("stop_info.csv",
                            "routes.csv",
                            "timetable.csv");

        //Demo loop
        while (true){
            display.display(time);
            simulate(display);
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void simulate(BusStopDisplay display){
        System.out.println();
        //Used to randomly generate a number
        Random random = new Random();


        ExpectedBus randomBus = display.expectedBuses.get(random.nextInt(9));

        if (time.isAfter(randomBus.time.plusMinutes(randomBus.delay + 3))){
            randomBus.setStatus(BusStatus.departed);
        }


        int randomEvent = random.nextInt(3);

        switch (randomEvent){
            case 0:
                randomBus.setStatus(BusStatus.cancelled);
                System.out.printf("%s route %s bus to %s canceled\n", randomBus.time, randomBus.routeNo, randomBus.destination);
                break;
            case 1:
                randomBus.setDelay(random.nextInt(29) + 1);
                randomBus.setStatus(BusStatus.delayed);
                System.out.printf("%s route %s bus to %s delayed by %s minutes\n", randomBus.time, randomBus.routeNo, randomBus.destination, randomBus.delay);
                break;
            case 3:
                break;
        }

    }
}
