package compiler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Scanner implements Iterator<Token> {
    /**
     * a collection of characters that act as blank space and can be ignored by the scanner
     */
    private static final Collection<Character> WHITE_SPACES = Arrays.asList(' ', '\t');


    /**
     * the input to the scanner
     */
    private String input;

    /**
     * the current index of the scan
     */
    private int index = 0;

    /**
     * the index of the lookAhead pointer. This will always be greater or equal to @member index
     */
    private int lookAhead = 0;

    /**
     * all FiniteStateAutomatons that will accept strings
     */
    private Collection<FiniteStateAutomaton> DFSAs;
    /**
     * the token that was last return on the @method this#next()
     */
    private Token lastToken = null;

    public Scanner(String input, Collection<FiniteStateAutomaton> DFSAs) {
        this.DFSAs = DFSAs;
        //add an extra whitespace to make automatons
        //using lookAhead work if it's at the ned of the file
        this.input = input + " ";

        //skip initial whitespaces
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