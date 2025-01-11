import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleHttpServer {

    public static void main(String [] args){

        final int port = 8080;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                4,
                4,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50));
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("Running Server at port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(() -> handleRequest(socket));

            }
        } catch(IOException e){
            System.out.println("Error Handling Request");
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
            Thread.sleep(1000);
            if("GET".equalsIgnoreCase(requestMethod) && "/messages".equalsIgnoreCase(path)){
                writeResponse(outputStream);
            }

        } catch (IOException e) {
            System.out.println("Filed to handle request");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket" + e.getMessage());
            }
        }
    }

    private static void writeResponse(OutputStream outputStream) throws IOException {
        String message = "Hello from Mikey";
        String httpResponse = """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                Content-Length:""" + message.length() +  "\n\n" +
                message;
        outputStream.write(httpResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
