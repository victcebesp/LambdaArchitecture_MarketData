package org.eifer.box.ness.feeders;

import org.eifer.box.FeederBox;

import io.intino.ness.inl.Message;
import java.util.Arrays;

public class DayAheadReportFeeder extends AbstractDayAheadReportFeeder {
	private final FeederBox box;

	public DayAheadReportFeeder(FeederBox box) {
		this.box = box;
	}


}