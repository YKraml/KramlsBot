package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;

public abstract class Routine {

  abstract Answer start(RoutineRunner routineRunner) throws MyOwnException;

}
