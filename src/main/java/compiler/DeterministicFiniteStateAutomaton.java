package compiler;

import java.util.Collection;

/**
 * A class that can simulate a working deterministic finite state automaton.
 * Its alphabet is by default every character, and the state will always be of type int.
 */
public class DeterministicFiniteStateAutomaton extends Automaton {
    /**
     * the starting state of the automaton
     */
    private int startingState;


    /**
     * the transition function, that give back the next state
     * based on the current input
     */
    private TransitionFunction transitionFunction;


    /**
     * the collection of accepting states.
     * this collection contains the state at the charPointer of the
     * processing, the input is marked as accepted.
     */
    private Collection<Integer> acceptingStates;

    /**
     * the current state of the automaton
     */
    private int currentState;

    public DeterministicFiniteStateAutomaton(boolean lookAhead,
                                             TokenConstructorWrapper tokenConstructorWrapper,
                                             int startingState,
                                             TransitionFunction transitionFunction,
                                             Collection<Integer> acceptingStates) {
        super(lookAhead, tokenConstructorWrapper);
        this.startingState = startingState;
        this.currentState = startingState;
        this.transitionFunction = transitionFunction;
        this.acceptingStates = acceptingStates;
    }

    /**
     * @param e the exception that occurred while calling the transition function
     */
    private void errorCallback(TransitionFunctionException e) {
        System.err.println("there was an exception during the transitionfunction");
        e.printStackTrace();
        System.exit(-1);
    }

    @Override
    public boolean accepts(String input) {
        int currentState = this.startingState;
        char currentChar;
        for (int i = 0; i < input.length(); i++) {
            currentChar = input.charAt(i);
            try {
                currentState = transitionFunction.call(currentState, currentChar);
            } catch (TransitionFunctionException e) {
                errorCallback(e);
            }
        }
        return acceptingStates.contains(currentState);
    }

    @Override
    public void input(char input) {
        try {
            currentState = transitionFunction.call(currentState, input);
        } catch (TransitionFunctionException e) {
            errorCallback(e);
        }
    }

    @Override
    public boolean isAccepting() {
        return this.acceptingStates.contains(this.currentState);
    }

    @Override
    public void reset() {
        this.currentState = this.startingState;
    }
}