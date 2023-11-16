package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;

public abstract class Routine {

    abstract Answer start(RoutineRunner routineRunner) throws MyOwnException;

}
