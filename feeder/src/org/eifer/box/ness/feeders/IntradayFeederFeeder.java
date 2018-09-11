package org.eifer.box.ness.feeders;

import io.intino.konos.alexandria.Inl;
import org.eifer.box.FeederBox;
import org.eifer.box.ness.TanksConnectors;
import org.eifer.box.schemas.IntradayReport;
import org.eifer.market.directorywalker.IntradayDirectoryWalker;
import org.eifer.market.generator.IntradayReportGenerator;
import org.eifer.market.reader.IntradayReader;

import java.io.IOException;
import java.util.List;

public class IntradayFeederFeeder extends AbstractIntradayFeederFeeder {
	private final FeederBox box;

	public IntradayFeederFeeder(FeederBox box) {
		this.box = box;
	}

	public void feedTank() throws IOException {
		new IntradayDirectoryWalker().getFilePaths(box.configuration().get("directory-path")).forEach(p ->
				getEvents(p).forEach(e -> TanksConnectors.intradayReport().feed(Inl.toMessage(e))));
	}

	private List<IntradayReport> getEvents(String path) {
		List<String> records = new IntradayReader().readRecords(path);
		return new IntradayReportGenerator().generateAllIntradayReportEvents(records, getPrizeZone(path));
	}

	private String getPrizeZone(String path) {

		String pricezone = ""; //DEAT

		if(path.contains("germany")) pricezone += "DE-";
		if(path.contains("austria")) pricezone += "AT-";
		if(path.contains("switzerland")) pricezone += "CH-";
		else pricezone += "FR-";

		return pricezone.substring(0, pricezone.length() - 1);
	}

}