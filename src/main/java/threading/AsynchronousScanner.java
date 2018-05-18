package threading;

import compiler.Automaton;
import compiler.Scanner;
import compiler.Token;

import java.util.Collection;

/**
 * This is a wrapper class around compiler.Scanner, that enables asynchronous scanning
 */
public class AsynchronousScanner {
    private Scanner scanner;
    private Channel<Token> channel;

    public AsynchronousScanner(String input, Collection<Automaton> automatons) {
        scanner = new Scanner(input, automatons);
        channel = new Channel<>();
        Channel<Token>.Sender sender = channel.getSender();
        new Thread(() -> {
            while (scanner.hasNext()) {
                try {
                    sender.send(scanner.next());
                } catch (SenderException e) {
                    System.err.println("receiver was closed before sender could send EOFToken");
                }
            }
        }).start();
    }

    public Channel<Token>.Receiver getReceiver() {
        return channel.getReceiver();
    }
}
