import java.util.Collection;

public class DeterministicFiniteStateAutomaton {
    private int startingState;
    private TransitionFunction transitionFunction;
    private Collection<Integer> acceptingStates;
    private boolean lookAhead = false;
    private Class tokenClass = Token.class;

    public DeterministicFiniteStateAutomaton(int startingState, TransitionFunction transitionFunction, Collection<Integer> acceptingStates) {
        this.startingState = startingState;
        this.transitionFunction = transitionFunction;
        this.acceptingStates = acceptingStates;
    }

    public void setLookAhead(boolean lookAhead) {
        this.lookAhead = lookAhead;
    }

    public boolean getLookAhead() {
        return this.lookAhead;
    }

    private void setTokenClass(Class tokenClass) {
        this.tokenClass = tokenClass;
    }

    private Class getTokenClass() {
        return tokenClass;
    }

    public boolean checkString(String input) {
        int currentState = this.startingState;
        char currentChar;
        for (int i = 0; i < input.length(); i++) {
            currentChar = input.charAt(i);
            currentState = transitionFunction.call(currentState, currentChar);
        }
        return acceptingStates.contains(currentState);
    }
}