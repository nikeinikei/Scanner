package compiler;

import java.util.Collection;

public class DeterministicFiniteStateAutomaton extends Automaton {
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
            try {
                currentState = transitionFunction.call(currentState, currentChar);
            } catch (TransitionFunctionException e) {
                System.err.println("there was an exception during the transitionfunction");
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return acceptingStates.contains(currentState);
    }
}