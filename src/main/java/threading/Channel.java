package threading;

import java.util.LinkedList;
import java.util.Queue;


public class Channel<T> {
    /**
     * the time in milliseconds after the Receiver will retry receiving again.
     * TODO adjust this time
     */
    private static final long RETRY_TIME = 2;

    /**
     * the queue of items
     */
    private Queue<T> queue = new LinkedList<>();

    /**
     * the sender object
     */
    private Sender sender = new Sender(this);

    /**
     * the receiver object
     */
    private Receiver receiver = new Receiver(this);

    /**
     * It is only possible to send receive onto this channel if it is active.
     * Otherwise it will throw Sender- and ReceiverExceptions in their respective classes
     */
    private boolean active = true;

    private synchronized void close() {
        this.active = false;
    }

    /**
     * @return this#sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * @return this#receiver
     */
    public Receiver getReceiver() {
        return receiver;
    }

    private synchronized void send(T t) throws SenderException {
        if (!active)
            throw new SenderException();

        queue.add(t);
    }

    private synchronized T receive() throws ReceiverException {
        if (!active)
            throw new ReceiverException();

        while (queue.isEmpty()) {
            try {
                this.wait(RETRY_TIME);
            } catch (InterruptedException ignored) {
            }
            if (!active)
                throw new ReceiverException();
        }

        return queue.poll();
    }

    public class Sender {
        private Channel<T> channel;

        Sender(Channel<T> channel) {
            this.channel = channel;
        }

        public void send(T t) throws SenderException {
            channel.send(t);
        }

        public void close() {
            channel.close();
        }
    }

    public class Receiver {
        private Channel<T> channel;

        Receiver(Channel<T> channel) {
            this.channel = channel;
        }

        public T receive() throws ReceiverException {
            return channel.receive();
        }

        public void close() {
            channel.close();
        }
    }
}
