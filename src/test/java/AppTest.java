import compiler.Automaton;
import compiler.Scanner;
import compiler.Token;
import org.junit.Test;

import java.util.Arrays;


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
        var scanner = new Scanner("n1k1 2", Arrays.asList(identifierAutomaton, numberAutomaton));
        while (scanner.hasNext()) {
            var next = scanner.next();
            System.out.println(next);
        }
    }
}
