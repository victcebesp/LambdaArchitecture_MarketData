package org.eifer.box.ness.mounters;

import org.eifer.box.MounterBox;
import projection.Report;

import java.time.Instant;
import java.util.Optional;

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
		Optional<Report> report = Optional.ofNullable(MounterBox.reports.get(id));
		return report.orElse(new Report(dayAheadReport.ts(), dayAheadReport.priceZone()));
	}

	private String getDateAndHour(Instant instant) {
		return instant.toString().substring(0, instant.toString().indexOf(':'));
	}
}