package org.acme.techassign.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@PlanningEntity
public abstract class PreviousJobOrTech {

	private Long id;
	
	@JsonIgnore
    @InverseRelationShadowVariable(sourceVariableName = "previousJobOrTech")
    protected Job nextJob;
	

	public PreviousJobOrTech() {
		// TODO Auto-generated constructor stub
	}

	public PreviousJobOrTech(long id) {
		this.id = id;
	}
	
	public Job getNextJob() {
		return nextJob;
	}

	public void setNextJob(Job nextJob) {
		this.nextJob = nextJob;
	}

	@PlanningId
	public Long getId() {
		return id;
	}
}
