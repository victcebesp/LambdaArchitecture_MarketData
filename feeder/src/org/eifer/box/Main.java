package org.eifer.box;

import io.intino.ness.datalake.Scale;
import io.intino.ness.datalake.graph.DatalakeGraph;
import io.intino.ness.datalake.graph.Tank;
import io.intino.tara.magritte.Graph;
import org.eifer.market.feeder.DayAheadFeeder;
import org.eifer.market.feeder.IntradayFeeder;
import org.eifer.market.feeder.MasterDataFeeder;
import org.eifer.market.reader.MasterDataReader;
import org.eifer.service.DayAheadSftpClient;
import org.eifer.service.IntradaySftpClient;
import org.eifer.service.MasterDataSftpClient;
import org.eifer.service.SftpClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		FeederBox box = new FeederBox(args);
		box.open();

        List<String> epexAccountData = Files.readAllLines(Paths.get("./epex_account_data.txt"));
        String server = epexAccountData.get(0);
        String username = epexAccountData.get(1);
        String password = epexAccountData.get(2);

		//SftpClient dayAheadSftpClient = new DayAheadSftpClient("/eod/market_data/power/spot/csv/")
		//		.connect(server, username, password)
		//		.retrieveFilesBetween(2000, 2018);
//
		//SftpClient intradaySftpClient = new IntradaySftpClient("/eod/market_data/power/spot/csv/")
		//		.connect(server, username, password)
		//		.retrieveFilesBetween(2000, 2018);

		//SftpClient masterDataSftpClient = new MasterDataSftpClient("/eod/transparency_data/power/csv/masterdata_power/")
		//		.connect(server, username, password)
		//		.retrieveFilesBetween(2009, 2013);


		String directoryPath = box.configuration().get("directory_path");
		//new IntradayFeeder(directoryPath).feedTank();
		//new DayAheadFeeder(directoryPath).feedTank();
		new MasterDataFeeder(directoryPath).feedTank();


		//intradaySftpClient.closeConnection();
		//dayAheadSftpClient.closeConnection();
//		masterDataSftpClient.closeConnection();

		//compressDatalake();

		box.close();

		Runtime.getRuntime().addShutdownHook(new Thread(box::close));
	}

	private static void compressDatalake() {
		DatalakeGraph datalake = new Graph().loadStashes("Datalake").as(DatalakeGraph.class);
		datalake.directory(new File("C:\\Users\\ceballos\\IdeaProjects\\EnergyMarket\\tmp\\ness\\datalake"));
		datalake.scale(Scale.Day);
		Tank intradayTank = datalake.add("market.intradayreport");
		intradayTank.sort();
		intradayTank.seal();

		Tank dayAheadTank = datalake.add("market.dayaheadreport");
		dayAheadTank.sort();
		dayAheadTank.seal();
	}
}