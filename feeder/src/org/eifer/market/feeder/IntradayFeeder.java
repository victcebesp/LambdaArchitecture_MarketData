package org.eifer.market.feeder;

import io.intino.konos.alexandria.Inl;
import org.eifer.box.ness.TanksConnectors;
import org.eifer.box.schemas.IntradayReport;
import org.eifer.market.directorywalker.IntradayDirectoryWalker;
import org.eifer.market.generators.IntradayReportGenerator;
import org.eifer.market.reader.IntradayReader;

import java.io.IOException;
import java.util.List;

public class IntradayFeeder implements Feeder {

    private final String path;

    public IntradayFeeder(String path) {
        this.path = path;
    }

    @Override
    public void feedTank() throws IOException {
        new IntradayDirectoryWalker().getFilePaths(path).forEach(p ->
                getEvents(p).forEach(e -> TanksConnectors.intradayReport().feed(Inl.toMessage(e))));
    }

    private List<IntradayReport> getEvents(String path) {
        List<String> records = new IntradayReader().readRecords(path);
        return new IntradayReportGenerator().generateAllIntradayReportEvents(records, getPrizeZone(path));
    }

    private String getPrizeZone(String path) {

        if(path.contains("germany_austria")) return "DE-AT";
        if(path.contains("switzerland")) return "CH";
        else return "FR";
    }
}
