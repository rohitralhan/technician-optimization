package org.acme.techassign.service;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.acme.techassign.domain.Job;
import org.acme.techassign.domain.TechJobAssign;
import org.acme.techassign.solver.change.AddJobProblemChange;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.change.ProblemChange;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;

@ApplicationScoped
public class SolverService {

    private final ManagedExecutor managedExecutor;

    // TODO: Replace by @Inject SolverManager once https://issues.redhat.com/browse/PLANNER-2141 is resolved.
    private final Solver<TechJobAssign> solver;

    private AtomicBoolean solving = new AtomicBoolean(false);
    private CompletableFuture<?> completableSolverFuture;
    private final BlockingQueue<ProblemChange<TechJobAssign>> waitingProblemChanges = new LinkedBlockingQueue<>();
    

    
    
    @Inject
    public SolverService(SolverFactory<TechJobAssign> solverFactory, @Default ManagedExecutor executorService) {
        solver = solverFactory.buildSolver();
        this.managedExecutor = executorService;

    }
    
    public void startSolving(TechJobAssign inputProblem,
            Consumer<BestSolutionChangedEvent<TechJobAssign>> bestSolutionChangedEventConsumer, Consumer<Throwable> errorHandler) {
        solving.set(true);
        completableSolverFuture = managedExecutor.runAsync(() -> {

            solver.addEventListener(event -> {
                if (event.isEveryProblemChangeProcessed() && event.getNewBestScore().isSolutionInitialized()) {
                    bestSolutionChangedEventConsumer.accept(event);
                }
            });

            try {
                solver.solve(inputProblem);
            } catch (Throwable throwable) {
                errorHandler.accept(throwable);
            }
            solver.addProblemChanges(new ArrayList<>(waitingProblemChanges));
        });
    }

    public void stopSolving() {
        solving.set(false);
        if (completableSolverFuture != null) {
            solver.terminateEarly();
            //SolverManager.create(solverFactory);
            try {
                completableSolverFuture.get(); // Wait for termination and propagate exceptions.
                completableSolverFuture = null;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Failed to stop solver.", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Failed to stop solver.", e.getCause());
            }
        }
    }

    public boolean isSolving() {
        return solving.get();
    }

    public void addJob(Job job) {
        registerProblemChange(new AddJobProblemChange(job));
    }


    private void registerProblemChange(ProblemChange<TechJobAssign> problemChange) {
        if (isSolving()) {
            assertSolverIsAlive();
            solver.addProblemChange(problemChange);
        } else {
            waitingProblemChanges.add(problemChange);
        }
    }

    private void assertSolverIsAlive() {
        if (completableSolverFuture == null) {
            throw new IllegalStateException("Solver has not been started yet.");
        }
        if (completableSolverFuture.isDone()) {
            try {
                completableSolverFuture.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Solver thread was interrupted.", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Solver thread has died.", e.getCause());
            }
            throw new IllegalStateException("Solver has finished solving even though it operates in daemon mode.");
        }
    }/**/
}
