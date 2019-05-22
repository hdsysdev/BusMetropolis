package BusDisplay;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RoutesAndStopInfoParserTest  {

    @Test
    void parseRouteTest() {
        String routeInfo = "21,Test Road,Other Test Road";
        String timetableInfo = "3,08:11,09:11,10:11";


        Route parsedRoute = RoutesAndStopInfoParser.parseRoute(routeInfo, timetableInfo);
        Route testRoute = new Route(21, "Test Road", "Other Test Road",
                new ArrayList<LocalTime>(
                        Arrays.asList(LocalTime.of(8, 11),
                                LocalTime.of(9, 11),
                                LocalTime.of(10, 11))));

        assertTrue(parsedRoute.equals(testRoute));
    }

    @Test
    void parseRoutesAndTimetableTest() {
        ArrayList<Route> testRoutesParsed = RoutesAndStopInfoParser.parseRoutes("routes_test.csv", "timetable_test.csv");
        ArrayList<Route> testRoutes = new ArrayList<>(Arrays.asList(new Route(3,
                "Vila Nova", "Centennial park",
                new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8, 11),
                        LocalTime.of(9, 11),
                        LocalTime.of(10, 11),
                        LocalTime.of(11, 11),
                        LocalTime.of(12, 11))))));

        assertTrue(testRoutesParsed.equals(testRoutes));

    }

    @Test
    void parseBusInfo() {
        String[] testStopInfo = RoutesAndStopInfoParser.parseStopInfo("stop_info.csv");
        String[] testStop = {"BS05", "Sweetspot"};
        assertTrue(Arrays.equals(testStopInfo, testStop));

    }
}
