package compiler;

import compiler.Token;

public class OpeningParenthToken extends Token {
    public OpeningParenthToken(Object value) {
        super(value);
    }

    @Override
    public String toString() {
        return "compiler.OpeningParenthToken{" +
                "value=" + value +
                '}';
    }
}
