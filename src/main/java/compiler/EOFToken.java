package compiler;

/**
 * This token will be return when the scanner has reached the end of the file
 */
public class EOFToken extends Token {
    public EOFToken() {
        super(null);
    }

    @Override
    public String
    toString() {
        return "compiler.EOFToken{" +
                '}';
    }
}
