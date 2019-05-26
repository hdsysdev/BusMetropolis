package BusDisplay;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;


public class RoutesStopTimetableParser {
    //ArrayList going to contain many route objects
    private ArrayList<Route> routes = new ArrayList<>();


    //Function to parse single route object out of a route
    static Route parseRoute(String route_info, String timetable_info){
        String[] parts = route_info.split(",");
        String[] timetableParts = timetable_info.split(",");

        ArrayList<LocalTime> schedule = new ArrayList<>();

        //Start after bus route number
        for(int i = 1; i < timetableParts.length; i++){
            schedule.add(LocalTime.parse(timetableParts[i]));
        }

        return new Route(Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                schedule);
    }


    //Going to parse an array list of routes from a given file
    public static ArrayList<Route> parseRoutes (String filename, String ttFilename)  {

        ArrayList<Route> routes = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(
                    new FileReader(filename));
            BufferedReader timetableFile = new BufferedReader(
                    new FileReader(ttFilename));

            //Skip first line
            file.readLine();
            while (true) {
                String route_data = file.readLine();
                String line = timetableFile.readLine();
                String timetable_data = "";

                if(route_data == null) {
                    file.close();
                    break;
                } else {

                    if (line.split(",")[0].equals(route_data.split(",")[0])){
                        timetable_data = line;
                    } else {
                        //If lines in timetable file don't line up with the order in routes.csv, the right row will be found and used
                        BufferedReader tempTimetableFile = new BufferedReader(new FileReader(ttFilename));

                        for (String timetableLine = tempTimetableFile.readLine(); timetableLine != null; timetableLine = tempTimetableFile.readLine()){
                            if (timetableLine.split(",")[0].equals(route_data.split(",")[0])){
                                timetable_data = timetableLine;
                                break;
                            }
                        }
                    }
                    routes.add(parseRoute(route_data, timetable_data));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return routes;
    }

    //Parses a pair containing a bus stop id and stop name, takes a filename string
    public static String[] parseStopInfo(String filename) {
        try {
            BufferedReader file = new BufferedReader(
                    new FileReader(filename));

            String line = file.readLine();

            if(line == null) {
                file.close();
                return null;
            } else {
                String[] parts = line.split(",");
                file.close();
                return parts;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
