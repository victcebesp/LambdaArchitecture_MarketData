package org.eifer.box.ness.mounters;

import org.eifer.box.MounterBox;
import org.eifer.box.schemas.*;

import java.time.Instant;

import static org.eifer.box.MounterBox.*;

public class ActualGenerationMounter {
	public MounterBox box;
	public org.eifer.box.schemas.ActualGeneration actualGeneration;

	public void execute() {

		String key = actualGeneration.unitID() + actualGeneration.ts();

		ActualGenerationPoint point = actualGenerationPoints.getOrDefault(key, new ActualGenerationPoint());

		point.ts(actualGeneration.ts())
			.unitID(actualGeneration.unitID())
			.actualGeneration(actualGeneration.actualGeneration())
			.country(actualGeneration.country());

		actualGenerationPoints.put(key, point);

	}
}