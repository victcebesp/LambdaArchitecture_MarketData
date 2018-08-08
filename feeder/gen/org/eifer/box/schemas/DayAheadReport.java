package org.eifer.box.schemas;

import org.eifer.box.schemas.*;

public  class DayAheadReport implements java.io.Serializable {

	private java.time.Instant ts;
	private String priceZone = "";
	private String volume = "";
	private String price = "";
	private String type = "";

	public java.time.Instant ts() {
		return this.ts;
	}

	public String priceZone() {
		return this.priceZone;
	}

	public String volume() {
		return this.volume;
	}

	public String price() {
		return this.price;
	}

	public String type() {
		return this.type;
	}

	public DayAheadReport ts(java.time.Instant ts) {
		this.ts = ts;
		return this;
	}

	public DayAheadReport priceZone(String priceZone) {
		this.priceZone = priceZone;
		return this;
	}

	public DayAheadReport volume(String volume) {
		this.volume = volume;
		return this;
	}

	public DayAheadReport price(String price) {
		this.price = price;
		return this;
	}

	public DayAheadReport type(String type) {
		this.type = type;
		return this;
	}

}