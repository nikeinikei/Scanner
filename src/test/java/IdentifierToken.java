public class IdentifierToken extends Token {
    public IdentifierToken(Object value) {
        super(value);
    }

    @Override
    public String toString() {
        return "IdentifierToken{" +
                "value=" + value +
                '}';
    }
}
