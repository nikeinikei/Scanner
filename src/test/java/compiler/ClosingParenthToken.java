package compiler;

public class ClosingParenthToken extends Token {
    public ClosingParenthToken(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "compiler.ClosingParenthToken{" +
                "value=" + value +
                '}';
    }
}
