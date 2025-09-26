import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private final String host;
    private final int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to chat server " + host + ":" + port);

            // Read initial prompt (server asks for name)
            String prompt = serverIn.readLine(); // "Enter your name:"
            if (prompt != null) System.out.print(prompt + " ");
            String name = scanner.nextLine();
            serverOut.println(name); // send name to server

            // Thread to continuously read messages from server and print to console
            Thread readerThread = new Thread(() -> {
                String lineFromServer;
                try {
                    while ((lineFromServer = serverIn.readLine()) != null) {
                        System.out.println(lineFromServer);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            // Main loop: read user input and send to server
            System.out.println("You can now type messages. Type /quit to exit.");
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                serverOut.println(message);
                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }
            }
            System.out.println("Bye!");
        } catch (IOException e) {
            System.err.println("Unable to connect to server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String host = args.length >= 1 ? args[0] : "localhost";
        int port = args.length >= 2 ? Integer.parseInt(args[1]) : 12345;
        new ChatClient(host, port).start();
    }
}