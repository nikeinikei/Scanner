import compiler.*;

import java.util.Collections;
import java.util.function.Function;

public class StaticVariables {
    private static Function<Character, Integer> func = (character -> {
        if (Character.isLetterOrDigit(character))
            return 1;
        else if (Character.isWhitespace(character))
            return 2;
        else
            return -1;
    });

    static Automaton identifierAutomaton = new DeterministicFiniteStateAutomaton(
            true,
            IdentifierToken::new,
            0,
            (state, c) -> {
                switch (state) {
                    case 0:
                        if (Character.isLetter(c))
                            return 1;
                        else
                            return -1;
                    case 1:
                        return func.apply(c);
                    case 2:
                        return -1;
                    default:
                        return -1;
                }
            },
            Collections.singletonList(2)
    );

    static Automaton numberAutomaton = new DeterministicFiniteStateAutomaton(
            true,
            NumberToken::new,
            0,
            (state, c) -> {
                switch (state) {
                    case 0:
                        if (Character.isDigit(c))
                            return 1;
                        else
                            return -1;
                    case 1:
                        return func.apply(c);
                    case 2:
                        return -1;
                    default:
                        return -1;
                }
            },
            Collections.singletonList(2)
    );

    static Automaton openingParenthAutomaton = new SingleCharacterAutomaton(OpeningParenthToken::new, '(');
    static Automaton closingParenthAutomaton = new SingleCharacterAutomaton(ClosingParenthToken::new, ')');

    private static int stringTransitionFunction(int state, char c) {
        switch (state) {
            case 0:
                if (c == '"') {
                    return 1;
                }
                return -1;
            case 1:
                switch (c) {
                    case '"':
                        return 3;
                    case '\\':
                        return 2;
                    default:
                        return 1;
                }
            case 2:
                switch (c) {
                    case 'n':
                    case 'r':
                    case 't':
                    case '\\':
                    case '"':
                        return 1;
                    default:
                        return -1;
                }
            case 3:
                return -1;
            default:
                return -1;
        }
    }

    static DeterministicFiniteStateAutomaton stringAutomaton = new DeterministicFiniteStateAutomaton(
            false,
            StringToken::new,
            0,
            StaticVariables::stringTransitionFunction,
            Collections.singletonList(3)
    );
}
