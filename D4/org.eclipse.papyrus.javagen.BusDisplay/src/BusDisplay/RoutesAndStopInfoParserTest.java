package BusDisplay;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RoutesAndStopInfoParserTest extends RoutesAndStopInfoParser {
    
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
    void pasreRoutesTest() {
        try {
            ArrayList<Route> testRoutesParsed = RoutesAndStopInfoParser.parseRoutes("../routes_test.csv", "../timetable_test.csv");
            ArrayList<Route> testRoutes = new ArrayList<>(Arrays.asList(new Route(3,
                    "Vila Nova", "Centennial park",
                    new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8, 11),
                            LocalTime.of(9, 11),
                            LocalTime.of(10, 11),
                            LocalTime.of(11, 11),
                            LocalTime.of(12, 11))))));

            assertTrue(testRoutesParsed.equals(testRoutes));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void parseBusInfo() {
        Pair<String, String> testStopInfo;
        try{
            BufferedReader file = new BufferedReader(
                    new FileReader("../stop_info.csv"));

            String line = file.readLine();

            if(line == null) {
                file.close();
            } else {
                String[] parts = line.split(",");
                file.close();
                testStopInfo = new Pair<>(parts[0], parts[1]);

                assertEquals(testStopInfo, new Pair<>("BS05", "Sweetspot"));
            }
        } catch (IOException e){
            fail();
        }
    }
}
