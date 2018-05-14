package compiler;

import compiler.Token;

public class NumberToken extends Token {
    public NumberToken(Object numberRepr) {
        super(numberRepr);
    }

    @Override
    public String toString() {
        return "compiler.NumberToken{" +
                "value=" + value +
                '}';
    }
}
