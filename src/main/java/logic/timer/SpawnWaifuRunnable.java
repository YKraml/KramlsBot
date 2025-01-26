package logic.timer;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import logic.routines.RoutineRunner;
import logic.routines.RoutineSpawnWaifuBuilder;
import util.Terminal;

public class SpawnWaifuRunnable extends SaveRunnable {

  private final ScheduledExecutorService scheduledExecutorService;
  private final RoutineRunner routineRunner;
  private final RoutineSpawnWaifuBuilder routineSpawnWaifuBuilder;
  private final Terminal terminal;

  @Inject
  public SpawnWaifuRunnable(ScheduledExecutorService scheduledExecutorService,
      RoutineRunner routineRunner, RoutineSpawnWaifuBuilder routineSpawnWaifuBuilder,
      Terminal terminal) {
    super(terminal);
    this.scheduledExecutorService = scheduledExecutorService;
    this.routineRunner = routineRunner;
    this.routineSpawnWaifuBuilder = routineSpawnWaifuBuilder;
    this.terminal = terminal;
  }

  @Override
  public void runSave() throws MyOwnException {

    int timeInMinutes = ((int) (Math.random() * 30) + 10);
    scheduledExecutorService.schedule(this, timeInMinutes, TimeUnit.MINUTES);
    terminal.printLine("Next waifu spawn in %d min.".formatted(timeInMinutes));

    routineRunner.start(routineSpawnWaifuBuilder.createRoutineSpawnWaifu());
  }


}
