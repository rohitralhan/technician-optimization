package org.acme.techassign.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.techassign.data.MySqlGetData;


@ApplicationScoped
public class SimulationService {

    private static final int MAX_DURATION_SECONDS = 60;
    private static final int MIN_DURATION_SECONDS = 10;
    private static final int MAX_FREQUENCY_PER_MINUTE = 60;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final SolverService solverService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    // Initial simulation values that are overridden by a client.
    private int durationSeconds = 2; 
    private int frequencyPerMinute = 30;

    private ScheduledFuture<?> addNewCallScheduledFuture;
    private int jobStartLoc = 1;

    @Inject
    public SimulationService(SolverService solverService) {
        this.solverService = solverService;
    }

    public void startSimulation() {
        startSimulation(frequencyPerMinute, durationSeconds); 
    }

    private void startSimulation(int frequency, int duration) {
        if (running.getAndSet(true)) {
            return; // The simulation has been already running.
        }

        if (frequency == 0) {
            return;
        }
        int delayInSeconds = 60 / frequency;
        MySqlGetData dataGenerator = new MySqlGetData();
        
        addNewCallScheduledFuture = scheduledExecutorService.scheduleAtFixedRate(
                () -> solverService.addJob(dataGenerator.getJob(jobStartLoc++)), 0, delayInSeconds, TimeUnit.SECONDS);
    }

    public void stopSimulation() {
        running.set(false);
        if (addNewCallScheduledFuture != null) {
            addNewCallScheduledFuture.cancel(true);
            addNewCallScheduledFuture = null;
        }
    }
}
