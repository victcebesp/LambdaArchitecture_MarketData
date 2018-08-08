package org.eifer.graph;

import io.intino.tara.magritte.Graph;

public class MounterGraph extends org.eifer.graph.AbstractGraph {

	public MounterGraph(Graph graph) {
		super(graph);
	}

	public MounterGraph(io.intino.tara.magritte.Graph graph, MounterGraph wrapper) {
	    super(graph, wrapper);
	}
}