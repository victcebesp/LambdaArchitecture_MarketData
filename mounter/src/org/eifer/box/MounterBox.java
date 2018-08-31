package org.eifer.box;

import io.intino.tara.magritte.Graph;
import org.eifer.box.schemas.ActualGenerationPoint;
import org.eifer.graph.MounterGraph;
import projection.Report;

import java.util.LinkedHashMap;
import java.util.Map;

public class MounterBox extends AbstractBox {

	public static Map<String, Report> reports = new LinkedHashMap<>();
	private MounterGraph graph;

	public MounterBox(String[] args) {
		super(args);
	}

	public MounterBox(MounterConfiguration configuration) {
		super(configuration);
	}

	@Override
	public io.intino.konos.alexandria.Box put(Object o) {
		if (o instanceof Graph) this.graph = ((Graph) o).as(MounterGraph.class);
		super.put(o);
		return this;
	}

	public io.intino.konos.alexandria.Box open() {
		return super.open();
	}

	public void close() {
		super.close();
	}

	public MounterGraph graph() {
		return graph;
	}

}