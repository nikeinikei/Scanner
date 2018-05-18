package compiler;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;


public class Scanner implements Iterator<Token> {
    private static final Logger logger = Logger.getLogger(Scanner.class.getName());

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

        //add an extra whitespace to make automatons
        //using lookAhead work if it's at the ned of the file
        this.input = input;
    }

    private boolean notEOF(int index) {
        return index < input.length();
    }

    private void skipWhiteSpaces() {
        while (begin < input.length() && Character.isWhitespace(input.charAt(begin))) {
            begin++;
        }
        end = begin;
    }

    @Override
    public boolean hasNext() {
        return !(lastToken instanceof EOFToken);
    }

    @Override
    public Token next() {
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
                    for (var a : automatons)
                        a.reset();
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
                begin = input.length();
                return lastToken;
            }
        }

        //if not token could be detected there is an error with input

        //clean error message
        StringBuilder sb = new StringBuilder();
        sb.append("error while parsing at index ").append(begin);
        sb.append("\n");
        sb.append(input);
        sb.append("\n");
        for (int i = 0; i < begin; i++) {
            sb.append(" ");
        }
        sb.append("^");
        logger.severe(sb.toString());

        throw new IllegalStateException("error while parsing");
    }
}