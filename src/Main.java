class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
        System.out.println("Incremented: " + count);
        notifyAll();
    }


    public synchronized void decrement() {
        count--;
        System.out.println("Decremented: " + count);
        notifyAll();
    }


    public synchronized int getCount() {
        return count;
    }


    public synchronized void waitAndIncrement() {
        while (count % 2 != 0) {
            try {
                System.out.println("Waiting for even count: " + count);
                wait();
            } catch (InterruptedException e) {

                System.out.println("Thread interrupted");
            }
        }

        increment();
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread incrementThread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread incrementThread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread incrementThread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread decrementThread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        Thread decrementThread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        Thread waitAndIncrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.waitAndIncrement();
            }
        });


        incrementThread1.start();
        incrementThread2.start();
        incrementThread3.start();
        decrementThread1.start();
        decrementThread2.start();
        waitAndIncrementThread.start();

        waitAndIncrementThread.join();
        incrementThread1.join();
        incrementThread2.join();
        incrementThread3.join();
        decrementThread1.join();
        decrementThread2.join();


        System.out.println("Final count: " + counter.getCount());
    }
}
