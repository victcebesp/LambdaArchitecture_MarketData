import org.eifer.box.schemas.DayAheadReport;
import org.eifer.market.generators.HorizontalDayAheadReportGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DayAheadReportGeneratorShould {

    @Test
    public void returnOclockHoursGivenTimeIntervalInRecords(){

        List<String> records = new LinkedList<>(Arrays.asList("ST;22-03-2018;2017-12-31T13:21:32+01:00",
                "PR;CH;EUR/MWh;23-24;32,98;2110,7",
                "PR;DE-AT;EUR/MWh;23-24;18,96;27839,8",
                "PR;FR;EUR/MWh;23-24;16,35;18764,9)"));

        HorizontalDayAheadReportGenerator dayAheadReportGenerator = new HorizontalDayAheadReportGenerator();
        List<DayAheadReport> intradayReports = dayAheadReportGenerator.generateAllDayAheadReportEvents(records);

        assertEquals("2018-03-22T23:00:00Z", intradayReports.get(0).ts().toString());
        assertEquals("2018-03-22T23:00:00Z", intradayReports.get(1).ts().toString());
        assertEquals("2018-03-22T23:00:00Z", intradayReports.get(2).ts().toString());
    }

}
