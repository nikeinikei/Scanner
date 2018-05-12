package compiler;

/**
 * An Automaton can accept strings that match a certain criteria
 */
public abstract class Automaton {
    /**
     * if lookAhead is true this Automaton needs to look at the next character before
     * deciding that a string is valid.
     */
    private boolean lookAhead;

    /**
     * this wrapper will be called with the string that was validated by this Automaton
     */
    private TokenConstructorWrapper tokenConstructorWrapper;

    public Automaton(boolean lookAhead, TokenConstructorWrapper tokenConstructorWrapper) {
        this.lookAhead = lookAhead;
        this.tokenConstructorWrapper = tokenConstructorWrapper;
    }

    //GETTERS
    public boolean isLookAhead() {
        return lookAhead;
    }

    public TokenConstructorWrapper getTokenConstructorWrapper() {
        return tokenConstructorWrapper;
    }

    /**
     * @param input the input string
     * @return returns true if the Automaton accepts the input String
     */
    public abstract boolean accepts(String input);
}
