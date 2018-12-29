package serverTCP;

public class Main {

    public static void main(String[] args) {

        ServerTCP server8081 = new ServerTCP("server8081", 8081);
        server8081.start();
    }
}
