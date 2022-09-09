package ru.javaops.webapp;

class AbstractSocket {
    private final String name;

    AbstractSocket(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class DataTransfer implements Runnable {
    private final AbstractSocket source;
    private final AbstractSocket destination;

    DataTransfer(AbstractSocket source, AbstractSocket destination) {
        this.source = source;
        this.destination = destination;
    }

    public void transfer(AbstractSocket source, AbstractSocket destination) {
        System.out.println("Data transfer from " + source + " to " + destination);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        synchronized (source) {
            System.out.println(threadName + " lock " + source);
            synchronized (destination) {
                System.out.println(threadName + " lock " + destination);
                transfer(source, destination);
            }
        }
    }
}

public class DeadlockMain {
    public static void main(String[] args) {
        AbstractSocket socket1 = new AbstractSocket("Socket1");
        AbstractSocket socket2 = new AbstractSocket("Socket2");

        Thread thread1 = new Thread(new DataTransfer(socket1, socket2));
        Thread thread2 = new Thread(new DataTransfer(socket2, socket1));

        thread1.start();
        thread2.start();
    }
}
