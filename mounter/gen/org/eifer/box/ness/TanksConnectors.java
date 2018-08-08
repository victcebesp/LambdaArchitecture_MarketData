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
import org.eifer.box.MounterBox;
import org.eifer.box.MounterConfiguration;
import org.eifer.box.ness.mounters.*;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TanksConnectors {
	private static io.intino.konos.datalake.Datalake.Tank intradayReport;
	private static io.intino.konos.datalake.Datalake.Tank dayAheadReport;

	public static void registerTanks(MounterBox box) {
		final String clientID = "";
		intradayReport = box.datalake().add("market.intradayreport");
		dayAheadReport = box.datalake().add("market.dayaheadreport");
		intradayReport.handler(new IntradayReportHandler(box));
		intradayReport.flow(clientID != null ? clientID + "-intradayreport" : null);
		dayAheadReport.handler(new DayAheadReportHandler(box));
		dayAheadReport.flow(clientID != null ? clientID + "-dayaheadreport" : null);
	}

	public static List<io.intino.konos.datalake.Datalake.Tank> all() {
		List<io.intino.konos.datalake.Datalake.Tank> tanks = new ArrayList<>();
		tanks.add(TanksConnectors.intradayReport);
		tanks.add(TanksConnectors.dayAheadReport);
		return tanks;
	}

	public static List<io.intino.konos.datalake.Datalake.Tank> byName(List<String> names) {
		List<io.intino.konos.datalake.Datalake.Tank> tanks = all();
		return tanks.stream().filter(t -> names.contains(t.name())).collect(java.util.stream.Collectors.toList());
	}

	public static io.intino.konos.datalake.Datalake.Tank intradayReport() {
		return TanksConnectors.intradayReport;
	}

	public static io.intino.konos.datalake.Datalake.Tank dayAheadReport() {
		return TanksConnectors.dayAheadReport;
	}

	public static class IntradayReportHandler implements io.intino.konos.datalake.MessageHandler {
		private final MounterBox box;

		public IntradayReportHandler(MounterBox box) {
			this.box = box;
		}

		public void handle(io.intino.ness.inl.Message m) {
			try {
				IntradayReportMounter mounter = new IntradayReportMounter();
				mounter.box = box;
				mounter.intradayReport = io.intino.konos.alexandria.Inl.fromMessage(m, org.eifer.box.schemas.IntradayReport.class);
				mounter.execute();
			} catch(Throwable e) {
				org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).error(e.getMessage(), e);
			}
		}
	}

	public static class DayAheadReportHandler implements io.intino.konos.datalake.MessageHandler {
		private final MounterBox box;

		public DayAheadReportHandler(MounterBox box) {
			this.box = box;
		}

		public void handle(io.intino.ness.inl.Message m) {
			try {
				DayAheadReportMounter mounter = new DayAheadReportMounter();
				mounter.box = box;
				mounter.dayAheadReport = io.intino.konos.alexandria.Inl.fromMessage(m, org.eifer.box.schemas.DayAheadReport.class);
				mounter.execute();
			} catch(Throwable e) {
				org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).error(e.getMessage(), e);
			}
		}
	}

	public static void unregister() {
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