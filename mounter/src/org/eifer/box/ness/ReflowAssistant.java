package org.eifer.box.ness;

import com.google.gson.Gson;
import io.intino.konos.datalake.Datalake;
import io.intino.tara.magritte.Graph;
import org.eifer.box.MounterBox;
import org.eifer.graph.MounterGraph;
import projection.ReportExporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ReflowAssistant {

	private final MounterBox box;

	ReflowAssistant(MounterBox box) {
		this.box = box;
	}

	void before() {

	}

	int defaultBlockSize() {
		return Integer.MAX_VALUE;
	}

	List<Datalake.Tank> defaultTanks() {
	 	return java.util.Collections.emptyList(); //TODO
	}

	Graph graph() {
//		return null;
		return box.graph().core$();
	}

	String[] coreStashes() {
		return new String[]{"Model"};
	}

	void saveGraph(Graph graph) {
		graph.saveAll("Model");
	}

	void after() {

		String path = box.configuration().get("csv_store");
		String csvHeading = "price zone;date;from;to;type;day-ahead price;day-ahead volume;continuous weighted volume;continuous ID3\n";

		Map<String, StringBuilder> pricezoneRecordsMap = new HashMap<>();

		MounterBox.reports.forEach((key, value) -> {

			StringBuilder priceZoneCsvText = pricezoneRecordsMap.getOrDefault(value.priceZone(), new StringBuilder(csvHeading));
			priceZoneCsvText.append(ReportExporter.exportToCsv(value));
			pricezoneRecordsMap.put(value.priceZone(), priceZoneCsvText);
		});

		new File(path).mkdirs();

		pricezoneRecordsMap.forEach((key, value) -> {
			try {
				Files.write(Paths.get(path + "/" + key + "_view.csv"), value.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}


}