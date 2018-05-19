package threading;

import compiler.Automaton;
import compiler.Scanner;
import compiler.Token;

import java.util.Collection;

/**
 * This is a wrapper class around compiler.Scanner, that enables asynchronous scanning
 */
public class AsynchronousScanner {
    /**
     * the scanner
     */
    private Scanner scanner;

    /**
     * the channel that is used to buffer the tokens
     */
    private Channel<Token> channel;

    /**
     * @param input the input string
     * @param automatons the collection of automatons that will be used to scan the input
     *
     * this constructor should look exactly the same as the Scanner constructor.
     */
    public AsynchronousScanner(String input, Collection<Automaton> automatons) {
        scanner = new Scanner(input, automatons);
        channel = new Channel<>();
        Channel<Token>.Sender sender = channel.getSender();

        //create a new thread where the tokens will be generated
        new Thread(() -> {
            while (scanner.hasNext()) {
                try {
                    sender.send(scanner.next());
                } catch (SenderException e) {
                    System.err.println("receiver was closed before sender could send EOFToken");
                } catch (compiler.ParseException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @return the Receiver object that is connected to this channel
     */
    public Channel<Token>.Receiver getReceiver() {
        return channel.getReceiver();
    }
}
