package BusDisplay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimetableParser {

    public static ArrayList<LocalTime> parseTimetable(String busNo, String filename) throws IOException{
        ArrayList<LocalTime> timetable = new ArrayList<>();
        BufferedReader file = new BufferedReader(new FileReader(filename));

        boolean endOf = false;

        while (!endOf) {
            String timeData = file.readLine();
            if(timeData == null) {
                file.close();
                endOf = true;
            } else {
                String[] parts = timeData.split(",");

                if(parts[0].equals(busNo)){
                    for(int i = 1; i < parts.length; i++){
                        if(parts[i] != null || !parts[i].equals("")){
                            timetable.add(LocalTime.parse(parts[i]));
                        } else {
                            endOf = true;
                        }
                    }
                }
            }
        }
        return timetable;
    }
}
