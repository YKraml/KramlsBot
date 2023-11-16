package ui.timer;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineRunner;
import logic.routines.RoutineSpawnWaifuBuilder;
import util.Terminal;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpawnWaifuRunnable extends SaveRunnable {

    private final ScheduledExecutorService scheduledExecutorService;
    private final RoutineRunner routineRunner;
    private final RoutineSpawnWaifuBuilder routineSpawnWaifuBuilder;

    @Inject
    public SpawnWaifuRunnable(ScheduledExecutorService scheduledExecutorService,
                              RoutineRunner routineRunner, RoutineSpawnWaifuBuilder routineSpawnWaifuBuilder) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.routineRunner = routineRunner;
        this.routineSpawnWaifuBuilder = routineSpawnWaifuBuilder;
    }

    @Override
    public void runSave() throws MyOwnException {

        int timeInMinutes = ((int) (Math.random() * 30) + 10);
        scheduledExecutorService.schedule(this, timeInMinutes, TimeUnit.MINUTES);
        Terminal.printLine("Next waifu spawn in %d min.".formatted(timeInMinutes));

        routineRunner.start(routineSpawnWaifuBuilder.createRoutineSpawnWaifu());
    }


}
