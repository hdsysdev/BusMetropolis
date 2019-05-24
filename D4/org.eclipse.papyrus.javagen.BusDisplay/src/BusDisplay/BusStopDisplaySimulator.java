package BusDisplay;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BusStopDisplaySimulator {

    public static void main(String[] args) {
        BusStopDisplay display = new BusStopDisplay()
                    .create("stop_info.csv",
                            "routes.csv",
                            "timetable_wrongOrder.csv");


        ////Set display's time to 6am before all upcoming buses
        //System.out.println("This is the state of the display at 6am with all bus times in the future: ");
        //display.display(LocalTime.of(6, 00));
        //System.out.println(Arrays.toString(display.display));
        //simulate(display);
        //System.out.println(display.getTimeOfNextBus(3, LocalTime.of(6, 0)));
        ////Testing set bus status, set delay and observer design pattern using parsed bus objects in expectedBuses array
        //System.out.println("\nThis is a test for the observer design checking that the object is updated in the observer");
        //System.out.println("Bus info before updating bus status: " + Arrays.toString(display.display[0]));
        //display.expectedBuses.get(0).setStatus(BusStatus.delayed);
        //display.expectedBuses.get(0).setDelay(15);
        //display.display(LocalTime.of(6, 0));
        //System.out.println("Bus info after updating bus status: " + Arrays.toString(display.display[0]));
        //
        //System.out.println("Bus info before updating bus delay: " + Arrays.toString(display.display[1]));
        //display.expectedBuses.get(1).setDelay(15);
        //display.display(LocalTime.of(6, 0));
        //System.out.println("Bus info after updating bus delay: " + Arrays.toString(display.display[1]));
        //
        ////Testing observer design pattern by wiping expected buses array and using new manually created buses
        //display.expectedBuses = new ArrayList<>();
        //ExpectedBus testBus = new ExpectedBus(21, BusStatus.delayed,
        //        15, "Test Destination", 1, LocalTime.of(7, 00), display);
        //display.expectedBuses.add(testBus);
        ////Printing all fields of the newly created bus
        //display.display(LocalTime.of(6, 0));
        //System.out.println("\nFresh bus before updating bus status & delay: " + Arrays.toString(display.display[0]));
        //testBus.setStatus(BusStatus.onTime);
        //testBus.setDelay(1000);
        ////Updating display array with the new bus
        //display.display(LocalTime.of(6, 0));
        //System.out.println("After creating and updating fresh bus obj: " + Arrays.toString(display.display[0]));


        //Demo loop
        while (true){
            display.display(LocalTime.of(8, 0));
            simulate(display);
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void simulate(BusStopDisplay display){
        System.out.println();
        //Print display array like it would be shown on the display screen at the stop
        //for (String[] row: display.display){
        //    System.out.print("| ");
        //    for (String cell: row){
        //        System.out.print(cell + " | ");
        //    }
        //    System.out.println();
        //}
        Random random = new Random();

        int randomEvent = random.nextInt(2);
        ExpectedBus randomBus = display.expectedBuses.get(random.nextInt(9));

        switch (randomEvent){
            case 0:
                randomBus.setStatus(BusStatus.cancelled);
                System.out.printf("%s route %s bus canceled\n", randomBus.time, randomBus.routeNo);
                break;
            case 1:
                randomBus.setDelay(random.nextInt(30));
                randomBus.setStatus(BusStatus.delayed);
                System.out.printf("%s route %s bus delayed by %s minutes\n", randomBus.time, randomBus.routeNo, randomBus.delay);
                break;
            //case 2:
            //    for (ExpectedBus bus: display.expectedBuses){
            //        if(bus.status == BusStatus.delayed || bus.status == BusStatus.cancelled){
            //            bus.status = BusStatus.onTime;
            //            bus.delay = 0;
            //            break;
            //        }
            //    }
            //    break;
            //case 3:
            //    break;
            //case 4:
            //    break;
            //case 5:
            //    break;
        }
    }
}
