import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Scanner implements Iterator<Token> {
    private static final Collection<Character> WHITE_SPACES = Arrays.asList(' ', '\t');

    private String input;
    private int index = 0;
    private int lookAhead = 0;
    private Collection<FiniteStateAutomaton> DFSAs;
    private Token lastToken = null;

    public Scanner(String input, Collection<FiniteStateAutomaton> DFSAs) {
        this.DFSAs = DFSAs;
        this.input = input + " ";

        skipWhiteSpaces();
    }

    private boolean notEOF(int index) {
        return index <= input.length();
    }

    private void skipWhiteSpaces() {
        while (index < input.length() && WHITE_SPACES.contains(input.charAt(index))) {
            index++;
        }
        lookAhead = index;
    }

    @Override
    public boolean hasNext() {
        return !(lastToken instanceof EOFToken);
    }

    @Override
    public Token next() {
        skipWhiteSpaces();
        lookAhead = index + 1;
        while (notEOF(lookAhead)) {
            String currentString = input.substring(index, lookAhead);
            for (FiniteStateAutomaton dfsa : DFSAs) {
                boolean accepts = dfsa.accepts(currentString);
                if (accepts) {
                    lastToken = dfsa.getTokenConstructorWrapper().newInstance(currentString);
                    if (dfsa.isLookAhead()) {
                        index = lookAhead;
                    } else {
                        index = ++lookAhead;
                    }
                    return lastToken;
                }
            }
            lookAhead++;
        }

        lastToken = new EOFToken();
        return lastToken;
    }
}