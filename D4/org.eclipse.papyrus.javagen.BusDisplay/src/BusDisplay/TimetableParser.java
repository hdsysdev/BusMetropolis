package BusDisplay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimetableParser {

    //Function to parse Time array from file list
    private static ArrayList<LocalTime> parseTimetable(String busNo, String time_info, String filename) throws IOException{
        ArrayList<LocalTime> timetable = new ArrayList<>();
        BufferedReader file = new BufferedReader(
                new FileReader(filename));

        boolean endOf = false;

        while (!endOf) {
            String timeData = file.readLine();
            if(timeData == null) {
                file.close();
                endOf = true;
            } else {
                String[] parts = timeData.split(",");

                //If next file line is not null, check if it's the correct bus number
                if(parts[0].equals(busNo)){
                    for(int i = 0; i <= parts.length; i++){
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