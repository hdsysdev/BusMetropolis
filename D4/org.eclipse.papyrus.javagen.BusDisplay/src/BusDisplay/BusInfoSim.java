package BusDisplay;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BusInfoSim {
    private static Integer loopNumber = 0;
    static Random rand = new Random();
    //Changed
    public static void main(String[] args) throws IOException, InterruptedException {
        BusStopDisplay display = new BusStopDisplay().create("..\\routes.csv", "..\\stop_info.csv");
        System.out.println("Number Route No    Destination    Due at    Status");
        //Demo loop
        while (true){
            display.display(LocalTime.now());
            loopNumber += 1;
            simulate(display);
            TimeUnit.SECONDS.sleep(3);
        }
    }

    //The simulate function is supposed to simulate how the system would work in a real world application.
    //In the real world, the system would be controlled by a human controller who would update the status/delay time of buses
    //in response to real world situations like buses breaking down, drivers dying, etc
    //This function is supposed to demonstrate how the observer design works in the system by making semi random changes to buses
    //based on the loopNumber counter which keeps track of how many times the demo of the system has run
    //The if statements below make changes to bus states and delay times based on the number of times the simulation loop has run
    //The changes are pseudo-random and not completely random so you can keep running the simulation and the state's of the bus
    //Will keep changing back and forth between being on time, delayed and canceled
    //The rules for when the bus status is changed are completely arbitrary

    public static void simulate(BusStopDisplay display){
        ExpectedBus randomBus = display.expectedBusList.get(rand.nextInt(display.expectedBusList.size()));
        Integer delayedOrCanceled = 0;

        //Keeps track of how many buses are delayed or canceled to ensure that no more than 4 are delayed/canceled at a time
        for(int i = 0; i < display.expectedBusList.size(); i += 1){
            ExpectedBus currentBus = display.expectedBusList.get(i);
            if (currentBus.status == BusStatus.canceled || currentBus.status == BusStatus.delayed){
                delayedOrCanceled += 1;
            }
        }

        if(loopNumber == 2 || loopNumber % 8 == 0){
            randomBus.status = BusStatus.delayed;
            randomBus.delay = 10;
        }
        else if (loopNumber % 7 == 0 || loopNumber % 6 == 0){
            randomBus.status = BusStatus.canceled;
            randomBus.delay = 0;
        }
        else if (loopNumber % 9 == 0 || loopNumber == 5){
            randomBus.status = BusStatus.delayed;
            randomBus.delay = 5;
        }
        else if (loopNumber % 4 == 0 || loopNumber % 5 == 0){
            for (int i = 0; i < display.expectedBusList.size(); i += 1){
                ExpectedBus currentBus = display.expectedBusList.get(i);

                if (currentBus.status == BusStatus.delayed || currentBus.status == BusStatus.canceled){
                    currentBus.status = BusStatus.onTime;
                    currentBus.delay = 0;
                    break;
                }
            }
        }

    }

}
