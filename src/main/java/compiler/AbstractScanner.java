package compiler;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * This class contains the logic to read tokens,
 * but has left the implementation of the input to subclasses
 */
public abstract class AbstractScanner {
    /**
     * all Automatons that can check strings for acceptance
     */
    private Collection<Automaton> automatons;

    /**
     * the token that was last return on the @method this#next()
     */
    private Token lastToken = null;

    AbstractScanner(Collection<Automaton> automatons) {
        this.automatons = automatons;
    }

    /**
     * @return true if there the scanne hasn't reached EOF yet
     */
    public boolean hasNext() {
        return !(lastToken instanceof EOFToken);
    }

    /**
     * increments the begin pointer until it doesn't point to a whitespace character anymore
     */
    private void skipWhiteSpaces() {
        while (notEOF() && Character.isWhitespace(getCurrentChar())) {
            readNextCharacter();
        }
    }

    /**
     * reads the next character
     */
    protected abstract void readNextCharacter();

    /**
     * @return the current character that is being read
     */
    protected abstract char getCurrentChar();

    /**
     * @return true if scanner hasn't reached the end of the file yet
     */
    protected abstract boolean notEOF();

    /**
     * @return the next token
     * @throws ParseException if there is a problem with the input String
     *
     * this method contains the main logic behind the scanner
     */
    public final Token next() throws ParseException {
        if (lastToken instanceof EOFToken)
            throw new NoSuchElementException();

        skipWhiteSpaces();

        if (!notEOF()) {
            lastToken = new EOFToken();
            return lastToken;
        }

        StringBuilder wholeTokenStringBuilder = new StringBuilder();

        //memory optimization
        char currentChar;
        String wholeString;
        while (notEOF()) {
            currentChar = getCurrentChar();
            for (var automaton : automatons) {
                automaton.input(currentChar);
                if (automaton.isAccepting()) {
                    if (!automaton.isLookAhead()) {
                        wholeTokenStringBuilder.append(currentChar);
                        readNextCharacter();
                    }
                    wholeString = wholeTokenStringBuilder.toString();
                    lastToken = automaton.getTokenConstructorWrapper().newInstance(wholeString);
                    automatons.forEach(Automaton::reset);
                    return lastToken;
                }
            }
            readNextCharacter();
            wholeTokenStringBuilder.append(currentChar);
        }

        for (var automaton : automatons) {
            automaton.input(' ');
            if (automaton.isAccepting()) {
                wholeString = wholeTokenStringBuilder.toString();
                lastToken = automaton.getTokenConstructorWrapper().newInstance(wholeString);
                automatons.forEach(Automaton::reset);
                return lastToken;
            }
        }

        throw new ParseException();
    }
}
