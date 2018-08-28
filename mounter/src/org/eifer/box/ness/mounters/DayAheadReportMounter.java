package org.eifer.box.ness.mounters;

import org.eifer.box.MounterBox;
import projection.Row;
import projection.Report;

import java.time.Instant;

public class DayAheadReportMounter {
	public MounterBox box;
	public org.eifer.box.schemas.DayAheadReport dayAheadReport;

	public void execute() {

		String id = getDateAndHour(dayAheadReport.ts()) + dayAheadReport.priceZone();

		Report report = getReport(id);
		report.updateDayAheadFields(dayAheadReport);
		MounterBox.reports.put(id, report);

	}

	private Report getReport(String id) {
		Report report = MounterBox.reports.get(id);
		if (report == null) report = new Report(dayAheadReport.ts(), dayAheadReport.priceZone());
		return report;
	}

	private String getDateAndHour(Instant instant) {
		return instant.toString().substring(0, instant.toString().indexOf(':'));
	}
}