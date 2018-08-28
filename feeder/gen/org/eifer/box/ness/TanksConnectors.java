package org.eifer.box.ness;

import io.intino.konos.jms.TopicConsumer;
import io.intino.konos.jms.TopicProducer;
import io.intino.konos.datalake.Ness;
import io.intino.konos.jms.Consumer;
import io.intino.konos.jms.Bus;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.advisory.DestinationListener;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQTopic;
import org.eifer.box.FeederBox;
import org.eifer.box.FeederConfiguration;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TanksConnectors {
	private static io.intino.konos.datalake.Datalake.Tank actualGeneration;
	private static io.intino.konos.datalake.Datalake.Tank eexMasterDataUnit;
	private static io.intino.konos.datalake.Datalake.Tank intradayReport;
	private static io.intino.konos.datalake.Datalake.Tank dayAheadReport;

	public static void registerTanks(FeederBox box) {
		final String clientID = "";
		actualGeneration = box.datalake().add("market.actualgeneration");
		eexMasterDataUnit = box.datalake().add("market.eexmasterdataunit");
		intradayReport = box.datalake().add("market.intradayreport");
		dayAheadReport = box.datalake().add("market.dayaheadreport");

	}

	public static List<io.intino.konos.datalake.Datalake.Tank> all() {
		List<io.intino.konos.datalake.Datalake.Tank> tanks = new ArrayList<>();
		tanks.add(TanksConnectors.actualGeneration);
		tanks.add(TanksConnectors.eexMasterDataUnit);
		tanks.add(TanksConnectors.intradayReport);
		tanks.add(TanksConnectors.dayAheadReport);
		return tanks;
	}

	public static List<io.intino.konos.datalake.Datalake.Tank> byName(List<String> names) {
		List<io.intino.konos.datalake.Datalake.Tank> tanks = all();
		return tanks.stream().filter(t -> names.contains(t.name())).collect(java.util.stream.Collectors.toList());
	}

	public static io.intino.konos.datalake.Datalake.Tank actualGeneration() {
		return TanksConnectors.actualGeneration;
	}

	public static io.intino.konos.datalake.Datalake.Tank eexMasterDataUnit() {
		return TanksConnectors.eexMasterDataUnit;
	}

	public static io.intino.konos.datalake.Datalake.Tank intradayReport() {
		return TanksConnectors.intradayReport;
	}

	public static io.intino.konos.datalake.Datalake.Tank dayAheadReport() {
		return TanksConnectors.dayAheadReport;
	}



	public static void unregister() {
		actualGeneration.unregister();
		eexMasterDataUnit.unregister();
		intradayReport.unregister();
		dayAheadReport.unregister();
	}

	private static boolean isRegisterOnly(Message message) {
		try {
			return message.getBooleanProperty(io.intino.konos.datalake.Datalake.REGISTER_ONLY);
		} catch (JMSException e) {
			return false;
		}
	}
}