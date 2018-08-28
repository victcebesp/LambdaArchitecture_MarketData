package org.eifer.box.ness;

import io.intino.konos.datalake.MessageHandler;
import io.intino.konos.datalake.ReflowDispatcher;
import io.intino.konos.datalake.ReflowConfiguration;
import io.intino.konos.jmx.JMXServer;
import io.intino.tara.magritte.Graph;
import io.intino.tara.magritte.RemounterGraph;
import io.intino.tara.magritte.stores.FileSystemStore;
import org.apache.commons.io.FileUtils;
import org.eifer.box.ness.TanksConnectors;
import org.eifer.box.MounterBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

import static org.slf4j.Logger.ROOT_LOGGER_NAME;

public class NessOperations implements NessOperationsMBean {
	private static Logger logger = LoggerFactory.getLogger(ROOT_LOGGER_NAME);
	private final MounterBox box;
	private final ReflowAssistant assistant;
	private int processed = 0;
	private io.intino.konos.datalake.Datalake.ReflowSession session;
	private Graph graph;

	public NessOperations(MounterBox box) {
		this.box = box;
		this.assistant = new org.eifer.box.ness.ReflowAssistant(box);
	}

	public java.util.List<String> help() {
		java.util.List<String> operations = new java.util.ArrayList<>();
		operations.add("boolean reflow():Starts reflow mode to reproduce events coming from datalake");
		operations.add("boolean reflow(String from):Starts reflow mode to reproduce events coming from datalake since the instant parameter");
		operations.add("boolean customReflow(String reflowConfiguration):Starts reflow mode to reproduce events coming from datalake since the instant parameter");
		return operations;
	}


	public boolean reflow() {
		return reflow(new ReflowConfiguration().blockSize(assistant.defaultBlockSize()).tankList(assistant.defaultTanks().stream().map(t -> new ReflowConfiguration.Tank().name(t.name()).from(Instant.MIN)).collect(java.util.stream.Collectors.toList())));
	}

	public boolean reflow(String from) {
		Instant instant = Instant.parse(from);
		return reflow(new ReflowConfiguration().blockSize(assistant.defaultBlockSize()).tankList(assistant.defaultTanks().stream().map(t -> new ReflowConfiguration.Tank().name(t.name()).from(instant)).collect(java.util.stream.Collectors.toList())));
	}

	public boolean customReflow(String reflowConfiguration) {
		return reflow(io.intino.konos.alexandria.Inl.fromMessage(io.intino.ness.inl.Message.load(reflowConfiguration), ReflowConfiguration.class));
	}

	private boolean reflow(ReflowConfiguration configuration) {
		logger.info("Starting Reflow...");
		assistant.before();
		TanksConnectors.unregister();
		this.session = box.datalake().reflow(configuration, new ReflowDispatcher(TanksConnectors.byName(configuration.tankList().stream().map(t -> t.name()).collect(java.util.stream.Collectors.toList())), onBlock(), onFinish()));
		final Graph graph = assistant.graph();
		try {
			if (graph.store() instanceof FileSystemStore && configuration.cleanStore())
				FileUtils.deleteDirectory(((FileSystemStore) graph.store()).directory());
			final RemounterGraph original = (RemounterGraph) new RemounterGraph(graph.store()).loadStashes(assistant.coreStashes());
			allowWriting(original, false);
			this.graph = original.realClone();
			box.put(this.graph);
			this.session.next();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private MessageHandler onFinish() {
		return m -> {
			doWrite();
			session.finish();
			final Graph graph = new Graph(NessOperations.this.graph.store());
			allowWriting(graph, true);
			box.put(graph.loadStashes(assistant.coreStashes()));
			TanksConnectors.registerTanks(box);
			assistant.after();
			logger.info("Reflow finished - " + m.get("count") + " messages processed");
		};
	}

	private MessageHandler onBlock() {
		return m -> {
			doWrite();
			logger.info("Block processed - " + m.get("count") + " messages processed");
			session.next();
		};
	}

	private void doWrite() {
		session.pause();
		allowWriting(graph, true);
		assistant.saveGraph(graph);
		this.graph = new RemounterGraph(graph.store()).loadStashes(assistant.coreStashes());
		allowWriting(graph, false);
		box.put(graph);
		session.play();
	}

	private void allowWriting(Graph original, boolean flag) {
		if (original.store() instanceof FileSystemStore) ((FileSystemStore) original.store()).allowWriting(flag);
	}


	public static JMXServer init(MounterBox box) {
		JMXServer server = new JMXServer(Collections.singletonMap("io.intino.cesar.box.ness.NessOperations", new Object[]{box}));
		server.init();
		return server;
	}
}