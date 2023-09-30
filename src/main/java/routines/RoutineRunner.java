package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;

public class RoutineRunner {

  public Answer start(Routine routine) throws MyOwnException {
    return routine.start(this);
  }

}
