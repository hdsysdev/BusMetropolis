package BusDisplay;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RoutesAndStopInfoParserTest extends RoutesAndStopInfoParser {

    @Test
    void pasreRoutes() {
        try {
            ArrayList<LocalTime> testTimeList = new ArrayList<>();
            testTimeList.add(LocalTime.parse("08:11"));
            testTimeList.add(LocalTime.parse("09:11"));
            testTimeList.add(LocalTime.parse("10:11"));
            testTimeList.add(LocalTime.parse("11:11"));
            testTimeList.add(LocalTime.parse("12:11"));
            testTimeList.add(LocalTime.parse("13:11"));
            testTimeList.add(LocalTime.parse("14:11"));
            testTimeList.add(LocalTime.parse("15:11"));
            testTimeList.add(LocalTime.parse("16:11"));
            testTimeList.add(LocalTime.parse("17:11"));
            testTimeList.add(LocalTime.parse("18:11"));
            testTimeList.add(LocalTime.parse("19:11"));
            testTimeList.add(LocalTime.parse("20:11"));
            testTimeList.add(LocalTime.parse("21:11"));

            ArrayList<Route> testRoutesParsed = RoutesAndStopInfoParser.parseRouteList("../routes_test.csv");
            ArrayList<Route> testRoutes = new ArrayList<>(Arrays.asList(new Route(3,
                    "Vila Nova", "Centennial park", testTimeList)));

            assertEquals(testRoutesParsed.get(0).routeNo, testRoutes.get(0).routeNo);
            assertEquals(testRoutesParsed.get(0).origin, testRoutes.get(0).origin);
            assertEquals(testRoutesParsed.get(0).destination, testRoutes.get(0).destination);
            assertEquals(testRoutesParsed.get(0).schedule, testRoutes.get(0).schedule);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parseOneRoute() {
        String testRouteConfig = "3,Vila Nova,Centennial park";

        ArrayList<LocalTime> testTimeList = new ArrayList<>();
        testTimeList.add(LocalTime.parse("08:11"));
        testTimeList.add(LocalTime.parse("09:11"));
        testTimeList.add(LocalTime.parse("10:11"));
        testTimeList.add(LocalTime.parse("11:11"));
        testTimeList.add(LocalTime.parse("12:11"));
        testTimeList.add(LocalTime.parse("13:11"));
        testTimeList.add(LocalTime.parse("14:11"));
        testTimeList.add(LocalTime.parse("15:11"));
        testTimeList.add(LocalTime.parse("16:11"));
        testTimeList.add(LocalTime.parse("17:11"));
        testTimeList.add(LocalTime.parse("18:11"));
        testTimeList.add(LocalTime.parse("19:11"));
        testTimeList.add(LocalTime.parse("20:11"));
        testTimeList.add(LocalTime.parse("21:11"));


        Route testRoute = new Route(3, "Vila Nova", "Centennial park", testTimeList);

        Route parsedRoute = null;
        try {
            parsedRoute = RoutesAndStopInfoParser.parseRoute(testRouteConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(parsedRoute.routeNo, testRoute.routeNo);
        assertEquals(parsedRoute.origin, testRoute.origin);
        assertEquals(parsedRoute.destination, testRoute.destination);
        assertEquals(parsedRoute.schedule, testRoute.schedule);
    }

}
