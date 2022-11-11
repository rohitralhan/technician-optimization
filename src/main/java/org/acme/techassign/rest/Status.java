package org.acme.techassign.rest;

import org.acme.techassign.domain.TechJobAssign;
import org.optaplanner.core.api.solver.SolverStatus;

public class Status {

    public final TechJobAssign solution;
    public final String scoreExplanation;
    public final boolean isSolving;

    public Status(TechJobAssign solution, String scoreExplanation, SolverStatus solverStatus) {
        this.solution = solution;
        this.scoreExplanation = scoreExplanation;
        this.isSolving = solverStatus != SolverStatus.NOT_SOLVING;
    }
}