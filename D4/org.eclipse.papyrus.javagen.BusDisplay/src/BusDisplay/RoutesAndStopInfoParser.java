package BusDisplay;

import java.time.LocalTime;
import java.util.ArrayList;

public class RoutesAndStopInfoParser {
    ArrayList<Route> routes = new ArrayList<>();
    private static Route parseRoute(String route_info){
        String[] parts = route_info.split(",");
        Route route = new Route(Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                LocalTime.parse(parts[3]));

        for (int i = 4; i < parts.length; i++) {
            Route a = new Route(Integer.parseInt(parts[i]));
            b.addAuthor(a);
        }
    }

    public RoutesAndStopInfoParser() {

    }
}
