package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;

public class RoutineRunner {

    public Answer start(Routine routine) throws MyOwnException {
        return routine.start(this);
    }

}
