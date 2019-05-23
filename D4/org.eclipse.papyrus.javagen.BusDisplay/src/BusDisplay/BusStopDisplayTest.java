package BusDisplay;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.*;

public class BusStopDisplayTest {

    @Test
    public void addToExpectedTest() throws IOException {
        BusStopDisplay display = new BusStopDisplay();
        display.routeList = RoutesAndStopInfoParser.parseRouteList("../routes.csv");
        display.addScheduledToExpected();

        assertFalse(display.expectedBusList.isEmpty());
    }

    @Test
    public void pathTest() throws IOException {
        BusStopDisplay busStopDisplay = new BusStopDisplay().create("..\\stop_info.csv", "..\\routes.csv");
        assertNotEquals(busStopDisplay, null);
    }

    @Test(expected = FileNotFoundException.class)
    public void emptyPath() throws IOException {
        new BusStopDisplay().create("", "");
    }
}

