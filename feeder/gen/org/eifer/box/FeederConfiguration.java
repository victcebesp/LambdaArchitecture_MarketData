package org.eifer.box;

import java.util.Map;
import java.util.HashMap;

public class FeederConfiguration extends io.intino.konos.alexandria.BoxConfiguration {

	public FeederConfiguration(String[] args) {
		super(args);

	}

	public String get(String key) {
		return args.get(key);
	}



	public static java.net.URL url(String url) {
		try {
		return new java.net.URL(url);
		} catch (java.net.MalformedURLException e) {
			return null;
		}
	}
}