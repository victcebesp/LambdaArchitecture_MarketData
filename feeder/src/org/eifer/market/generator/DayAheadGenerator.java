package org.eifer.market.generator;

import org.eifer.box.schemas.DayAheadReport;

import java.util.List;

public interface DayAheadGenerator {

    List<DayAheadReport> generateAllDayAheadReportEvents(List<String> records);
}
