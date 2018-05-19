package compiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

/**
 * a scanner implementation which uses a buffered reader.
 * should be used when the input file is large to improve memory and processor efficiency
 */
public class BufferedScanner extends AbstractScanner {
    /**
     * the buffered reader that's used to read the input
     */
    private BufferedReader reader;

    /**
     * the current char
     */
    private int currentChar;

    /**
     * @param fileReader the fileReader used to create a BufferedReader
     * @param automatons the collection of automatons that's used by the AbstractScanner
     */
    public BufferedScanner(FileReader fileReader, Collection<Automaton> automatons) {
        super(automatons);

        reader = new BufferedReader(fileReader);
        try {
            currentChar = reader.read();
        } catch (IOException e) {
            readerErrorCallback();
        }
    }

    @Override
    protected void readNextCharacter() {
        try {
            currentChar = reader.read();
        } catch (IOException e) {
            readerErrorCallback();
        }
    }

    @Override
    protected char getCurrentChar() {
        return (char) currentChar;
    }

    @Override
    protected boolean notEOF() {
        return currentChar != -1;
    }

    private void readerErrorCallback() {
        System.err.println("error while reading from reader");
        throw new Error();
    }
}
