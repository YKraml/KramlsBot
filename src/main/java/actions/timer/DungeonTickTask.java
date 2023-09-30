package actions.timer;

import com.google.inject.Inject;
import de.kraml.Terminal;
import exceptions.MyOwnException;
import routines.RoutineDungeonTickBuilder;
import routines.RoutineRunner;

public class DungeonTickTask extends SaveRunnable {

  private final RoutineRunner routineRunner;
  private final RoutineDungeonTickBuilder routineDungeonTickBuilder;

  @Inject
  public DungeonTickTask(RoutineRunner routineRunner,
      RoutineDungeonTickBuilder routineDungeonTickBuilder) {
    this.routineRunner = routineRunner;
    this.routineDungeonTickBuilder = routineDungeonTickBuilder;
  }

  @Override
  public void runSave() throws MyOwnException {
    routineRunner.start(routineDungeonTickBuilder.createRoutineDungeonTick());
    Terminal.printLine("Dungeon tick");
  }

}
