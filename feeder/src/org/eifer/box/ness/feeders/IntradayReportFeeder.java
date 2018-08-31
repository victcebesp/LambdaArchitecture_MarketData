package org.eifer.box.ness.feeders;

import org.eifer.box.FeederBox;

import io.intino.ness.inl.Message;
import java.util.Arrays;

public class IntradayReportFeeder extends AbstractIntradayReportFeeder {
	private final FeederBox box;

	public IntradayReportFeeder(FeederBox box) {
		this.box = box;
	}


}