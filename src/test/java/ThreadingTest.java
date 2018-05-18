import org.junit.Test;
import threading.Channel;
import threading.ReceiverException;

import java.util.logging.Logger;


public class ThreadingTest {
    private static final Logger logger = Logger.getLogger(ThreadingTest.class.getName());

    @Test(expected = ReceiverException.class)
    public void testReceiveOnClosedSender() throws ReceiverException {
        var channel = new Channel<Integer>();
        var receiver = channel.getReceiver();
        channel.getSender().close();
        receiver.receive();
    }
}