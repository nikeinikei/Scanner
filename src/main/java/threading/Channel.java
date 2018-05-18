package threading;

import java.util.LinkedList;
import java.util.Queue;


public class Channel<T> {
    private Queue<T> queue = new LinkedList<>();
    private Sender sender = new Sender(this);
    private Receiver receiver = new Receiver(this);

    private boolean active = true;

    void close() {
        active = false;
    }

    public Sender getSender() {
        return sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    private synchronized void send(T t) throws SenderException {
        if (!active)
            throw new SenderException();

        queue.add(t);

        this.notify();
    }

    private synchronized T receive() throws ReceiverException {
        if (!active)
            throw new ReceiverException();

        while (queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            channel.active = false;
        }
    }

    public class Receiver {
        private Channel<T> channel;

        Receiver(Channel<T> channel) {
            this.channel = channel;
        }

        @SuppressWarnings("UnusedReturnValue")
        public T receive() throws ReceiverException {
            return channel.receive();
        }
    }
}
