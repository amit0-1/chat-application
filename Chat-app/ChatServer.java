import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private static final int PORT = 12345;
    // thread-safe list of connected clients
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("Starting ChatServer on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getRemoteSocketAddress());
                // create handler and start; the handler adds itself to clients after setup
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // broadcast to all clients
    static void broadcast(String message) {
        for (ClientHandler ch : clients) {
            ch.sendMessage(message);
        }
    }

    // remove client
    static void removeClient(ClientHandler ch) {
        clients.remove(ch);
    }

    // ClientHandler inner class
    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String name = "Anonymous";

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask client for a name
                out.println("Enter your name:");
                String requestedName = in.readLine();
                if (requestedName != null && !requestedName.trim().isEmpty()) {
                    name = requestedName.trim();
                } else {
                    name = "User" + socket.getPort();
                }

                // Now add to the global list and announce
                clients.add(this);
                System.out.println(name + " (" + socket.getRemoteSocketAddress() + ") joined.");
                broadcast("[Server] " + name + " has joined the chat.");

                // Read messages from this client and broadcast them
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    broadcast(name + ": " + line);
                }
            } catch (IOException e) {
                System.err.println("Connection error with " + name + ": " + e.getMessage());
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
                removeClient(this);
                broadcast("[Server] " + name + " has left the chat.");
                System.out.println(name + " disconnected.");
            }
        }

        // send a message to this client (thread-safe because PrintWriter is used per client)
        void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }
    }
}