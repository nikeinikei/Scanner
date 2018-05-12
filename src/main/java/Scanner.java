import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Scanner {
    private static final Collection<Character> WHITE_SPACES = Arrays.asList(' ', '\t');

    private String input;
    private int index = 0;
    private int lookAhead = 0;
    private Collection<DeterministicFiniteStateAutomaton> DFSAs;

    public Scanner(String input, Collection<DeterministicFiniteStateAutomaton> DFSAs) {
        this.DFSAs = DFSAs;
        this.input = input;
    }

    private boolean isEOF() {
        return index >= input.length();
    }

    private void skipWhiteSpaces() {
        while (!isEOF() && WHITE_SPACES.contains(input.charAt(index))) {
            index++;
            lookAhead = index;
        }
    }

    public Token getNextToken() {
        

        return null;
    }
}