package ui.timer;

import com.google.inject.Inject;
import util.Terminal;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineDungeonTickBuilder;
import logic.routines.RoutineRunner;

public class DungeonTickRunnable extends SaveRunnable {

    private final RoutineRunner routineRunner;
    private final RoutineDungeonTickBuilder routineDungeonTickBuilder;

    @Inject
    public DungeonTickRunnable(RoutineRunner routineRunner, RoutineDungeonTickBuilder routineDungeonTickBuilder) {
        this.routineRunner = routineRunner;
        this.routineDungeonTickBuilder = routineDungeonTickBuilder;
    }

    @Override
    public void runSave() throws MyOwnException {
        routineRunner.start(routineDungeonTickBuilder.createRoutineDungeonTick());
        Terminal.printLine("Dungeon tick");
    }

}
