package org.eifer.box.schemas;

import org.eifer.box.schemas.*;

public  class ActualGenerationPoint implements java.io.Serializable {

	private java.time.Instant ts;
	private String unitID = "";
	private String capacity = "";
	private String unitName = "";
	private String plantID = "";
	private String plantName = "";
	private String connectingArea = "";
	private String source = "";
	private String startDate = "";
	private String endDate = "";
	private String latitude = "";
	private String longitude = "";
	private String country = "";
	private String reportReason = "";
	private String companyName = "";
	private String companyID = "";
	private String commercialisation = "";
	private String actualGeneration = "";

	public java.time.Instant ts() {
		return this.ts;
	}

	public String unitID() {
		return this.unitID;
	}

	public String capacity() {
		return this.capacity;
	}

	public String unitName() {
		return this.unitName;
	}

	public String plantID() {
		return this.plantID;
	}

	public String plantName() {
		return this.plantName;
	}

	public String connectingArea() {
		return this.connectingArea;
	}

	public String source() {
		return this.source;
	}

	public String startDate() {
		return this.startDate;
	}

	public String endDate() {
		return this.endDate;
	}

	public String latitude() {
		return this.latitude;
	}

	public String longitude() {
		return this.longitude;
	}

	public String country() {
		return this.country;
	}

	public String reportReason() {
		return this.reportReason;
	}

	public String companyName() {
		return this.companyName;
	}

	public String companyID() {
		return this.companyID;
	}

	public String commercialisation() {
		return this.commercialisation;
	}

	public String actualGeneration() {
		return this.actualGeneration;
	}

	public ActualGenerationPoint ts(java.time.Instant ts) {
		this.ts = ts;
		return this;
	}

	public ActualGenerationPoint unitID(String unitID) {
		this.unitID = unitID;
		return this;
	}

	public ActualGenerationPoint capacity(String capacity) {
		this.capacity = capacity;
		return this;
	}

	public ActualGenerationPoint unitName(String unitName) {
		this.unitName = unitName;
		return this;
	}

	public ActualGenerationPoint plantID(String plantID) {
		this.plantID = plantID;
		return this;
	}

	public ActualGenerationPoint plantName(String plantName) {
		this.plantName = plantName;
		return this;
	}

	public ActualGenerationPoint connectingArea(String connectingArea) {
		this.connectingArea = connectingArea;
		return this;
	}

	public ActualGenerationPoint source(String source) {
		this.source = source;
		return this;
	}

	public ActualGenerationPoint startDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public ActualGenerationPoint endDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public ActualGenerationPoint latitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public ActualGenerationPoint longitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public ActualGenerationPoint country(String country) {
		this.country = country;
		return this;
	}

	public ActualGenerationPoint reportReason(String reportReason) {
		this.reportReason = reportReason;
		return this;
	}

	public ActualGenerationPoint companyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public ActualGenerationPoint companyID(String companyID) {
		this.companyID = companyID;
		return this;
	}

	public ActualGenerationPoint commercialisation(String commercialisation) {
		this.commercialisation = commercialisation;
		return this;
	}

	public ActualGenerationPoint actualGeneration(String actualGeneration) {
		this.actualGeneration = actualGeneration;
		return this;
	}

}