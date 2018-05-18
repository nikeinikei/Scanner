import org.junit.Assert;
import org.junit.Test;
import threading.Channel;
import threading.ReceiverException;
import threading.SenderException;


public class ThreadingTest {
    @Test(expected = ReceiverException.class)
    public void testReceiveOnClosedSender() throws ReceiverException {
        var channel = new Channel<Integer>();
        var receiver = channel.getReceiver();
        channel.getSender().close();
        receiver.receive();
    }

    @Test
    public void test() throws ReceiverException {
        var data = new int[]{1, 2, 3, 4, 5};
        var channel = new Channel<Integer>();
        var sender = channel.getSender();
        var receiver = channel.getReceiver();
        new Thread(() -> {
            try {
                for (var i : data)
                    sender.send(i);
            } catch (SenderException e) {
                e.printStackTrace();
            }
        }).start();
        int[] received = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            received[i] = receiver.receive();
        }
        Assert.assertArrayEquals(received, data);
    }
}