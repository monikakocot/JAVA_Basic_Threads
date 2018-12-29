package BucketThreads;

public class Main {

    public static void main(String[] args) {

        Bucket bucket = new Bucket();

        Consument consument  = new Consument("Consument", bucket);
        Producer producer = new Producer("Producer", bucket);

        consument.start();
        producer.start();
    }
}
