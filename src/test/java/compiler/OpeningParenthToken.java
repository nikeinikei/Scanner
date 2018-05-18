package compiler;

public class OpeningParenthToken extends Token {
    public OpeningParenthToken(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "compiler.OpeningParenthToken{" +
                "value=" + value +
                '}';
    }
}
