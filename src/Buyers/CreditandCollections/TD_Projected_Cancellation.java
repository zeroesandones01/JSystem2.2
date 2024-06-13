package Buyers.CreditandCollections;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TD_Projected_Cancellation {

	private String unitPBL;
	private String clientName;
	private String buyerType;
	private String houseModel;
	private Timestamp trDate;
	private Timestamp orDate;
	private Timestamp defaultDate;
	private String stage;
	private String saleDiv;
	private String class_type;
	private String saleAgent;
	private String withNTC;
	private Timestamp ctsNotarize;
	private String reason;
	private BigDecimal grossTCP;
	private String phase;
	private Integer mpd;


	public String getUnitPBL() {
		return unitPBL;
	}

	public void setUnitPBL(String unitPBL) {
		this.unitPBL = unitPBL;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public String getHouseModel() {
		return houseModel;
	}

	public void setHouseModel(String houseModel) {
		this.houseModel = houseModel;
	}

	public Timestamp getTrDate() {
		return trDate;
	}

	public void setTrDate(Timestamp trDate) {
		this.trDate = trDate;
	}

	public Timestamp getOrDate() {
		return orDate;
	}

	public void setOrDate(Timestamp orDate) {
		this.orDate = orDate;
	}

	public Timestamp getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(Timestamp defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSaleDiv() {
		return saleDiv;
	}


	public void setSaleDiv(String saleDiv) {
		this.saleDiv = saleDiv;
	}

	public String getClass_type() {
		return class_type;
	}

	public void setClass_type(String class_type) {
		this.class_type = class_type;
	}


	public String getSaleAgent() {
		return saleAgent;
	}


	public void setSaleAgent(String saleAgent) {
		this.saleAgent = saleAgent;
	}

	public String getWithNTC() {
		return withNTC;
	}

	public void setWithNTC(String withNTC) {
		this.withNTC = withNTC;
	}


	public Timestamp getCtsNotarize() {
		return ctsNotarize;
	}


	public void setCtsNotarize(Timestamp ctsNotarize) {
		this.ctsNotarize = ctsNotarize;
	}


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getGrossTCP() {
		return grossTCP;
	}


	public void setGrossTCP(BigDecimal grossTCP) {
		this.grossTCP = grossTCP;
	}


	public String getPhase() {
		return phase;
	}


	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Integer getMpd() {
		return mpd;
	}

	public void setMpd(Integer mpd) {
		this.mpd = mpd;
	}

	public TD_Projected_Cancellation(String unitPBL,String clientName, String buyerType, String houseModel, Timestamp trDate, Timestamp orDate, Timestamp defaultDate, String stage, String saleDiv, String class_type, String saleAgent, String withNTC, Timestamp ctsNotarize, String reason, BigDecimal grossTCP,String phase, Integer mpd) {
		this.unitPBL = unitPBL;
		this.clientName = clientName;
		this.buyerType = buyerType;
		this.houseModel = houseModel;
		this.trDate = trDate;
		this.orDate = orDate;
		this.defaultDate = defaultDate;
		this.stage = stage;
		this.saleDiv = saleDiv;
		this.class_type = class_type;
		this.saleAgent = saleAgent;
		this.withNTC = withNTC;
		this.ctsNotarize = ctsNotarize;
		this.reason = reason;
		this.grossTCP = grossTCP;
		this.mpd = mpd;
		System.out.printf("Display Sales Division dito sa Getter and Setter: %s%n", this.saleDiv);
	}

}
