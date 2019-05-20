package BusDisplay;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BusInfoSim {

    static void printAllFieldsFromObj(Object object){
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                System.out.printf("%s: %s%n", field.getName(), field.get(object));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BusStopDisplay display = null;
        try {
            display = new BusStopDisplay()
                    .create("..\\stop_info.csv",
                            "..\\routes.csv",
                            "..\\timetable.csv");
        } catch (IOException e) {
            System.out.println("One or more of the entered file names is incorrect.");
        }

        //Set display's time to 6am before all upcoming buses
        System.out.println("This is the state of the display at 6am with all bus times in the future: ");
        display.display(LocalTime.of(6, 00));

        simulate(display);

        //Testing set bus status, set delay and observer design pattern using parsed bus objects in expectedBuses array
        System.out.println("\nThis is a test for the observer design checking that the object is updated in the observer");
        System.out.println("Bus info before updating bus status: " + Arrays.toString(display.display[0]));
        display.expectedBuses.get(0).setStatus(BusStatus.delayed);
        display.expectedBuses.get(0).setDelay(15);
        display.display(LocalTime.of(6, 0));
        System.out.println("Bus info after updating bus status: " + Arrays.toString(display.display[0]));

        System.out.println("Bus info before updating bus delay: " + Arrays.toString(display.display[1]));
        display.expectedBuses.get(1).setDelay(15);
        display.display(LocalTime.of(6, 0));
        System.out.println("Bus info after updating bus delay: " + Arrays.toString(display.display[1]));

        //Testing observer design pattern by wiping expected buses array and using new manually created buses
        display.expectedBuses = new ArrayList<>();
        ExpectedBus testBus = new ExpectedBus(21, BusStatus.delayed,
                15, "Test Destination", 1, LocalTime.of(7, 00), display);
        display.expectedBuses.add(testBus);
        //Printing all fields of the newly created bus
        display.display(LocalTime.of(6, 0));
        System.out.println("\nFresh bus before updating bus status & delay: " + Arrays.toString(display.display[0]));
        testBus.setStatus(BusStatus.onTime);
        testBus.setDelay(1000);
        //Updating display array with the new bus
        display.display(LocalTime.of(6, 0));
        System.out.println("After creating and updating fresh bus obj: " + Arrays.toString(display.display[0]));


        //Demo loop
        while (true){
            display.display(LocalTime.now());
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
        //Print display array like it would be shown on the display screen at the stop
        for (String[] row: display.display){
            for (String cell: row){
                System.out.print(cell + " | ");
            }
            System.out.println();
        }


    }

}
