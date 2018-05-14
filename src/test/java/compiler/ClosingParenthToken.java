package compiler;

import compiler.Token;

public class ClosingParenthToken extends Token {
    public ClosingParenthToken(Object value) {
        super(value);
    }

    @Override
    public String toString() {
        return "compiler.ClosingParenthToken{" +
                "value=" + value +
                '}';
    }
}
