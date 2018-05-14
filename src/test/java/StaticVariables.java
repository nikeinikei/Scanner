import compiler.*;

import java.util.Collections;

public class StaticVariables {
    static Automaton identifierAutomaton = new Automaton(true, IdentifierToken::new) {
        @Override
        public boolean accepts(String input) {
            if (!Character.isLetter(input.charAt(0))) {
                return false;
            }

            for (int i = 1; i < input.length() - 1; i++) {
                if (!Character.isLetterOrDigit(input.charAt(i))) {
                    return false;
                }
            }

            return !Character.isLetterOrDigit(input.charAt(input.length() - 1));
        }
    };
    static Automaton numberAutomaton = new Automaton(true, NumberToken::new) {
        @Override
        public boolean accepts(String input) {
            if (!Character.isDigit(input.charAt(0))) {
                return false;
            }

            for (int i = 1; i < input.length() - 1; i++) {
                if (!Character.isLetterOrDigit(input.charAt(i))) {
                    return false;
                }
            }

            return !Character.isLetterOrDigit(input.charAt(input.length() - 1));
        }
    };
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
