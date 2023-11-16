package logic.routines;

import domain.exceptions.MyOwnException;
import domain.Answer;

public abstract class Routine {

    abstract Answer start(RoutineRunner routineRunner) throws MyOwnException;

}
