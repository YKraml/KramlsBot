package actions.timer;

import com.google.inject.Inject;
import routines.RoutineGivePointsBuilder;
import routines.RoutineRunner;

public class GivePointsTask extends SaveRunnable {

  private static final int MONEY_PER_MINUTE = 10;
  private static final int TIME_IN_MINUTES = 5;
  private final RoutineRunner routineRunner;
  private final RoutineGivePointsBuilder routineGivePointsBuilder;

  @Inject
  public GivePointsTask(RoutineRunner routineRunner,
      RoutineGivePointsBuilder routineGivePointsBuilder) {
    this.routineGivePointsBuilder = routineGivePointsBuilder;
    this.routineRunner = routineRunner;
  }

  @Override
  public void runSave() throws Exception {
    routineRunner.startRoutine(
        routineGivePointsBuilder.createRoutineGivePoints(MONEY_PER_MINUTE, TIME_IN_MINUTES));

  }

  public int getTimeInMinutes() {
    return TIME_IN_MINUTES;
  }
}


