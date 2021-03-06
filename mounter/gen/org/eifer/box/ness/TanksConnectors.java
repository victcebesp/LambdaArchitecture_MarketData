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
	private static io.intino.konos.datalake.Datalake.Tank actualGeneration;
	private static io.intino.konos.datalake.Datalake.Tank dayAheadReport;
	private static io.intino.konos.datalake.Datalake.Tank eexMasterDataUnit;
	private static io.intino.konos.datalake.Datalake.Tank intradayReport;

	public static void registerTanks(MounterBox box) {
		final String clientID = "";
		actualGeneration = box.datalake().add("market.actualgeneration");
		dayAheadReport = box.datalake().add("market.dayaheadreport");
		eexMasterDataUnit = box.datalake().add("market.eexmasterdataunit");
		intradayReport = box.datalake().add("market.intradayreport");
		actualGeneration.handler(new ActualGenerationHandler(box)).flow(clientID != null ? clientID + "-actualgeneration" : null);
		dayAheadReport.handler(new DayAheadReportHandler(box)).flow(clientID != null ? clientID + "-dayaheadreport" : null);
		eexMasterDataUnit.handler(new EexMasterDataUnitHandler(box)).flow(clientID != null ? clientID + "-eexmasterdataunit" : null);
		intradayReport.handler(new IntradayReportHandler(box)).flow(clientID != null ? clientID + "-intradayreport" : null);
	}

	public static List<io.intino.konos.datalake.Datalake.Tank> all() {
		List<io.intino.konos.datalake.Datalake.Tank> tanks = new ArrayList<>();
		tanks.add(TanksConnectors.actualGeneration);
		tanks.add(TanksConnectors.dayAheadReport);
		tanks.add(TanksConnectors.eexMasterDataUnit);
		tanks.add(TanksConnectors.intradayReport);
		return tanks;
	}

	public static List<io.intino.konos.datalake.Datalake.Tank> byName(List<String> names) {
		return all().stream().filter(t -> names.contains(t.name())).collect(java.util.stream.Collectors.toList());
	}

	public static io.intino.konos.datalake.Datalake.Tank byName(String type) {
		return all().stream().filter(t -> type.equals(t.name())).findFirst().orElse(null);
	}

	public static io.intino.konos.datalake.Datalake.Tank actualGeneration() {
		return TanksConnectors.actualGeneration;
	}

	public static io.intino.konos.datalake.Datalake.Tank dayAheadReport() {
		return TanksConnectors.dayAheadReport;
	}

	public static io.intino.konos.datalake.Datalake.Tank eexMasterDataUnit() {
		return TanksConnectors.eexMasterDataUnit;
	}

	public static io.intino.konos.datalake.Datalake.Tank intradayReport() {
		return TanksConnectors.intradayReport;
	}

	public static class ActualGenerationHandler implements io.intino.konos.datalake.MessageHandler {
		private final MounterBox box;

		public ActualGenerationHandler(MounterBox box) {
			this.box = box;
		}

		public void handle(io.intino.ness.inl.Message m) {
			try {
				ActualGenerationMounter mounter = new ActualGenerationMounter();
				mounter.box = box;
				mounter.actualGeneration = io.intino.konos.alexandria.Inl.fromMessage(m, org.eifer.box.schemas.ActualGeneration.class);
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

	public static class EexMasterDataUnitHandler implements io.intino.konos.datalake.MessageHandler {
		private final MounterBox box;

		public EexMasterDataUnitHandler(MounterBox box) {
			this.box = box;
		}

		public void handle(io.intino.ness.inl.Message m) {
			try {
				EexMasterDataUnitMounter mounter = new EexMasterDataUnitMounter();
				mounter.box = box;
				mounter.eexMasterDataUnit = io.intino.konos.alexandria.Inl.fromMessage(m, org.eifer.box.schemas.EexMasterDataUnit.class);
				mounter.execute();
			} catch(Throwable e) {
				org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).error(e.getMessage(), e);
			}
		}
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

	public static void unregister() {
		actualGeneration.unregister();
		dayAheadReport.unregister();
		eexMasterDataUnit.unregister();
		intradayReport.unregister();
	}

	private static boolean isRegisterOnly(Message message) {
		try {
			return message.getBooleanProperty(io.intino.konos.datalake.Datalake.REGISTER_ONLY);
		} catch (JMSException e) {
			return false;
		}
	}
}