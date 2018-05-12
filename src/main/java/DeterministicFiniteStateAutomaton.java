import java.lang.reflect.Constructor;
import java.util.Collection;

public class DeterministicFiniteStateAutomaton extends FiniteStateAutomaton {
    private int startingState;
    private TransitionFunction transitionFunction;
    private Collection<Integer> acceptingStates;

    public DeterministicFiniteStateAutomaton(boolean lookAhead,
                                             TokenConstructorWrapper tokenConstructorWrapper,
                                             int startingState,
                                             TransitionFunction transitionFunction,
                                             Collection<Integer> acceptingStates) {
        super(lookAhead, tokenConstructorWrapper);
        this.startingState = startingState;
        this.transitionFunction = transitionFunction;
        this.acceptingStates = acceptingStates;
    }

    public boolean accepts(String input) {
        int currentState = this.startingState;
        char currentChar;
        for (int i = 0; i < input.length(); i++) {
            currentChar = input.charAt(i);
            currentState = transitionFunction.call(currentState, currentChar);
        }
        return acceptingStates.contains(currentState);
    }
}