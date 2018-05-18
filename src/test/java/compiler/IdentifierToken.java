package compiler;

public class IdentifierToken extends Token {
    public IdentifierToken(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "compiler.IdentifierToken{" +
                "value=" + value +
                '}';
    }
}
