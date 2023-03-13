package routines;

import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;

public class RoutineRunner {

  public Answer startRoutine(Routine routine) throws MyOwnException {
    return routine.start(this);
  }

}
