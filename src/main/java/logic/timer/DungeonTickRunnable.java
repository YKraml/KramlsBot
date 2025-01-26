package logic.timer;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineDungeonTickBuilder;
import logic.routines.RoutineRunner;
import util.Terminal;

public class DungeonTickRunnable extends SaveRunnable {

  private final RoutineRunner routineRunner;
  private final RoutineDungeonTickBuilder routineDungeonTickBuilder;
  private final Terminal terminal;

  @Inject
  public DungeonTickRunnable(RoutineRunner routineRunner,
      RoutineDungeonTickBuilder routineDungeonTickBuilder, Terminal terminal) {
    super(terminal);
    this.routineRunner = routineRunner;
    this.routineDungeonTickBuilder = routineDungeonTickBuilder;
    this.terminal = terminal;
  }

  @Override
  public void runSave() throws MyOwnException {
    routineRunner.start(routineDungeonTickBuilder.createRoutineDungeonTick());
    terminal.printLine("Dungeon tick");
  }

}
