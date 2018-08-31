package org.eifer.box.ness.feeders;

import org.eifer.box.FeederBox;

import io.intino.ness.inl.Message;
import java.util.Arrays;

public class EexMasterDataUnitFeeder extends AbstractEexMasterDataUnitFeeder {
	private final FeederBox box;

	public EexMasterDataUnitFeeder(FeederBox box) {
		this.box = box;
	}


}