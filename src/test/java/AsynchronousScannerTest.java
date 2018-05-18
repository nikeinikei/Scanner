import compiler.EOFToken;
import compiler.IdentifierToken;
import compiler.NumberToken;
import compiler.Token;
import helper.AssertUtil;
import helper.StaticVariables;
import org.junit.Test;
import threading.AsynchronousScanner;
import threading.ReceiverException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AsynchronousScannerTest {
    @Test
    public void testAsynchronousScanner() throws ReceiverException {
        var asyncScanner = new AsynchronousScanner("34f fg5", Arrays.asList(
                StaticVariables.numberAutomaton,
                StaticVariables.identifierAutomaton
        ));
        var receiver = asyncScanner.getReceiver();
        Token lastToken = null;
        List<Token> tokens = new ArrayList<>();
        while (!(lastToken instanceof EOFToken)) {
            lastToken = receiver.receive();
            tokens.add(lastToken);
        }
        AssertUtil.assertListSameClass(
                tokens,
                Arrays.asList(
                        NumberToken.class,
                        IdentifierToken.class,
                        EOFToken.class)
        );
    }
}
