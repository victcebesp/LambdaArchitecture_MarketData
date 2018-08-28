package org.eifer.market.feeder;

import io.intino.konos.alexandria.Inl;
import org.eifer.box.ness.TanksConnectors;
import org.eifer.box.schemas.ActualGeneration;
import org.eifer.market.directorywalker.ActualGenerationDirectoryWalker;
import org.eifer.market.generator.ActualGenerationGenerator;
import org.eifer.market.reader.ActualGenerationReader;

import java.io.IOException;
import java.util.List;

public class ActualGenerationFeeder implements Feeder {

    private final String path;

    public ActualGenerationFeeder(String path) {
        this.path = path;
    }

    @Override
    public void feedTank() throws IOException {

        new ActualGenerationDirectoryWalker().getFilePaths(path).forEach(p ->
                getEvents(p).forEach(e -> TanksConnectors.actualGeneration().feed(Inl.toMessage(e))));

    }

    private List<ActualGeneration> getEvents(String path) {
        List<String> records = new ActualGenerationReader().readRecords(path);
        return new ActualGenerationGenerator().getAllActualGenerationEvents(records);
    }
}
