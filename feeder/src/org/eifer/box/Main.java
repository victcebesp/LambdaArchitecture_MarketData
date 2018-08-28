package org.eifer.box;

import io.intino.ness.datalake.Scale;
import io.intino.ness.datalake.graph.DatalakeGraph;
import io.intino.ness.datalake.graph.Tank;
import io.intino.tara.magritte.Graph;
import org.eifer.market.feeder.ActualGenerationFeeder;
import org.eifer.market.feeder.DayAheadFeeder;
import org.eifer.market.feeder.IntradayFeeder;
import org.eifer.market.feeder.MasterDataFeeder;
import org.eifer.market.reader.MasterDataReader;
import org.eifer.service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		FeederBox box = new FeederBox(args);
		box.open();

        List<String> epexAccountData = Files.readAllLines(Paths.get("C:\\Users\\ceballos\\IdeaProjects\\EnergyMarket\\feeder\\epex_account_data.txt"));
        String server = epexAccountData.get(0);
        String username = epexAccountData.get(1);
        String password = epexAccountData.get(2);

		/*SftpClient dayAheadSftpClient = new DayAheadSftpClient("/eod/market_data/power/spot/csv/")
				.connect(server, username, password)
				.retrieveFilesBetween(2000, 2018);

		SftpClient intradaySftpClient = new IntradaySftpClient("/eod/market_data/power/spot/csv/")
				.connect(server, username, password)
				.retrieveFilesBetween(2000, 2018);

		SftpClient masterDataSftpClient = new MasterDataSftpClient("/eod/transparency_data/power/csv/masterdata_power/")
				.connect(server, username, password)
				.retrieveFilesBetween(2009, 2013);*/

		//SftpClient actualGenerationSftpClient = new ActualGenerationSftpClient("/eod/transparency_data/power/csv/de_at/production/usage/ex_post/")
		//		.connect(server, username, password)
		//		.retrieveFilesBetween(2016, 2018);


		String directoryPath = box.configuration().get("directory-path");
		//new IntradayFeeder(directoryPath).feedTank();
		//new DayAheadFeeder(directoryPath).feedTank();
		//new MasterDataFeeder(directoryPath).feedTank();
		new ActualGenerationFeeder(directoryPath).feedTank();

		//intradaySftpClient.closeConnection();
		//dayAheadSftpClient.closeConnection();
		//masterDataSftpClient.closeConnection();
		//actualGenerationSftpClient.closeConnection();

		//compressAndSortDatalake(box);

		box.close();

		Runtime.getRuntime().addShutdownHook(new Thread(box::close));
	}

	//private static void compressAndSortDatalake(FeederBox box) {
	//	DatalakeGraph datalake = new Graph().loadStashes("Datalake").as(DatalakeGraph.class);
	//	datalake.directory(new File(box.configuration.get("datalake-url")));
	//	datalake.scale(Scale.Day);
	//	Tank intradayTank = datalake.add("market.intradayreport");
	//	intradayTank.sort();
	//	intradayTank.seal();
//
	//	Tank dayAheadTank = datalake.add("market.dayaheadreport");
	//	dayAheadTank.sort();
	//	dayAheadTank.seal();

//		Tank intradayTank = datalake.add("market.eexmasterdataunit");
//		intradayTank.sort();
//		intradayTank.seal();

	}
//}