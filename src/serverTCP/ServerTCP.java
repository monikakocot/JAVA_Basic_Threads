package serverTCP;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP  extends Thread{

    private int serverPort ;

    public ServerTCP(String name, int port) {
        this.serverPort = serverPort;
        setName(name);
        serverPort = port;
    }
    public void run() {

        ServerSocket serverSocket = null;//we need it to stream communication peer-to-peer client-server.
        Socket connectedSocket = null;
        InputStream imputStream = null;
        OutputStream outputStream = null;

        String responseMessage =
                "HTTP/1.1 200 OK\r\n\r\n"
                + "<!DOCTYPE html>"
                + "<html><head></body>"
                + "<br/><center> <b> Witaj na serwerze TCP Akademii Kodu :) </br><center>"
                + "</body></html>";

        byte[] response = responseMessage.getBytes();
        byte[] buffer = new byte[2048];

        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!this.isInterrupted()) {

            try {
                connectedSocket = serverSocket.accept();
                ConectedTCP myThread = new ConectedTCP("Server");
                myThread.start();
                imputStream = connectedSocket.getInputStream();
                outputStream = connectedSocket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                outputStream.write(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int counter = imputStream.read(buffer);
                if(counter > 0) {
                    String testMessage = new String(buffer, 0, buffer.length);
                    System.out.println(testMessage);
                }
            } catch (IOException e) {

            }
            try {
                outputStream.flush(); // empties the output stream and forces the buffer data to be saved.
                outputStream.close();
                imputStream.close();
                connectedSocket.close();

              /*  if(serverSocket != null) {
                    serverSocket.close();
                }*/

            } catch (IOException e) {

            }
        }
    }
}
