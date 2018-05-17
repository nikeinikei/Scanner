package compiler;


import java.util.Collections;

public class SingleCharacterAutomaton extends DeterministicFiniteStateAutomaton {
    private char character;

    public SingleCharacterAutomaton(TokenConstructorWrapper tokenConstructorWrapper, final char character) {
        super(false, tokenConstructorWrapper, 0, (state, c) -> {
            switch (state) {
                case 0:
                    if (c == character) {
                        return 1;
                    } else {
                        return -1;
                    }
                default:
                    return -1;
            }
        }, Collections.singletonList(1));
        this.character = character;
    }

    @Override
    public String toString() {
        return "SingleCharacterAutomaton{" +
                "character=" + character +
                '}';
    }
}
