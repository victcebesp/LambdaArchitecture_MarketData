package org.eifer.box.ness.feeders;

import org.eifer.box.ness.TanksConnectors;
import io.intino.konos.datalake.Feeder;
import io.intino.konos.datalake.Sensor;
import io.intino.ness.inl.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class AbstractActualGenerationFeeder extends io.intino.konos.datalake.Feeder {
	protected List<Sensor> sensors;

	public AbstractActualGenerationFeeder() {
		this.sensors = Arrays.asList();
	}

	public List<String> eventTypes() {
		return Arrays.asList();
	}


	public void feed(Message event) {
		TanksConnectors.byName("market." + event.type().toLowerCase()).feed(event);
	}

	public List<Sensor> sensors() {
		return sensors;
	}


}