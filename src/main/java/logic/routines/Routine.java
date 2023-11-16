package logic.routines;

import domain.exceptions.MyOwnException;
import ui.commands.Answer;

public abstract class Routine {

    abstract Answer start(RoutineRunner routineRunner) throws MyOwnException;

}
