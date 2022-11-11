package org.acme.techassign.domain;

import java.util.Objects;

public class TechNJobs {

	private String id;
    private String region;
    private String district;
	private double lati;
	private double longi;  
	private String latLong;
	
	
	  @Override
	  public boolean equals(final Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if (obj == null) {
	      return false;
	    }
	    if (getClass() != obj.getClass()) {
	      return false;
	    }
	    TechNJobs other = (TechNJobs) obj;
	    return Objects.equals(id, other.id);
	  }	
	
	
	public TechNJobs(String id, String region, String district, double lati, double longi, String latLong) {
		super();
		this.id = id;
		this.region = region;
		this.district = district;
		this.lati = lati;
		this.longi = longi;
		this.latLong = latLong;
	}



	public TechNJobs() {
		super();
		//TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public String getRegion() {
		return region;
	}


	public String getDistrict() {
		return district;
	}


	public double getLati() {
		return lati;
	}


	public double getLongi() {
		return longi;
	}


	public String getLatLong() {
		return latLong;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public void setLati(double lati) {
		this.lati = lati;
	}


	public void setLongi(double longi) {
		this.longi = longi;
	}


	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}
	
}
