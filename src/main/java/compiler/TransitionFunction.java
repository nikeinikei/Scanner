package compiler;

public interface TransitionFunction {
    /**
     * @param currentStatus the current status of the automaton
     * @param currentChar the current character of the automaton
     * @return the new status
     */
    int call(int currentStatus, char currentChar) throws TransitionFunctionException;
}