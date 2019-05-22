package BusDisplay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;


public class RoutesAndStopInfoParser {
    //Parses a 2 item array containing a bus stop busId and stop name, takes a filename string
    public static String[] parseBusStopInfo(String name) throws IOException {
        String[] parsedStop = new String[2];

        BufferedReader stopfile = new BufferedReader(new FileReader(name));

        String currentRow = stopfile.readLine();

        if(currentRow == null) {
            return null;
        } else {
            String[] splitRow = currentRow.split(",");

            parsedStop[0] = splitRow[0];
            parsedStop[1] = splitRow[1];

            return parsedStop;
        }
    }

    //Function to parse single route object out of a route
    //Changed
    public static Route parseRoute(String route_info) throws IOException {
        String[] parts = route_info.split(",");

        ArrayList<LocalTime> timetable = TimetableParser.parseTimetable(parts[0], "../timetable.csv");
        Route parsedRoute = new Route(Integer.parseInt(parts[0]), parts[1], parts[2], timetable);

        return parsedRoute;
    }

    //Going to parse an array list of routes from a given file
    //Changed
    public static ArrayList<Route> parseRouteList(String file) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        boolean endOf = false;
        ArrayList<Route> routeList = new ArrayList<>();

        //Loop reads each line of the file one by one, checking if the next line of the file is not empty
        //which would mean the end of the file, if the end is reached the file is closed and loop is escaped
        //by closing the file and setting endOf to true, if the next line is not empty, route object r is parsed from
        //the line string before being added to the array and the array being returned at the end

        //Skip first line
        fileReader.readLine();
        while (endOf == false) {
            String routeRow = fileReader.readLine();
            if(routeRow != null) {
                Route r = parseRoute(routeRow);
                routeList.add(r);
            } else {
                endOf = true;
            }
        }
        return routeList;
    }

}
