import compiler.EOFToken;
import compiler.Scanner;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AppTest {
    @Test
    public void testIdentifierAndNumberAutomaton() {
        var scanner = new Scanner("( )niki0 2345 timo4", Arrays.asList(
                StaticVariables.identifierAutomaton,
                StaticVariables.numberAutomaton,
                StaticVariables.openingParenthAutomaton,
                StaticVariables.closingParenthAutomaton,
                StaticVariables.stringAutomaton
        ));
        while (scanner.hasNext()) {
            var next = scanner.next();
            System.out.println(next);
        }
    }

    @Test
    public void testStringAutomaton() {
        assertTrue(StaticVariables.stringAutomaton.accepts("\"\""));
        assertFalse(StaticVariables.stringAutomaton.accepts("\"\"d"));
        assertTrue(StaticVariables.stringAutomaton.accepts("\"d\""));
        assertTrue(StaticVariables.stringAutomaton.accepts("\"\\r\""));
        assertFalse(StaticVariables.stringAutomaton.accepts("d\"\""));
        assertTrue(StaticVariables.stringAutomaton.accepts("\"isduiogu;;;,e45tghdfiogudhf\""));
    }

    @Test(expected = NoSuchElementException.class)
    public void testExceptionOnNextCallWithoutHasNext() {
        var scanner = new Scanner("", Collections.emptyList());
        assertTrue(scanner.hasNext());
        assertTrue(scanner.next() instanceof EOFToken);
        assertFalse(scanner.hasNext());
        scanner.next();
    }
}
