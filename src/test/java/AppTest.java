import compiler.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;


public class AppTest {
    private Automaton identifierAutomaton = new Automaton(true, IdentifierToken::new) {
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

    private Automaton numberAutomaton = new Automaton(true, NumberToken::new) {
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

    @Test
    public void testIdentifierAndNumberAutomaton() {
        Scanner scanner = new Scanner("n1k1 2", Arrays.asList(identifierAutomaton, numberAutomaton));
        while (scanner.hasNext()) {
            Token next = scanner.next();
            System.out.println(next);
        }
    }
}
