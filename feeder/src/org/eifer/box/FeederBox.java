package org.eifer.box;

public class FeederBox extends AbstractBox {

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
}