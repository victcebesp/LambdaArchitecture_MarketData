package org.eifer.box;

import org.eifer.box.schemas.MasterDataUnit;

import java.util.HashMap;
import java.util.Map;

public class FeederBox extends AbstractBox {

	private static Map<String, MasterDataUnit> existingMasterDataUnits = new HashMap<>();

	public FeederBox(String[] args) {
		super(args);
	}

	public FeederBox(FeederConfiguration configuration) {
		super(configuration);
	}

	@Override
	public io.intino.konos.alexandria.Box put(Object o) {
		super.put(o);
		return this;
	}

	public io.intino.konos.alexandria.Box open() {
		return super.open();
	}

	public void close() {
		super.close();
	}

	public static Map<String, MasterDataUnit> existingMasterDataUnits() {
		return existingMasterDataUnits;
	}

}