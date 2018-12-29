package BucketThreads;

public class Consument  extends Thread{

    private Bucket bucket;
    private String name;

    public Consument(String name, Bucket bucket) {
        this.name = name;
        this.bucket = bucket;
    }

    public void run() { // if we implement Runable run will be @override

        while(!isInterrupted()) {

            try {
                synchronized (bucket) {
                    bucket.getProduct();
                    bucket.wait();
                    System.out.println(ThreadColor.ANSI_BLUE +  name + " buy product nr: " + bucket.getProduct());
                    this.sleep(1000);
                    bucket.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Thread has been stopped");
            }
        }
    }
}
