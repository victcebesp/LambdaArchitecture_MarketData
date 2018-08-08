package org.eifer.box.schemas;

import org.eifer.box.schemas.*;

public  class MasterDataUnit implements java.io.Serializable {

	private java.time.Instant ts;
	private String unitID = "";
	private double capacity = 0.0;
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

	public java.time.Instant ts() {
		return this.ts;
	}

	public String unitID() {
		return this.unitID;
	}

	public double capacity() {
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

	public MasterDataUnit ts(java.time.Instant ts) {
		this.ts = ts;
		return this;
	}

	public MasterDataUnit unitID(String unitID) {
		this.unitID = unitID;
		return this;
	}

	public MasterDataUnit capacity(double capacity) {
		this.capacity = capacity;
		return this;
	}

	public MasterDataUnit unitName(String unitName) {
		this.unitName = unitName;
		return this;
	}

	public MasterDataUnit plantID(String plantID) {
		this.plantID = plantID;
		return this;
	}

	public MasterDataUnit plantName(String plantName) {
		this.plantName = plantName;
		return this;
	}

	public MasterDataUnit connectingArea(String connectingArea) {
		this.connectingArea = connectingArea;
		return this;
	}

	public MasterDataUnit source(String source) {
		this.source = source;
		return this;
	}

	public MasterDataUnit startDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public MasterDataUnit endDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public MasterDataUnit latitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public MasterDataUnit longitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public MasterDataUnit country(String country) {
		this.country = country;
		return this;
	}

	public MasterDataUnit reportReason(String reportReason) {
		this.reportReason = reportReason;
		return this;
	}

	public MasterDataUnit companyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public MasterDataUnit companyID(String companyID) {
		this.companyID = companyID;
		return this;
	}

}