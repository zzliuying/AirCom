package com.zn.db.entity;


import java.util.Date;


/*
 * 实时数据实体类，变量与数据库表中的各个字段的值相同
 */
public class RealData {
	private int ID;
	private Date time;
	private String site_name;
	private String site_num;
	private String MN;
	private double longtitude;
	private double latitude;
	private double AQIindex;
	private double AQIlevel;
	private double SO2;
	private String SO2_flag;
	private double atm_pressure;
	private String atm_pressure_flag;
	private double wind_direction;
	private String wind_direction_flag;
	private double wind_speed;
	private String wind_speed_flag;
	private double NO2;
	private String NO2_flag;
	private double temperature;
	private String temperature_flag;
	private double TVOC;
	private String TVOC_flag;
	private double PM25;
	private String PM25_flag;
	private double PM10;
	private String PM10_flag;
	private double CO;
	private String CO_flag;
	private double O3;
	private String O3_flag;
	private double moisture;
	private String moistrue_flag;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSite_num() {
		return site_num;
	}
	public void setSite_num(String site_num) {
		this.site_num = site_num;
	}
	public String getMN() {
		return MN;
	}
	public void setMN(String mN) {
		MN = mN;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getAQIindex() {
		return AQIindex;
	}
	public void setAQIindex(double aQIindex) {
		AQIindex = aQIindex;
	}
	public double getAQIlevel() {
		return AQIlevel;
	}
	public void setAQIlevel(double aQIlevel) {
		AQIlevel = aQIlevel;
	}
	public double getSO2() {
		return SO2;
	}
	public void setSO2(double sO2) {
		SO2 = sO2;
	}
	public String getSO2_flag() {
		return SO2_flag;
	}
	public void setSO2_flag(String sO2_flag) {
		SO2_flag = sO2_flag;
	}
	public double getAtm_pressure() {
		return atm_pressure;
	}
	public void setAtm_pressure(double atm_pressure) {
		this.atm_pressure = atm_pressure;
	}
	public String getAtm_pressure_flag() {
		return atm_pressure_flag;
	}
	public void setAtm_pressure_flag(String atm_pressure_flag) {
		this.atm_pressure_flag = atm_pressure_flag;
	}
	public double getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(double wind_direction) {
		this.wind_direction = wind_direction;
	}
	public String getWind_direction_flag() {
		return wind_direction_flag;
	}
	public void setWind_direction_flag(String wind_direction_flag) {
		this.wind_direction_flag = wind_direction_flag;
	}
	public double getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}
	public String getWind_speed_flag() {
		return wind_speed_flag;
	}
	public void setWind_speed_flag(String wind_speed_flag) {
		this.wind_speed_flag = wind_speed_flag;
	}
	public double getNO2() {
		return NO2;
	}
	public void setNO2(double nO2) {
		NO2 = nO2;
	}
	public String getNO2_flag() {
		return NO2_flag;
	}
	public void setNO2_flag(String nO2_flag) {
		NO2_flag = nO2_flag;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public String getTemperature_flag() {
		return temperature_flag;
	}
	public void setTemperature_flag(String temperature_flag) {
		this.temperature_flag = temperature_flag;
	}
	public double getTVOC() {
		return TVOC;
	}
	public void setTVOC(double tVOC) {
		TVOC = tVOC;
	}
	public String getTVOC_flag() {
		return TVOC_flag;
	}
	public void setTVOC_flag(String tVOC_flag) {
		TVOC_flag = tVOC_flag;
	}
	public double getPM25() {
		return PM25;
	}
	public void setPM25(double pM25) {
		PM25 = pM25;
	}
	public String getPM25_flag() {
		return PM25_flag;
	}
	public void setPM25_flag(String pM25_flag) {
		PM25_flag = pM25_flag;
	}
	public double getPM10() {
		return PM10;
	}
	public void setPM10(double pM10) {
		PM10 = pM10;
	}
	public String getPM10_flag() {
		return PM10_flag;
	}
	public void setPM10_flag(String pM10_flag) {
		PM10_flag = pM10_flag;
	}
	public double getCO() {
		return CO;
	}
	public void setCO(double cO) {
		CO = cO;
	}
	public String getCO_flag() {
		return CO_flag;
	}
	public void setCO_flag(String cO_flag) {
		CO_flag = cO_flag;
	}
	public double getO3() {
		return O3;
	}
	public void setO3(double o3) {
		O3 = o3;
	}
	public String getO3_flag() {
		return O3_flag;
	}
	public void setO3_flag(String o3_flag) {
		O3_flag = o3_flag;
	}
	public double getMoisture() {
		return moisture;
	}
	public void setMoisture(double moisture) {
		this.moisture = moisture;
	}
	public String getMoistrue_flag() {
		return moistrue_flag;
	}
	public void setMoistrue_flag(String moistrue_flag) {
		this.moistrue_flag = moistrue_flag;
	}
	@Override
	public String toString() {
		return "RealData [time=" + time + ", site_name=" + site_name
				+ ", site_num=" + site_num + ", MN=" + MN + ", longtitude="
				+ longtitude + ", latitude=" + latitude + ", AQIindex="
				+ AQIindex + ", AQIlevel=" + AQIlevel + ", SO2=" + SO2
				+ ", SO2_flag=" + SO2_flag + ", atm_pressure=" + atm_pressure
				+ ", atm_pressure_flag=" + atm_pressure_flag
				+ ", wind_direction=" + wind_direction
				+ ", wind_direction_flag=" + wind_direction_flag
				+ ", wind_speed=" + wind_speed + ", wind_speed_flag="
				+ wind_speed_flag + ", NO2=" + NO2 + ", NO2_flag=" + NO2_flag
				+ ", temperature=" + temperature + ", temperature_flag="
				+ temperature_flag + ", TVOC=" + TVOC + ", TVOC_flag="
				+ TVOC_flag + ", PM25=" + PM25 + ", PM25_flag=" + PM25_flag
				+ ", PM10=" + PM10 + ", PM10_flag=" + PM10_flag + ", CO=" + CO
				+ ", CO_flag=" + CO_flag + ", O3=" + O3 + ", O3_flag="
				+ O3_flag + ", moisture=" + moisture + ", moistrue_flag="
				+ moistrue_flag + ", getID()=" + getID() + ", getTime()="
				+ getTime() + ", getSite_name()=" + getSite_name()
				+ ", getSite_num()=" + getSite_num() + ", getMN()=" + getMN()
				+ ", getLongtitude()=" + getLongtitude() + ", getLatitude()="
				+ getLatitude() + ", getAQIindex()=" + getAQIindex()
				+ ", getAQIlevel()=" + getAQIlevel() + ", getSO2()=" + getSO2()
				+ ", getSO2_flag()=" + getSO2_flag() + ", getAtm_pressure()="
				+ getAtm_pressure() + ", getAtm_pressure_flag()="
				+ getAtm_pressure_flag() + ", getWind_direction()="
				+ getWind_direction() + ", getWind_direction_flag()="
				+ getWind_direction_flag() + ", getWind_speed()="
				+ getWind_speed() + ", getWind_speed_flag()="
				+ getWind_speed_flag() + ", getNO2()=" + getNO2()
				+ ", getNO2_flag()=" + getNO2_flag() + ", getTemperature()="
				+ getTemperature() + ", getTemperature_flag()="
				+ getTemperature_flag() + ", getTVOC()=" + getTVOC()
				+ ", getTVOC_flag()=" + getTVOC_flag() + ", getPM25()="
				+ getPM25() + ", getPM25_flag()=" + getPM25_flag()
				+ ", getPM10()=" + getPM10() + ", getPM10_flag()="
				+ getPM10_flag() + ", getCO()=" + getCO() + ", getCO_flag()="
				+ getCO_flag() + ", getO3()=" + getO3() + ", getO3_flag()="
				+ getO3_flag() + ", getMoisture()=" + getMoisture()
				+ ", getMoistrue_flag()=" + getMoistrue_flag()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	


}
