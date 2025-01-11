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

    private static void handleRequest(Socket socket){
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()){

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            String [] parts =  line.split(" ");
            String requestMethod = parts[0];
            String path = parts[1];

            if("GET".equalsIgnoreCase(requestMethod) && "/messages".equalsIgnoreCase(path)){

            }

        } catch (IOException e) {
            System.out.println("Filed to handle request");
        }
    }

}
