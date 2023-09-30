package actions.timer;

import com.google.inject.Inject;
import de.kraml.Terminal;
import exceptions.MyOwnException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import routines.RoutineRunner;
import routines.RoutineSpawnWaifuBuilder;

public class SpawnWaifuTask extends SaveRunnable {

  private final ScheduledExecutorService scheduledExecutorService;
  private final RoutineRunner routineRunner;
  private final RoutineSpawnWaifuBuilder routineSpawnWaifuBuilder;

  @Inject
  public SpawnWaifuTask(ScheduledExecutorService scheduledExecutorService,
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
