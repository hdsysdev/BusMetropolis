package BusDisplay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;


public class RoutesAndStopInfoParser {

    public static String[] parseBusStop(String name) throws IOException {
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

    public static Route parseRoute(String route_info) throws IOException {
        String[] parts = route_info.split(",");

        ArrayList<LocalTime> timetable = TimetableParser.parseTimetable(parts[0], "../timetable.csv");
        Route parsedRoute = new Route(Integer.parseInt(parts[0]), parts[1], parts[2], timetable);

        return parsedRoute;
    }

    public static ArrayList<Route> parseRouteList(String file) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        boolean endOf = false;
        ArrayList<Route> routeList = new ArrayList<>();


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
