package org.eifer.box.ness.mounters;

import org.eifer.box.MounterBox;
import projection.Report;

import java.time.Instant;

public class IntradayReportMounter {
    public MounterBox box;
    public org.eifer.box.schemas.IntradayReport intradayReport;

    public void execute() {

        String id = getDateAndHour(intradayReport.ts()) + intradayReport.priceZone();

        Report report = getReport(id);
        report.updateIntradayFields(intradayReport);
        MounterBox.reports.put(id, report);

    }

    private String getDateAndHour(Instant instant) {
        return instant.toString().substring(0, instant.toString().indexOf(":"));
    }

    private Report getReport(String id) {
        Report report = MounterBox.reports.get(id);
        if (report == null) report = new Report(intradayReport.ts(), intradayReport.priceZone());
        return report;
    }

}