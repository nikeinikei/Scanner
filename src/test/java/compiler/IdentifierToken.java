package compiler;

import compiler.Token;

public class IdentifierToken extends Token {
    public IdentifierToken(Object value) {
        super(value);
    }

    @Override
    public String toString() {
        return "compiler.IdentifierToken{" +
                "value=" + value +
                '}';
    }
}