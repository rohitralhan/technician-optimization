package org.acme.techassign.rest;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.techassign.service.SimulationService;
import org.acme.techassign.service.SolverService;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.acme.techassign.data.DataGenerator;
import org.acme.techassign.domain.TechJobAssign;

@Path("/job-assign")
public class JobAssignResource {

    private AtomicReference<TechJobAssign> bestSolution = new AtomicReference<>();
    private AtomicReference<Throwable> solvingError = new AtomicReference<>();

    
    private static final long PROBLEM_ID = 0L;  
    
    private final AtomicReference<Throwable> solverError = new AtomicReference<>();
    
    
    @Inject
    SolverService solverService;
    
    @Inject
    ScoreManager<TechJobAssign, HardSoftScore> scoreManager;

    @Inject
    SolverManager<TechJobAssign, Long> solverManager;    
    
    @Inject
    SimulationService simulationService;   
       
    @Inject
    JobAssignResource(DataGenerator dataGenerator) {
        bestSolution.set(dataGenerator.generateTechJob());      
    }


    private Status statusFromSolution(TechJobAssign solution) {
        return new Status(
                solution,
                scoreManager.explainScore(solution).getSummary(),
                solverManager.getSolverStatus(PROBLEM_ID));
    }
    

    @GET
    @Path("status")
    public Status status() {
        Optional.ofNullable(solverError.getAndSet(null)).ifPresent(throwable -> {
            throw new RuntimeException("Solver failed", throwable);
        });
        return statusFromSolution(bestSolution.get());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TechJobAssign get() {
        if (solvingError.get() != null) {
            throw new IllegalStateException("Exception occurred during solving.", solvingError.get());
        }
        TechJobAssign jobCenter = bestSolution.get();
        jobCenter.setSolving(solverService.isSolving());
        return jobCenter;
    }

    
    @POST
    @Path("solve")
    public void solve() {
    	solverService.startSolving(bestSolution.get(), bestSolutionChangedEvent -> {	
            bestSolution.set(bestSolutionChangedEvent.getNewBestSolution());
        }, throwable -> solvingError.set(throwable));
    	simulationService.startSimulation();

    }

    
    @POST
    @Path("stop")
    public void stop() {
        solverService.stopSolving();
    }

}