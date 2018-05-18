package helper;

import compiler.Scanner;
import compiler.Token;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ScannerUtil {
    public static void assertScannerOutputClasses(Scanner scanner, List<Class<? extends Token>> tokenList) throws ParseException {
        List<Token> scannerTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            scannerTokens.add(scanner.next());
        }

        AssertUtil.assertListSameClass(scannerTokens, tokenList);
    }
}
