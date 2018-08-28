package org.eifer.market.generator;

import org.eifer.box.schemas.ActualGeneration;
import org.eifer.market.parser.ActualGenerationTimeParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActualGenerationGenerator {

    public List<ActualGeneration> getAllActualGenerationEvents(List<String> csvLine) {

        List<ActualGeneration> actualGenerationList = new ArrayList<>();
        csvLine.forEach(line -> actualGenerationList.add(createActualGeneration(line)));
        return actualGenerationList.stream().sorted(Comparator.comparing(ActualGeneration::ts)).collect(Collectors.toList());
    }

    private ActualGeneration createActualGeneration(String line) {
        String[] fields = line.split(";");
        int country = 1;
        int unitID = 2;
        int actualGeneration = 4;
        int ts = 3;
        return new ActualGeneration()
                .ts(new ActualGenerationTimeParser().parseDateTimeToInstant(fields[ts]))
                .country(fields[country])
                .unitID(fields[unitID])
                .actualGeneration(fields[actualGeneration] + " MW");
    }

}
