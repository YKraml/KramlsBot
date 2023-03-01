package actions.timer;

import exceptions.MyOwnException;
import routines.RoutineDungeonTickBuilder;
import routines.RoutineRunner;
import de.kraml.Terminal;

public class DungeonTickTask extends SaveRunnable {

  private final RoutineRunner routineRunner;
  private final RoutineDungeonTickBuilder routineDungeonTickBuilder;

  public DungeonTickTask(RoutineRunner routineRunner,
      RoutineDungeonTickBuilder routineDungeonTickBuilder) {
    this.routineRunner = routineRunner;
    this.routineDungeonTickBuilder = routineDungeonTickBuilder;
  }

  @Override
  public void runSave() throws MyOwnException {
      routineRunner.startRoutine(routineDungeonTickBuilder.createRoutineDungeonTick());

    Terminal.printLine("Dungeon tick");
  }

}
