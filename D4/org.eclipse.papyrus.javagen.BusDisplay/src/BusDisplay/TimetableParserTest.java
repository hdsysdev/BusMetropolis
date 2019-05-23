package BusDisplay;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimetableParserTest extends RoutesAndStopInfoParser {

    @Test
    public void parseTimetable() throws IOException {
        ArrayList<LocalTime> testTimetable = new ArrayList<LocalTime>(){{
            add(LocalTime.parse("08:11"));
            add(LocalTime.parse("09:11"));
            add(LocalTime.parse("10:11"));
            add(LocalTime.parse("11:11"));
            add(LocalTime.parse("12:11"));
            add(LocalTime.parse("13:11"));
            add(LocalTime.parse("14:11"));
            add(LocalTime.parse("15:11"));
            add(LocalTime.parse("16:11"));
            add(LocalTime.parse("17:11"));
            add(LocalTime.parse("18:11"));
            add(LocalTime.parse("19:11"));
            add(LocalTime.parse("20:11"));
            add(LocalTime.parse("21:11"));
        }};

        ArrayList<LocalTime> parsedTimetable = TimetableParser.parseTimetable("3", "../timetable.csv");

        assertEquals(testTimetable, parsedTimetable);
    }
}