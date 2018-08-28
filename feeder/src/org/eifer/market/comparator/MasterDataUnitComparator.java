package org.eifer.market.comparator;

import org.eifer.box.schemas.EexMasterDataUnit;

public class MasterDataUnitComparator {

    public static boolean compare (EexMasterDataUnit first, EexMasterDataUnit second) {

        return first.unitID().equals(second.unitID()) &&
                first.capacity().equals(second.capacity()) &&
                first.unitName().equals(second.unitName()) &&
                first.plantID().equals(second.plantID()) &&
                first.plantName().equals(second.plantName()) &&

                first.connectingArea().equals(second.connectingArea()) &&

                first.source().equals(second.source()) &&
                first.startDate().equals(second.startDate()) &&
                first.endDate().equals(second.endDate()) &&
                first.latitude().equals(second.latitude()) &&
                first.longitude().equals(second.longitude()) &&
                first.country().equals(second.country()) &&
                first.reportReason().equals(second.reportReason()) &&
                first.companyName().equals(second.companyName()) &&
                first.companyID().equals(second.companyID());


    }

}
