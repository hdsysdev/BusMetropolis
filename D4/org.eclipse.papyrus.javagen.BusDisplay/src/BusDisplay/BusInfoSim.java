package BusDisplay;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BusInfoSim {
    private static Integer loopNumber = 0;
    static Random rand = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        BusStopDisplay display = new BusStopDisplay().create("..\\routes.csv", "..\\stop_info.csv");
        System.out.println("Number Route No    Destination    Due at    Status");

        while (true){
            display.display(LocalTime.now());
            loopNumber += 1;
            simulate(display);
            TimeUnit.SECONDS.sleep(15);
        }
    }

    public static void simulate(BusStopDisplay display){
        ArrayList<ExpectedBus> displayedBuses = new ArrayList<>();

        for (int i = 0; i < 10; i += 1){
            displayedBuses.add(display.expectedBusList.get(i));
        }

        ExpectedBus randomBus = displayedBuses.get(rand.nextInt(displayedBuses.size()));
        Integer delayedOrCanceled = 0;


        for(int i = 0; i < displayedBuses.size(); i += 1){
            ExpectedBus currentBus = displayedBuses.get(i);
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
        else if (delayedOrCanceled > 4){
            for (int i = 0; i < displayedBuses.size(); i += 1){
                ExpectedBus currentBus = displayedBuses.get(i);

                if (currentBus.status == BusStatus.delayed || currentBus.status == BusStatus.canceled){
                    currentBus.status = BusStatus.onTime;
                    currentBus.delay = 0;
                    break;
                }
            }
        }

    }

}
