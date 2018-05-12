package compiler;

import java.util.Collection;
import java.util.Iterator;


public class Scanner implements Iterator<Token> {
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
     * all Automatons that can check strings for acceptance
     */
    private Collection<Automaton> DFSAs;

    /**
     * the token that was last return on the @method this#next()
     */
    private Token lastToken = null;

    public Scanner(String input, Collection<Automaton> DFSAs) {
        this.DFSAs = DFSAs;

        //add an extra whitespace, if needed, to make automatons
        //using lookAhead work if it's at the ned of the file
        if (!Character.isWhitespace(input.charAt(input.length() - 1)))
            this.input = input + " ";
    }

    private boolean notEOF(int index) {
        return index <= input.length();
    }

    private void skipWhiteSpaces() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
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
            var currentString = input.substring(index, lookAhead);
            for (Automaton automaton : DFSAs)
                if (automaton.accepts(currentString)) {
                    lastToken = automaton.getTokenConstructorWrapper().newInstance(currentString);
                    if (automaton.isLookAhead())
                        index = lookAhead;
                    else
                        index = ++lookAhead;
                    return lastToken;
                }
            lookAhead++;
        }

        lastToken = new EOFToken();
        return lastToken;
    }
}