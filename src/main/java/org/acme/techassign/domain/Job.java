package org.acme.techassign.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.techassign.routing.GHRouter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graphhopper.util.shapes.GHPoint;

@PlanningEntity
public class Job extends PreviousJobOrTech {

	private String jobId;
	private String region;
	private String district;
	private String dispatchArea;
	private String lati;
	private String longi;
	private Set<Skill> requiredSkill;
	private Set<Tool> requiredTools;
	private String ispulljob;
	private String dispatch;
	private String priority;
	private String earlyStartDate;
	private String dueDate;
	private String status;
	private String starttime;
	private String endtime;
	private int duration;
	

	@PlanningPin
    private boolean pinned;

    @JsonIgnore
    @PlanningVariable(valueRangeProviderRefs = { "techRange", "jobRange" }, graphType = PlanningVariableGraphType.CHAINED)
    private PreviousJobOrTech previousJobOrTech;

    @JsonIgnore
    @AnchorShadowVariable(sourceVariableName = "previousJobOrTech")
    private Tech tech;	
	
    final int maxSkillLevel = 5;
	
	public Job() {
		
	}

	
	public Job(Long id, String jobId, String region, String district, String dispatchArea, String lati, String longi, Set<Skill> requiredSkill,
			Set<Tool> requiredTools, String ispulljob, String dispatch, String priority, String earlyStartDate, String dueDate, String status, String starttime, String endtime, int duration) {
		super(id);
		this.jobId = jobId;
		this.region = region;
		this.district = district;
		this.dispatchArea = dispatchArea;
		this.lati = lati;
		this.longi = longi;
		this.requiredSkill = requiredSkill;
		this.requiredTools = requiredTools;
		this.ispulljob = ispulljob;
		this.dispatch = dispatch;
		this.priority = priority;
		this.dueDate = dueDate;
		this.earlyStartDate = earlyStartDate;
		this.status = status;
		this.starttime = starttime;
		this.endtime = endtime;
		this.duration = duration;
	}

	public String getJobId() {
		return jobId;
	}


	public void setJobID(String jobId) {
		this.jobId = jobId;
	}

	
	public PreviousJobOrTech getPreviousJobOrTech() {
		return previousJobOrTech;
	}


	public void setPreviousJobOrTech(PreviousJobOrTech previousJobOrTech) {
		if(jobId == "NC2022096161982" && previousJobOrTech instanceof Job && ((Job)previousJobOrTech).jobId == "NC2022075160734") {
			System.out.println("Previous Check -- " + ((Job)previousJobOrTech).jobId);
			throw new IllegalAccessError();
		}
		this.previousJobOrTech = previousJobOrTech;
	}


	public boolean isPinned() {
		return pinned;
	}
	
	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public int getMissingSkillCount() {
    	if(tech == null) {
    		return 0;
    	}
    	
    	return (int) requiredSkill.stream()
    			.filter(skill -> tech.getSkills().stream()
    					.noneMatch(reqSkill -> reqSkill.getSkill().equals(skill.getSkill()) 
// Remove this comment to have skill and skill level match						&& reqSkill.getSkillLevel() == skill.getSkillLevel()
    							)
    					)
    			.count();
    	/*return (int) requiredSkill.stream()
    			.filter(skill -> !tech.getSkills().contains(skill))
    			.count();*/
    }
	
	
	// ################ Match Skill Level and return missing skill level count ################
	public int getMissingSkillLevelCount() {
    	if(tech == null) {
    		return 0;
    	}

    	List<Skill> filterSkills = tech.getSkills().stream()
    	        .filter(p -> requiredSkill.stream()
    	                .anyMatch(e -> e.getSkill().equals(p.getSkill()))            
    	        )
    	        .map(i -> new Skill(i.getSkill(), maxSkillLevel - i.getSkillLevel()))
    	        .collect(Collectors.toList()
    	        );
    	
    	int missingSkillLevelCount = 0;
    	for (Skill skill : filterSkills) {
    		missingSkillLevelCount += skill.getSkillLevel();
		}  	
    	return missingSkillLevelCount;

    }

	
	// ################ Find missing tools and return missing tool count  ################
	public int getMissingTools() {
    	if(tech == null) {
    		return 0;
    	}
    	return (int) requiredTools.stream()
    			.filter(tool -> tech.getTools().stream()
    					.noneMatch(reqTools -> reqTools.getTool().equals(tool.getTool())))
    			.count();
    }	
	
	// ################ Match Region and District ################
	public boolean matchRegionDistrict() {
    	if(tech == null) {
    		return false;
    	}
    	return (tech.getRegion().equalsIgnoreCase(region) && tech.getDistrict().equalsIgnoreCase(district));
    }	

	
	
	// ################ Get Travel Time of the Tech ################
	public int getTravelTimeMS() {
		
    	if(tech == null) {
    		return 0;
    	}
    	
    	GHRouter ghr = new GHRouter(); 
		List<GHPoint> ghp = new ArrayList<GHPoint>();
		ghp.add(new GHPoint(tech.getLati()/1000000d, tech.getLongi()/1000000d));
		for(Job j : tech.getAssignedCalls()) {
			ghp.add(new GHPoint(Double.parseDouble(j.getLati())/1000000d, Double.parseDouble(j.getLongi())/1000000d));
		}
		
		if(!ghp.isEmpty()) {
			// ################ Real time calculation of travel time is very slow and does not perform very well hence commented/not used
			//return (int) ghr.travelTimeMillis(ghp);
			return (int) ghr.getTravelTime(ghp);
		}
		return 0;
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
	
	public String getDispatchArea() {
		return dispatchArea;
	}
	public void setDispatchArea(String dispatchArea) {
		this.dispatchArea = dispatchArea;
	}
	
	public String getLati() {
		return lati;
	}
	public void setLati(String lati) {
		this.lati = lati;
	}
	
	public String getLongi() {
		return longi;
	}
	public void setLongi(String longi) {
		this.longi = longi;
	}
	
    public Set<Skill> getRequiredSkill() {
		return requiredSkill;
	}
	public void setRequiredSkill(Set<Skill> requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	public Set<Tool> getRequiredTools() {
		return requiredTools;
	}
	public void setRequiredTools(Set<Tool> requiredTools) {
		this.requiredTools = requiredTools;
	}
	
	public String getIspulljob() {
		return ispulljob;
	}
	public void setIspulljob(String ispulljob) {
		this.ispulljob = ispulljob;
	}
	
	public String getDispatch() {
		return dispatch;
	}
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	
	public Tech getTech() {
		return tech;
	}

	public void setTech(Tech tech) {
		this.tech = tech;
	}	
	
	
    public String getEarlyStartDate() {
		return earlyStartDate;
	}


	public String getStatus() {
		return status;
	}


	public void setEarlyStartDate(String earlyStartDate) {
		this.earlyStartDate = earlyStartDate;
	}

	public String getEndtime() {
		return endtime;
	}


	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}	
	
	public String getStarttime() {
		return starttime;
	}


	public int getDuration() {
		return duration;
	}


	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Job))
            return false;
        Job job = (Job) o;
        return getId().equals(job.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }	
	
    @Override
    public String toString() {
    	return "Job " + jobId;
    }

    
    
}
