public abstract class FiniteStateAutomaton {
    private boolean lookAhead;
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

    public abstract boolean accepts(String input);
}
