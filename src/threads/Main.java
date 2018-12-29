package threads;

public class Main {

// Start the main thread.
    public static void main(String[] args) {
        System.out.println(ThreadColor.ANSI_BLUE + "Hello world: " + Thread.currentThread().getName() );

// Not synchronized object CountDown
        Countdown countdown = new Countdown();
// Here we push CountDown Object to Threads
        CountDawnThread t1 = new CountDawnThread(countdown);
        t1.setName("Thread 1");
        CountDawnThread t2 = new CountDawnThread(countdown);
        t2.setName("Thread 2");

// Start child threads.
        t1.start();
        t2.start();

      try {
            Thread.sleep(25000);
            System.out.println("The end of thread main");
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        //System.out.println("The end of thread main");
    }
}

    class Countdown {
        private int i;
        public void doCountdown() {
            String color;
            System.out.println("NOT SYNCHRONIZED VERSION");

            switch(Thread.currentThread().getName()) {
            case "Thread 1":
            color = ThreadColor.ANSI_CYAN;
            break;
            case "Thread 2":
            color = ThreadColor.ANSI_PURPLE;
            break;

            default: color = ThreadColor.ANSI_GREEN;
            }

            for ( i = 10; i > 0 ; i--) {
            System.out.println(color + Thread.currentThread().getName() + ": " + i );
            }
        }

        public /*synchronized*/ void doCountdownSynchro() {
            String color;


            switch(Thread.currentThread().getName()) {
                case "Thread 1":
                    color = ThreadColor.ANSI_CYAN;
                    break;
                case "Thread 2":
                    color = ThreadColor.ANSI_PURPLE;
                    break;

                default: color = ThreadColor.ANSI_GREEN;
            }

            synchronized (this) {
                System.out.println("SYNCHRONIZED VERSION");
                for ( i = 10; i > 0 ; i--) {
                    System.out.println(color + Thread.currentThread().getName() + ": " + i );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

class CountDawnThread extends Thread {
    private Countdown threadCountdown;

    public CountDawnThread(Countdown countDawn) {
        threadCountdown = countDawn;
    }

    public void run() {
        //threadCountdown.doCountdown();
        threadCountdown.doCountdownSynchro();
    }
}
