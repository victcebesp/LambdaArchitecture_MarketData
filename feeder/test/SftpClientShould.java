import org.eifer.service.IntradaySftpClient;
import org.eifer.service.SftpClient;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class SftpClientShould {

    @Test
    public void given_2018_server_path_when_all_files_retrieved_place_three_files_in_intradayData_directory() throws IOException {

        SftpClient sftpClient = new IntradaySftpClient("/eod/market_data/power/spot/csv/");
        sftpClient.connect("85.239.110.71", "jan.eberbach_at_eifer.uni-karlsruhe.de", "EIFeex265");
        sftpClient.retrieveFilesBetween(2018, 2018);

        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2018/intraday_results_hours_france_2018.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2018/intraday_results_hours_germany_austria_2018.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2018/intraday_results_hours_switzerland_2018.csv")));

    }

    @Test
    public void given_2017_and_2018_server_path_when_all_files_retrieved_place_six_files_in_intradayData_directory() throws IOException {

        SftpClient sftpClient = new IntradaySftpClient("/eod/market_data/power/spot/csv/");
        sftpClient.connect("85.239.110.71", "jan.eberbach_at_eifer.uni-karlsruhe.de", "EIFeex265");
        sftpClient.retrieveFilesBetween(2017, 2018);

        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2018/intraday_results_hours_france_2018.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2018/intraday_results_hours_germany_austria_2018.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2018/intraday_results_hours_switzerland_2018.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2017/intraday_results_hours_france_2017.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2017/intraday_results_hours_germany_austria_2017.csv")));
        assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/2017/intraday_results_hours_switzerland_2017.csv")));

    }

    @Test
    public void given_from_2015_until_2018_server_path_when_all_files_retrieved_place_twelve_files_in_intradayData_directory() throws IOException {

        SftpClient sftpClient = new IntradaySftpClient("/eod/market_data/power/spot/csv/");
        sftpClient.connect("85.239.110.71", "jan.eberbach_at_eifer.uni-karlsruhe.de", "EIFeex265");
        sftpClient.retrieveFilesBetween(2015, 2018);

        for (int i = 2015; i < 2019; i++) {
            assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/" + i + "/intraday_results_hours_france_" + i + ".csv")));
            assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/" + i + "/intraday_results_hours_germany_austria_" + i + ".csv")));
            assertTrue(Files.exists(Paths.get("C:/Users/ceballos/IdeaProjects/EnergyMarket/tmp/marketData/" + i + "/intraday_results_hours_switzerland_" + i + ".csv")));
        }

    }

}
