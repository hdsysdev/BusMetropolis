package BusDisplay;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class BusInfoSim {

    public static void main(String[] args) {
        try {
            System.out.println(new File("").getAbsolutePath());
            BusStopDisplay display = new BusStopDisplay()
                    .create("..\\stop_info.csv",
                            "..\\routes.csv",
                            "..\\timetable.csv");
            //Set display's time 
            display.display(LocalTime.of(8, 10));

            for (String[] row: display.display){
                for (String cell: row){
                    System.out.print(cell + " | ");
                }
                System.out.println();
            }


        } catch (IOException e) {
            System.out.println("One or more of the entered file names is incorrect.");
        }

    }
}
