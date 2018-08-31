package org.eifer.box.ness.feeders;

import org.eifer.box.FeederBox;

import io.intino.ness.inl.Message;
import java.util.Arrays;

public class ActualGenerationFeeder extends AbstractActualGenerationFeeder {
	private final FeederBox box;

	public ActualGenerationFeeder(FeederBox box) {
		this.box = box;
	}


}