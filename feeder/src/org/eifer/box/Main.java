package org.eifer.box;

import io.intino.konos.datalake.Datalake;
import io.intino.ness.datalake.Scale;
import io.intino.ness.datalake.graph.DatalakeGraph;
import io.intino.ness.datalake.graph.Tank;
import io.intino.tara.magritte.Graph;
import org.eifer.box.ness.TanksConnectors;
import org.eifer.box.ness.feeders.ActualGenerationFeederFeeder;
import org.eifer.box.ness.feeders.DayAheadFeederFeeder;
import org.eifer.box.ness.feeders.IntradayFeederFeeder;
import org.eifer.box.ness.feeders.MasterDataFeederFeeder;
import org.eifer.service.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

public class Main {

	public static void main(String[] args) throws IOException {

		FeederBox box = new FeederBox(args);
		box.open();

        String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("epex.properties").getPath();

        Properties epexProperties = new Properties();
        epexProperties.load(new FileInputStream(propertiesPath));
        String server = epexProperties.getProperty("server");
        String username = epexProperties.getProperty("username");
        String password = epexProperties.getProperty("password");

		SftpClient.connect(server, username, password);

		new DayAheadSftpClient("/eod/market_data/power/spot/csv/")
				.retrieveFilesBetween(2000, 2018);

		new IntradaySftpClient("/eod/market_data/power/spot/csv/")
				.retrieveFilesBetween(2000, 2018);

		new MasterDataSftpClient("/eod/transparency_data/power/csv/masterdata_power/")
				.retrieveFilesBetween(2009, 2013);

		new ActualGenerationSftpClient("/eod/transparency_data/power/csv/de_at/production/usage/ex_post/")
				.retrieveFilesBetween(2016, 2018);

		new IntradayFeederFeeder(box).feedTank();
		new DayAheadFeederFeeder(box).feedTank();
		new MasterDataFeederFeeder(box).feedTank();
		new ActualGenerationFeederFeeder(box).feedTank();

		SftpClient.closeConnection();

		Files.walk(Paths.get("./tmp/marketData"))
				.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(File::delete);

		compressAndSortDatalake(box);

		box.close();

		Runtime.getRuntime().addShutdownHook(new Thread(box::close));
	}

	private static void compressAndSortDatalake(FeederBox box) {
		DatalakeGraph datalake = new Graph().loadStashes("Datalake").as(DatalakeGraph.class);
		datalake.directory(new File(box.configuration.get("datalake-url")));
		datalake.scale(Scale.Day);

		TanksConnectors.all().stream().map(Datalake.Tank::name).forEach(e -> {
			Tank tank = datalake.add(e);
			tank.sort();
			tank.seal();
		});

	}
}