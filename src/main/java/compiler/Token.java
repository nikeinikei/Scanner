package compiler;

/**
 * A token is a string of letters that match a certain pattern
 * this class will be returned by the sccanner#next() method
 */
public abstract class Token {
    protected Object value;

    public Token(Object value) {
        this.value = value;
    }
}