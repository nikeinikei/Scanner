import compiler.Scanner;
import compiler.Token;

import java.util.ArrayList;
import java.util.List;

public class ScannerUtil {
    static void assertSCannerOutputClasses(Scanner scanner, List<Class<? extends Token>> tokenList) {
        List<Token> scannerTokens = new ArrayList<>();
        while (scanner.hasNext()) {
            scannerTokens.add(scanner.next());
        }

        AssertUtil.assertListSameClass(scannerTokens, tokenList);
    }
}
