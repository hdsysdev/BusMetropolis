package BusDisplay;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;

public class RoutesAndStopInfoParser {
    //ArrayList going to contain many route objects
    private ArrayList<Route> routes = new ArrayList<>();

    //Function to parse single route object out of a route
    private static Route parseRoute(String route_info){
        String[] parts = route_info.split(",");

        return new Route(Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                LocalTime.parse(parts[3]));
    }


    //Going to parse an array list of routes from a given file
    public static ArrayList<Route> parseRoutes (String filename) throws IOException {
        BufferedReader file = new BufferedReader(
                new FileReader(filename));

        boolean endOf = false;
        ArrayList<Route> routes = new ArrayList<>();

        //Loop reads each line of the file one by one, checking if the next line of the file is not empty
        //which would mean the end of the file, if the end is reached the file is closed and loop is escaped
        //by closing the file and setting endOf to true, if the next line is not empty, route object r is parsed from
        //the line string before being added to the array and the array being returned at the end
        while (!endOf) {
            String route_data = file.readLine();
            if(route_data == null) {
                file.close();
                endOf = true;
            } else {
                Route r = parseRoute(route_data);
                routes.add(r);
            }
        }
        return routes;
    }

    //Parses a pair containing a bus stop id and stop name, takes a filename string
    public static Pair<String, String> parseBusInfo(String filename) throws IOException {
        BufferedReader file = new BufferedReader(
                new FileReader(filename));

        String line = file.readLine();

        if(line == null) {
            file.close();
            return null;
        } else {
            String[] parts = line.split(",");
            file.close();
            return new Pair<>(parts[0], parts[1]);
        }

    }
}
