package compiler;

/**
 * A Token signaling the charPointer of file. There won't be any additional tokens after this one, in one scan.
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
