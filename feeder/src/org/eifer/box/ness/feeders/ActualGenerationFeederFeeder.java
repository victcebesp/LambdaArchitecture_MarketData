package org.eifer.box.ness.feeders;

import io.intino.konos.alexandria.Inl;
import org.eifer.box.FeederBox;
import org.eifer.box.schemas.ActualGeneration;
import org.eifer.market.directorywalker.ActualGenerationDirectoryWalker;
import org.eifer.market.generator.ActualGenerationGenerator;
import org.eifer.market.reader.ActualGenerationReader;

import java.io.IOException;
import java.util.List;

public class ActualGenerationFeederFeeder extends AbstractActualGenerationFeederFeeder {
	private final FeederBox box;

	public ActualGenerationFeederFeeder(FeederBox box) {
		this.box = box;
	}

	public void feedTank() throws IOException {

		new ActualGenerationDirectoryWalker().getFilePaths(box.configuration().get("directory-path")).forEach(p ->
				getEvents(p).forEach(e -> feed(Inl.toMessage(e))));

	}

	private List<ActualGeneration> getEvents(String path) {
		List<String> records = new ActualGenerationReader().readRecords(path);
		return new ActualGenerationGenerator().getAllActualGenerationEvents(records);
	}

}