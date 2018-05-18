package compiler;

import java.text.ParseException;
import java.util.Collection;
import java.util.NoSuchElementException;


public class Scanner {
    /**
     * the input to the scanner
     */
    private String input;

    /**
     * the current begin of the scan
     */
    private int begin = 0;

    /**
     * the begin of the end pointer. This will always be greater or equal to @member begin
     */
    private int end = 0;

    /**
     * all Automatons that can check strings for acceptance
     */
    private Collection<Automaton> automatons;

    /**
     * the token that was last return on the @method this#next()
     */
    private Token lastToken = null;

    public Scanner(String input, Collection<Automaton> automatons) {
        this.automatons = automatons;
        this.input = input;
    }

    /**
     * @param index the index to be checked
     * @return true if the index is not at the end of file (EOF) yet
     */
    private boolean notEOF(int index) {
        return index < input.length();
    }

    /**
     * increments the begin pointer until it doesn't point to a whitespace character anymore
     */
    private void skipWhiteSpaces() {
        while (begin < input.length() && Character.isWhitespace(input.charAt(begin))) {
            begin++;
        }
        end = begin;
    }

    /**
     * @return true if there the scanne hasn't reached EOF yet
     */
    public boolean hasNext() {
        return !(lastToken instanceof EOFToken);
    }

    /**
     * @return the next token
     * @throws ParseException if there is a problem with the input String
     */
    public Token next() throws ParseException {
        if (lastToken instanceof EOFToken)
            throw new NoSuchElementException();

        skipWhiteSpaces();

        if (begin >= input.length()) {
            lastToken = new EOFToken();
            return lastToken;
        }

        end = begin;
        while (notEOF(end)) {
            for (var automaton : automatons) {
                automaton.input(input.charAt(end));
                if (automaton.isAccepting()) {
                    String wholeString = input.substring(begin, end + 1);
                    lastToken = automaton.getTokenConstructorWrapper().newInstance(wholeString);
                    if (automaton.isLookAhead())
                        begin = end;
                    else
                        begin = end + 1;
                    automatons.forEach(Automaton::reset);
                    return lastToken;
                }
            }
            end++;
        }

        //if at the end of the file, try it again after a whitespace,
        //since some automatons need it.
        for (var automaton : automatons) {
            automaton.input(' ');
            if (automaton.isAccepting()) {
                String wholeString = input.substring(begin);
                lastToken = automaton.getTokenConstructorWrapper().newInstance(wholeString);
                automatons.forEach(Automaton::reset);
                //point being to EOF so it will return an EOFToken the next time next() gets called
                begin = input.length();
                return lastToken;
            }
        }

        //if nothing worked by now
        //throw a ParseException
        throw new ParseException(input, begin);
    }
}