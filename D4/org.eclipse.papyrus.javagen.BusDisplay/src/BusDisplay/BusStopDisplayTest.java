package BusDisplay;



import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;


public class BusStopDisplayTest {

    @Test
    public void addToExpectedTest() throws IOException {
        BusStopDisplay display = new BusStopDisplay();
        display.routeList = RoutesAndStopInfoParser.parseRouteList("../routes.csv");
        display.addScheduledToExpected();

        assertFalse(display.expectedBusList.isEmpty());
    }

    @Test(expected = FileNotFoundException.class)
    public void emptyPathTest() throws IOException {
        BusStopDisplay busStopDisplay = new BusStopDisplay().create("", "");
    }

    @Test
    public void rightPathTest() throws IOException {
        BusStopDisplay busStopDisplay = new BusStopDisplay().create("..\\stop_info.csv", "..\\routes.csv");
        assertNotEquals(busStopDisplay, null);
    }
}
