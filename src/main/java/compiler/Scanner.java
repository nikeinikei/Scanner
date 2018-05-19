package compiler;

import java.util.Collection;


/**
 * a scanner implementation which has the whole String as an input
 * should be used when the input is small
 */
public class Scanner extends AbstractScanner {
    /**
     * the input to the scanner
     */
    private String input;

    private int charPointer = 0;

    public Scanner(String input, Collection<Automaton> automatons) {
        super(automatons);
        this.input = input;
    }

    @Override
    protected void readNextCharacter() {
        charPointer++;
    }

    @Override
    protected char getCurrentChar() {
        return input.charAt(charPointer);
    }

    @Override
    protected boolean notEOF() {
        return charPointer < input.length();
    }
}