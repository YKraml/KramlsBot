package logic.routines;

import domain.exceptions.MyOwnException;
import domain.Answer;

public class RoutineRunner {

    public Answer start(Routine routine) throws MyOwnException {
        return routine.start(this);
    }

}
