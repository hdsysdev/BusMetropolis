package BusDisplay;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.String;
import java.time.LocalTime;
import java.util.ArrayList;


public class BusStopDisplayTest {
    //Defining byte array output stream to test for console printed messages
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void createWithBadPaths() {
        assertThrows(NullPointerException.class,
                () -> new BusStopDisplay().create("bad.path", "bad.path", "bad.path"));
    }

    @Test
    void createWithCorrectPaths()  {
        BusStopDisplay display = new BusStopDisplay()
                    .create("stop_info.csv",
                            "routes.csv",
                            "timetable.csv");
        assertNotNull(display);
    }

    //Setting output stream to variable made earlier to access messages printed to console
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void addScheduledToExpected() {
        BusStopDisplay display = new BusStopDisplay();
        display.callingRoutes = RoutesStopTimetableParser.parseRoutes("routes.csv", "timetable.csv");
        assertNotNull(display.callingRoutes);
        assertEquals(display.expectedBuses, new ArrayList<>());

        display.addScheduledToExpected();
        assertFalse(display.expectedBuses.isEmpty());
    }

    @Test
    public void getCallingRoutes() {
        BusStopDisplay display = new BusStopDisplay()
                .create("stop_info.csv",
                        "routes.csv",
                        "timetable.csv");

        assertEquals(display.getCallingRoutes(), display.callingRoutes);
    }

    @Test
    public void getDepartureTimes() {
        BusStopDisplay display = new BusStopDisplay()
                .create("stop_info.csv",
                        "routes.csv",
                        "timetable.csv");
        String[] route3Departures = {"08:11", "09:11", "10:11", "11:11", "12:11",
                "13:11", "14:11", "15:11", "16:11",
                "17:11", "18:11", "19:11", "20:11", "21:11"};
        String[] actualTimes = new String[14];
        for (int i = 0; i < display.getDepartureTimes(3).size(); i++){
            actualTimes[i] = display.getDepartureTimes(3).get(i).toString();
        }

        assertArrayEquals(actualTimes, route3Departures);
    }

    @Test
    public void getTimeOfNextBus() {
        BusStopDisplay display = new BusStopDisplay()
                .create("stop_info.csv",
                        "routes.csv",
                        "timetable.csv");

        assertEquals(display.getTimeOfNextBus(3, LocalTime.of(6, 0)), LocalTime.of(8, 11));
    }

    @Test
    public void testRemovePassedBus() {
        BusStopDisplay display = new BusStopDisplay()
                .create("stop_info.csv",
                        "routes.csv",
                        "timetable.csv");
        ExpectedBus firstBus = display.expectedBuses.get(0);
        assertTrue(display.expectedBuses.contains(firstBus));
        display.display(LocalTime.of(9, 10));
        assertFalse(display.expectedBuses.contains(firstBus));
    }
}
