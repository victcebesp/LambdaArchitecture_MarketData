package org.eifer.box.schemas;

import org.eifer.box.schemas.*;

public  class IntradayReport implements java.io.Serializable {

	private java.time.Instant ts;
	private String priceZone = "";
	private String weightedPrice = "";
	private String id3 = "";
	private String type = "";

	public java.time.Instant ts() {
		return this.ts;
	}

	public String priceZone() {
		return this.priceZone;
	}

	public String weightedPrice() {
		return this.weightedPrice;
	}

	public String id3() {
		return this.id3;
	}

	public String type() {
		return this.type;
	}

	public IntradayReport ts(java.time.Instant ts) {
		this.ts = ts;
		return this;
	}

	public IntradayReport priceZone(String priceZone) {
		this.priceZone = priceZone;
		return this;
	}

	public IntradayReport weightedPrice(String weightedPrice) {
		this.weightedPrice = weightedPrice;
		return this;
	}

	public IntradayReport id3(String id3) {
		this.id3 = id3;
		return this;
	}

	public IntradayReport type(String type) {
		this.type = type;
		return this;
	}

}