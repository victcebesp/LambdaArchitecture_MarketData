package org.eifer.box.ness.mounters;

import org.eifer.box.MounterBox;
import org.eifer.box.schemas.*;

import static org.eifer.box.MounterBox.actualGenerationPoints;

public class EexMasterDataUnitMounter {
	public MounterBox box;
	public org.eifer.box.schemas.EexMasterDataUnit eexMasterDataUnit;

	public void execute() {

		String key = eexMasterDataUnit.unitID() + eexMasterDataUnit.ts();

		ActualGenerationPoint point = actualGenerationPoints.getOrDefault(key, new ActualGenerationPoint());

		point.ts(eexMasterDataUnit.ts())
			 .unitID(eexMasterDataUnit.unitID())
			 .capacity(eexMasterDataUnit.capacity())
			 .unitName(eexMasterDataUnit.unitName())
			 .plantID(eexMasterDataUnit.plantID())
			 .plantName(eexMasterDataUnit.plantName())
			 .connectingArea(eexMasterDataUnit.connectingArea())
			 .source(eexMasterDataUnit.source())
			 .startDate(eexMasterDataUnit.startDate())
			 .endDate(eexMasterDataUnit.endDate())
			 .latitude(eexMasterDataUnit.latitude())
			 .longitude(eexMasterDataUnit.longitude())
			 .country(eexMasterDataUnit.country())
			 .reportReason(eexMasterDataUnit.reportReason())
			 .companyName(eexMasterDataUnit.companyName())
			 .companyID(eexMasterDataUnit.companyID())
			 .commercialisation(eexMasterDataUnit.commercialisation());

		actualGenerationPoints.put(key, point);

	}
}