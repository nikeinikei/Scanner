package compiler;

/**
 * An Automaton can accept strings that match a certain criteria
 */
public abstract class Automaton {
    /**
     * if lookAhead is true this Automaton needs to look at the next character before
     * deciding that a string is valid. This is important for the scanner.
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

    /**
     * @return this#lookAhead
     */
    public boolean isLookAhead() {
        return lookAhead;
    }

    /**
     * @return this#tokenConstructorWrapper
     */
    public TokenConstructorWrapper getTokenConstructorWrapper() {
        return tokenConstructorWrapper;
    }

    /**
     * @param input the input string
     * @return returns true if the Automaton accepts the input String
     */
    public boolean accepts(String input) {
        for (char c : input.toCharArray()) {
            input(c);
        }
        var isAccepting = isAccepting();
        reset();
        return isAccepting;
    }

    public abstract void input(char input);

    public abstract boolean isAccepting();

    public abstract void reset();
}
