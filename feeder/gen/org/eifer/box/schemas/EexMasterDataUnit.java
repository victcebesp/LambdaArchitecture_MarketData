package org.eifer.box.schemas;

import org.eifer.box.schemas.*;

public  class EexMasterDataUnit implements java.io.Serializable {

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

	public EexMasterDataUnit ts(java.time.Instant ts) {
		this.ts = ts;
		return this;
	}

	public EexMasterDataUnit unitID(String unitID) {
		this.unitID = unitID;
		return this;
	}

	public EexMasterDataUnit capacity(String capacity) {
		this.capacity = capacity;
		return this;
	}

	public EexMasterDataUnit unitName(String unitName) {
		this.unitName = unitName;
		return this;
	}

	public EexMasterDataUnit plantID(String plantID) {
		this.plantID = plantID;
		return this;
	}

	public EexMasterDataUnit plantName(String plantName) {
		this.plantName = plantName;
		return this;
	}

	public EexMasterDataUnit connectingArea(String connectingArea) {
		this.connectingArea = connectingArea;
		return this;
	}

	public EexMasterDataUnit source(String source) {
		this.source = source;
		return this;
	}

	public EexMasterDataUnit startDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public EexMasterDataUnit endDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public EexMasterDataUnit latitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public EexMasterDataUnit longitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public EexMasterDataUnit country(String country) {
		this.country = country;
		return this;
	}

	public EexMasterDataUnit reportReason(String reportReason) {
		this.reportReason = reportReason;
		return this;
	}

	public EexMasterDataUnit companyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public EexMasterDataUnit companyID(String companyID) {
		this.companyID = companyID;
		return this;
	}

}