import org.junit.Test;
import threading.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.*;


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