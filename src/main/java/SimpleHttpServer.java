import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    public static void main(String [] args){

        final int port = 808;
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            Socket socket = serverSocket.accept();
            handleRequest(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    }

}
