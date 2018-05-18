package compiler;

public class NumberToken extends Token {
    public NumberToken(String numberRepr) {
        super(numberRepr);
    }

    @Override
    public String toString() {
        return "compiler.NumberToken{" +
                "value=" + value +
                '}';
    }
}
