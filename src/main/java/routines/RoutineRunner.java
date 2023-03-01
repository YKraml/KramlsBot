package routines;

import actions.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;

public class RoutineRunner {

  @Inject
  public RoutineRunner() {
  }

  public Answer startRoutine(Routine routine) throws MyOwnException {
    return routine.start(this);
  }

}
