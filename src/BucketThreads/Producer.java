package BucketThreads;

public class Producer  extends Thread{

    private long  id= 1;
    //private long tmpId = 1;
    private Bucket bucket;
    private String name;
    private int counter;

    public Producer(String name, Bucket bucket) {
        this.name = name;
        this.bucket = bucket;
        this.counter = 1;
    }

    public int makingProduct() {
        return counter++;
    }

    public long getId() {
        return id;
    }

  /*  public long getImpId() {
        return tmpId;
    }*/

    public void run() {
        try {
        while(!isInterrupted()) {
                    System.out.println(ThreadColor.ANSI_GREEN +   name + " produced product nr: " + counter);

                this.sleep(1000);
                    synchronized (bucket) {
                        bucket.notifyAll();
                        bucket.setProduct(makingProduct());
                        bucket.wait();
                    }
        } } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Thread has been stopped");
        }
    }
}
