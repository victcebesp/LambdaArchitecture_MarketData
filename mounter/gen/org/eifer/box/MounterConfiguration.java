package org.eifer.box;

import java.util.Map;
import java.util.HashMap;

public class MounterConfiguration extends io.intino.sumus.box.SumusConfiguration {

	public MounterConfiguration(String[] args) {
		super(args);
		if (store == null) {
			if (this.args.get("graph_store") != null)
				store = new java.io.File(this.args.get("graph_store"));
			else store = new java.io.File("./store");
		}
	}

	public String get(String key) {
		return args.get(key);
	}

	public java.io.File store() {
		return this.store;
	}

	public static java.net.URL url(String url) {
		try {
		return new java.net.URL(url);
		} catch (java.net.MalformedURLException e) {
			return null;
		}
	}
}