package org.acme.techassign.solver.change;


import org.acme.techassign.domain.Job;
import org.acme.techassign.domain.TechJobAssign;
import org.optaplanner.core.api.solver.change.ProblemChange;
import org.optaplanner.core.api.solver.change.ProblemChangeDirector;

public class AddJobProblemChange implements ProblemChange<TechJobAssign> {

    private final Job job;

    public AddJobProblemChange(Job job) {
        this.job = job;
    }

    @Override
    public void doChange(TechJobAssign workingJob, ProblemChangeDirector problemChangeDirector) {
        problemChangeDirector.addEntity(job, workingJob.getJobs()::add);
    }
}
