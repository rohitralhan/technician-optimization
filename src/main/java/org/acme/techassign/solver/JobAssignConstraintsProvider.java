package org.acme.techassign.solver;

import org.acme.techassign.domain.Job;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class JobAssignConstraintsProvider  implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                noRequiredSkillMissing(constraintFactory),
                RequiredSkillLevelMissing(constraintFactory),
                noRequiredToolsMissing(constraintFactory),
                isRegionDistrictMatch(constraintFactory),
                travelTime(constraintFactory),                
        };
    }


	Constraint noRequiredSkillMissing(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Job.class)
                .filter(job -> job.getMissingSkillCount() > 0)
                .penalize("required skills are missing", HardSoftScore.ONE_HARD, job -> job.getMissingSkillCount());
    }	

    
    Constraint RequiredSkillLevelMissing(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Job.class)
                .filter(job -> job.getMissingSkillLevelCount() > 0)
                .penalize("required skills level are missing", HardSoftScore.ONE_SOFT);
    }	    

    
    Constraint noRequiredToolsMissing(ConstraintFactory constraintFactory) { 
        return constraintFactory.forEach(Job.class)
                .filter(job -> job.getMissingTools() > 0)
                .penalize("required tools are missing", HardSoftScore.ONE_HARD, job -> job.getMissingTools());
    }	

    
    Constraint isRegionDistrictMatch(ConstraintFactory constraintFactory) {
		return constraintFactory.forEach(Job.class)
				.filter(job -> job.matchRegionDistrict() == false)
				.penalize("region district match", HardSoftScore.ONE_HARD); 
	}    

 
	private Constraint travelTime(ConstraintFactory constraintFactory) { 
		return constraintFactory.forEach(Job.class)
				.penalize("travel time", HardSoftScore.ONE_SOFT, job -> job.getTravelTimeMS());
				
	}

}
