package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;

public class RoutineRunner {

  public RoutineRunner() {
  }

  public Answer startRoutine(Routine routine) throws MyOwnException {
    return routine.start(this);
  }

}
