package logic.timer;

import com.google.inject.Inject;
import logic.routines.RoutineGivePointsBuilder;
import logic.routines.RoutineRunner;
import util.Terminal;

public class GivePointsRunnable extends SaveRunnable {

  private static final int MONEY_PER_MINUTE = 10;
  private static final int TIME_IN_MINUTES = 5;
  private final RoutineRunner routineRunner;
  private final RoutineGivePointsBuilder routineGivePointsBuilder;

  @Inject
  public GivePointsRunnable(RoutineRunner routineRunner,
      RoutineGivePointsBuilder routineGivePointsBuilder, Terminal terminal) {
    super(terminal);
    this.routineGivePointsBuilder = routineGivePointsBuilder;
    this.routineRunner = routineRunner;
  }

  @Override
  public void runSave() throws Exception {
    routineRunner.start(
        routineGivePointsBuilder.createRoutineGivePoints(MONEY_PER_MINUTE, TIME_IN_MINUTES));

  }

  public int getTimeInMinutes() {
    return TIME_IN_MINUTES;
  }
}


