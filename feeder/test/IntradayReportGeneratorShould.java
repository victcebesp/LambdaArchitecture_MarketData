import org.eifer.box.schemas.IntradayReport;
import org.eifer.market.generators.IntradayReportGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntradayReportGeneratorShould {

    @Test
    public void returnEmptyID3GiveEmptyID3InRecord(){

        List<String> records = Collections.singletonList("22/03/2018,10qh3,10qh3,0.2,0.2,46.80,46.80,46.80,21/03/2018 16:31:00,,,,46.80\n");

        IntradayReportGenerator intradayReportGenerator = new IntradayReportGenerator();
        List<IntradayReport> intradayReports = intradayReportGenerator.generateAllIntradayReportEvents(records, "DE-AT");

        assertEquals(" €", intradayReports.get(0).id3());
    }

}
