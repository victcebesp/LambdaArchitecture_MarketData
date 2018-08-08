package org.eifer.box;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;


public abstract class AbstractBox extends io.intino.konos.alexandria.Box {
	private static Logger logger = LoggerFactory.getLogger(ROOT_LOGGER_NAME);
	protected MounterConfiguration configuration;
	private io.intino.konos.datalake.Ness ness;

	public AbstractBox(String[] args) {
		this(new MounterConfiguration(args));
	}

	public AbstractBox(MounterConfiguration configuration) {
		owner = new io.intino.sumus.box.SumusBox(configuration);
		this.configuration = configuration;
	}

	public MounterConfiguration configuration() {
		return (MounterConfiguration) configuration;
	}

	@Override
	public io.intino.konos.alexandria.Box put(Object o) {
		owner.put(o);
		return this;
	}

	public io.intino.konos.alexandria.Box open() {
		if(owner != null) owner.open();
		initLogger();
		initUI();
		initRESTServices();
		initJMXServices();
		initJMSServices();
		initDataLake();
		initTasks();
		initSlackBots();
		return this;
	}

	public void close() {
		if(owner != null) owner.close();


		if (ness != null) ness.disconnect();
	}




	public io.intino.konos.datalake.Ness datalake() {
		return this.ness;
	}

	public org.eifer.box.ness.TanksConnectors feeders() {
		return new org.eifer.box.ness.TanksConnectors();
	}

	public org.eifer.box.ness.TanksConnectors mounters() {
		return new org.eifer.box.ness.TanksConnectors();
	}

	public org.eifer.box.ness.TanksConnectors processes() {
		return new org.eifer.box.ness.TanksConnectors();
	}



	private void initRESTServices() {

	}

	private void initJMSServices() {


	}

	private void initJMXServices() {

	}

	private void initSlackBots() {

	}

	private void initUI() {

	}

	private void initDataLake() {
		this.ness = new io.intino.konos.datalake.Ness(configuration().get("ness-url"), configuration().get("ness-user"), configuration().get("ness-password"), "");
		java.lang.Thread thread = new java.lang.Thread(() -> {
			this.ness.connect("");
			org.eifer.box.ness.TanksConnectors.registerTanks((MounterBox) this);

			org.eifer.box.ness.NessOperations.init((MounterBox) this);
			logger.info("Ness connection: started!");
		}, "ness init");
		try {
			thread.start();
			thread.join(10000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	private void initTasks() {

	}

	private void initLogger() {
		final java.util.logging.Logger logger = java.util.logging.Logger.getGlobal();
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		handler.setFormatter(new io.intino.konos.alexandria.LogFormatter("log"));
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
	}

	private static java.net.URL url(String url) {
		try {
		return new java.net.URL(url);
		} catch (java.net.MalformedURLException e) {
			return null;
		}
	}
}