package compiler;

/**
 * A token is a string of letters that match a certain pattern
 * this class will be returned by the scanner#next() method
 */
public abstract class Token {
    protected String value;

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}