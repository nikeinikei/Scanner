package compiler;

public abstract class FiniteStateAutomaton {
    /**
     * if lookAhead is true this FiniteStateAutomaton needs to look at the next character before
     * deciding that a string is valid.
     */
    private boolean lookAhead;

    /**
     * this wrapper will be called with the string that was validated by this FiniteStateAutomaton
     */
    private TokenConstructorWrapper tokenConstructorWrapper;

    public FiniteStateAutomaton(boolean lookAhead, TokenConstructorWrapper tokenConstructorWrapper) {
        this.lookAhead = lookAhead;
        this.tokenConstructorWrapper = tokenConstructorWrapper;
    }

    public boolean isLookAhead() {
        return lookAhead;
    }

    public void setLookAhead(boolean lookAhead) {
        this.lookAhead = lookAhead;
    }

    public TokenConstructorWrapper getTokenConstructorWrapper() {
        return tokenConstructorWrapper;
    }

    /**
     * @param input the input string
     * @return returns true if the FiniteStateAutomaton accepts the input String
     */
    public abstract boolean accepts(String input);
}
