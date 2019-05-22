package BusDisplay;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class BusInfoSim {
    //Changed
    public static void main(String[] args) throws IOException, InterruptedException {
        BusStopDisplay display = null;

        display = new BusStopDisplay().create("..\\routes.csv", "..\\stop_info.csv");

        //Demo loop
        while (true){
            display.disp(LocalTime.now());
            simulate(display);
            TimeUnit.SECONDS.sleep(15);
        }
    }

    public static void simulate(BusStopDisplay display){
        //Print display array like it would be shown on the display screen at the stop
        for(int i = 0; i < display.display.length; i += 1){
            String[] row = display.display[i];
            System.out.print("\n");
            for (int x = 0; x < row.length; x += 1){
                System.out.print(row[x] + " ");
            }
        }

    }

}
