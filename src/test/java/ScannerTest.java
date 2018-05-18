import compiler.*;
import helper.ScannerUtil;
import helper.StaticVariables;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ScannerTest {
    @Test
    public void testIdentifierAndNumberAutomaton() {
        var scanner = new Scanner("( )niki0 2345 timo4", Arrays.asList(
                StaticVariables.identifierAutomaton,
                StaticVariables.numberAutomaton,
                StaticVariables.openingParenthAutomaton,
                StaticVariables.closingParenthAutomaton,
                StaticVariables.stringAutomaton
        ));
        ScannerUtil.assertSCannerOutputClasses(scanner, Arrays.asList(
                OpeningParenthToken.class,
                ClosingParenthToken.class,
                IdentifierToken.class,
                NumberToken.class,
                IdentifierToken.class,
                EOFToken.class
        ));
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

    @Test
    public void testScannerWithStringAutomaton() {
        var scanner = new Scanner(
                "\"hallo\" \"\\n\\r.ddd\\t\"",
                Collections.singletonList(StaticVariables.stringAutomaton)
        );
        ScannerUtil.assertSCannerOutputClasses(
                scanner,
                Arrays.asList(StringToken.class, StringToken.class, EOFToken.class)
        );
    }
}
