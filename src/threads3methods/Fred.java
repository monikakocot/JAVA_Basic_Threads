package threads3methods;

//https://przemelek.blogspot.com/2009/10/wait-i-notifynotifyall-najbardziej.html

public class Fred {

    public static void main(String[] args) {

        final Fred fred = new Fred();// synchronized object. We can call the Object's methods: wait (), notifyAll (), notify ().

        final Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("t1 start :-)");
                System.out.println("t1 will wait...");
                synchronized (fred) {
                    try {
                        fred.wait();
                    } catch (InterruptedException ie) {
                        System.out.println("t1 interrupted...");
                    }
                }
                System.out.println("t1 stop :-)");
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("t2 start :-)");
                synchronized (fred) {
                    System.out.println("t2 notifyAll!!!");
                    fred.notifyAll();
                    System.out.println("t2 done ;-)");
                }
                System.out.println("t2 stop :-)");
            }
        });
        t2.start();
    }

}
