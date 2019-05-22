package BusDisplay;

import javafx.util.Pair;
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
            fail();
        }

        assertEquals();
        assertEquals(parsedRoute.schedule, testRoute.schedule);
    }

    @Test
    void pasreRoutesTest() {
        try {
            ArrayList<Route> testRoutesParsed = RoutesAndStopInfoParser.parseRouteList("../routes_test.csv");
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
