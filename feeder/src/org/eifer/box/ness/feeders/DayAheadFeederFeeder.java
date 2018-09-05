package org.eifer.box.ness.feeders;

import io.intino.konos.alexandria.Inl;
import org.eifer.box.FeederBox;
import org.eifer.box.schemas.DayAheadReport;
import org.eifer.market.directorywalker.HorizontalDayAheadDirectoryWalker;
import org.eifer.market.directorywalker.VerticalDayAheadDirectoryWalker;
import org.eifer.market.generator.HorizontalDayAheadReportGenerator;
import org.eifer.market.generator.VerticalDayAheadReportGenerator;
import org.eifer.market.reader.DayAheadReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayAheadFeederFeeder extends AbstractDayAheadFeederFeeder {
	private final FeederBox box;

	public DayAheadFeederFeeder(FeederBox box) {
		this.box = box;
	}

	public void feedTank() throws IOException {

		List<DayAheadReport> events = new ArrayList<>();
		events.addAll(getVerticalDayAheadReportEvents());
		events.addAll(getHorizontalDayAheadReportEvents());

		events.forEach(e -> feed(Inl.toMessage(e)));

	}

	private List<DayAheadReport> getVerticalDayAheadReportEvents() throws IOException {

		List<String> dayAheadFilePaths = new VerticalDayAheadDirectoryWalker().getFilePaths(box.configuration().get("directory-path"));
		List<DayAheadReport> events = new ArrayList<>();

		dayAheadFilePaths.forEach(p -> {
			List<String> records = new DayAheadReader().readRecords(p);
			events.addAll(new VerticalDayAheadReportGenerator().generateAllDayAheadReportEvents(records));
		});

		return events;

	}

	private List<DayAheadReport> getHorizontalDayAheadReportEvents() throws IOException {

		List<String> dayAheadFilePaths = new HorizontalDayAheadDirectoryWalker().getFilePaths(box.configuration().get("directory-path"));
		List<DayAheadReport> events = new ArrayList<>();

		dayAheadFilePaths.forEach(p -> {
			List<String> records = new DayAheadReader().readRecords(p);
			events.addAll(new HorizontalDayAheadReportGenerator().generateAllDayAheadReportEvents(records));
		});

		return events;

	}


}