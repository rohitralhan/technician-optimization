package org.acme.techassign.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tech  extends PreviousJobOrTech {

	private String name;
	private String type;
	private Set<Skill> skills;	
    private Set<Tool> tools;
    private String region;
    private String district;
	private double lati;
	private double longi;    


	private String dispatchPolicy;
    private String selfAssigned;
    private String active;
    private String reqLunchBreak;
    

	public Tech() {
		// TODO Auto-generated constructor stub
	}

	public Tech(Long id, String name, String type, Set<Skill> skills, Set<Tool> tools, double lati, double longi, String region, String district, String dispatchPolicy, String selfAssigned, String active, String reqLunchBreak) {
		super(id);
		this.name = name;
		this.type = type;
		this.skills = skills;
		this.tools = tools;
		this.region = region;
		this.district = district;
		this.dispatchPolicy = dispatchPolicy;
		this.selfAssigned = selfAssigned;
		this.active = active;
		this.reqLunchBreak = reqLunchBreak;
		this.lati = lati;
		this.longi = longi;
	}

    @JsonProperty(value = "jobs", access = JsonProperty.Access.READ_ONLY)
    public List<Job> getAssignedCalls() {
        Job nextJob = getNextJob();
        List<Job> assignedCalls = new ArrayList<>();
        while (nextJob != null) {
            assignedCalls.add(nextJob);
            nextJob = nextJob.getNextJob();
        }
        return assignedCalls;
    }

    public double getLati() {
		return lati;
	}

	public double getLongi() {
		return longi;
	}

	public void setLati(double lati) {
		this.lati = lati;
	}

	public void setLongi(double longi) {
		this.longi = longi;
	}    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
   
    
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Skill> getSkills() {
		return skills;
	}


	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}


	public Set<Tool> getTools() {
		return tools;
	}


	public void setTools(Set<Tool> tools) {
		this.tools = tools;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getDispatchPolicy() {
		return dispatchPolicy;
	}


	public void setDispatchPolicy(String dispatchPolicy) {
		this.dispatchPolicy = dispatchPolicy;
	}


	public String getSelfAssigned() {
		return selfAssigned;
	}


	public void setSelfAssigned(String selfAssigned) {
		this.selfAssigned = selfAssigned;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}
    
	public String getReqLunchBreak() {
		return reqLunchBreak;
	}

	public void setReqLunchBreak(String reqLunchBreak) {
		this.reqLunchBreak = reqLunchBreak;
	}   
	
	
}