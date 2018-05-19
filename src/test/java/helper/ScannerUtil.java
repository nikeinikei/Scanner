package helper;

import compiler.AbstractScanner;
import compiler.ParseException;
import compiler.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertFalse;

public class ScannerUtil {
    public static void assertScannerOutputClasses(AbstractScanner scanner, List<Class<? extends Token>> tokenList) throws ParseException {
        List<Token> scannerTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            scannerTokens.add(scanner.next());
        }

        AssertUtil.assertListSameClass(scannerTokens, tokenList);
    }

    public static void assertScannerOutput(AbstractScanner scanner, List<Class<? extends Token>> tokenList, List<String> tokenValues) throws ParseException {
        List<Token> scannerTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            scannerTokens.add(scanner.next());
        }

        AssertUtil.assertListSameClass(scannerTokens, tokenList);
        assertTokensValues(scannerTokens, tokenValues);
    }

    private static void assertTokensValues(List<Token> tokenList, List<String> values) {
        var tokenIterator = tokenList.iterator();
        var valueIterator = values.iterator();
        while (tokenIterator.hasNext() && valueIterator.hasNext()) {
            var token = tokenIterator.next().getValue();
            var value = valueIterator.next();
            assert Objects.equals(token, value);
        }
        assertFalse(tokenIterator.hasNext());
        assertFalse(valueIterator.hasNext());
    }
}
