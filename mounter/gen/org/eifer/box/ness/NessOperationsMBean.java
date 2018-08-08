package org.eifer.box.ness;

import io.intino.konos.jmx.Description;
import io.intino.konos.jmx.Parameters;

public interface NessOperationsMBean {
	@Description("Shows information about the available operations")
	@Parameters({})
	java.util.List<String> help();

	@Description("Starts reflow mode to reproduce events coming from datalake")
	@Parameters({})
	boolean reflow();

	@Description("Starts reflow mode to reproduce events coming from datalake since instant parameter")
	@Parameters({})
	boolean reflow(String fromInstant);

	@Description("Starts reflow mode to reproduce events coming from datalake based on reflow configuration. This configuration is coded on INL with io.intino.konos.datalake.ReflowConfiguration schema")
	@Parameters({})
	boolean customReflow(String reflowConfiguration);
}