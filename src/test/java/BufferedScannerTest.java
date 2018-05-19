import compiler.*;
import helper.ScannerUtil;
import helper.StaticVariables;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;


public class BufferedScannerTest {
    @Test
    public void testBufferedScanner() throws FileNotFoundException, ParseException {
        var scanner = new BufferedScanner(
                new FileReader("testData/identifierAndNumberTestInput.txt"),
                Arrays.asList(
                        StaticVariables.identifierAutomaton,
                        StaticVariables.numberAutomaton
                ));
        ScannerUtil.assertScannerOutput(scanner,
                Arrays.asList(
                        IdentifierToken.class,
                        NumberToken.class,
                        IdentifierToken.class,
                        EOFToken.class
                ), Arrays.asList(
                        "niki0",
                        "3f456",
                        "top3m",
                        null
                ));
    }

    @Test
    public void stressTest() throws FileNotFoundException, ParseException {
        var scanner = new BufferedScanner(
                new FileReader("testData/output.txt"),
                Arrays.asList(
                        StaticVariables.identifierAutomaton,
                        StaticVariables.numberAutomaton
                )
        );
        var tokenCount = 0;
        var expectedTokenCount = 10_001;
        while (scanner.hasNext()) {
            var token = scanner.next();
            assert token instanceof IdentifierToken
                    || token instanceof NumberToken
                    || token instanceof EOFToken;
            tokenCount++;
        }
        assert tokenCount == expectedTokenCount;
    }
}
