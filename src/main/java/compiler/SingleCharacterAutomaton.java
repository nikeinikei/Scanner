package compiler;

public class SingleCharacterAutomaton extends Automaton {
    private char character;

    public SingleCharacterAutomaton(TokenConstructorWrapper tokenConstructorWrapper, char character) {
        super(false, tokenConstructorWrapper);
        this.character = character;
    }

    @Override
    public boolean accepts(String input) {
        if (input.length() == 1) {
            return input.charAt(0) == character;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SingleCharacterAutomaton{" +
                "character=" + character +
                '}';
    }
}
