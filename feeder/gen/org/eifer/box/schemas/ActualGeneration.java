package org.eifer.box.schemas;

import org.eifer.box.schemas.*;

public  class ActualGeneration implements java.io.Serializable {

	private java.time.Instant ts;
	private String unitID = "";
	private String actualGeneration = "";
	private String country = "";

	public java.time.Instant ts() {
		return this.ts;
	}

	public String unitID() {
		return this.unitID;
	}

	public String actualGeneration() {
		return this.actualGeneration;
	}

	public String country() {
		return this.country;
	}

	public ActualGeneration ts(java.time.Instant ts) {
		this.ts = ts;
		return this;
	}

	public ActualGeneration unitID(String unitID) {
		this.unitID = unitID;
		return this;
	}

	public ActualGeneration actualGeneration(String actualGeneration) {
		this.actualGeneration = actualGeneration;
		return this;
	}

	public ActualGeneration country(String country) {
		this.country = country;
		return this;
	}

}