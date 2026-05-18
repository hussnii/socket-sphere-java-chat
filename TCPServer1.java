package TCP_IP_CONNECTION;



import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TCPServer1 {

    private static final int PORT = 5000;
    private static List<ClientHandler> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        System.out.println("Server started...");

        // SERVER INPUT THREAD
        new Thread(() -> {
            BufferedReader console =
                    new BufferedReader(new InputStreamReader(System.in));

            String msg;
            try {
                while ((msg = console.readLine()) != null) {
                    broadcast("[SERVER " + getTime() + "] " + msg);
                }
            } catch (IOException e) {
                System.out.println("Server input error");
            }
        }).start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }

        } catch (IOException e) {
            System.out.println("❌ Port already in use. Close previous server or change port.");
        }
    }

    // ===== BROADCAST =====
    public static void broadcast(String message) {
        synchronized (clients) {
            Iterator<ClientHandler> it = clients.iterator();

            while (it.hasNext()) {
                ClientHandler client = it.next();
                try {
                    client.out.println(message);
                } catch (Exception e) {
                    it.remove();
                }
            }
        }
    }

    // ===== TIME =====
    private static String getTime() {
        DateTimeFormatter f =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalDateTime.now().format(f);
    }

    // ===== CLIENT HANDLER =====
    static class ClientHandler implements Runnable {

        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {

            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter username:");
                username = in.readLine();

                if (username == null || username.trim().isEmpty()) {
                    username = "User" + new Random().nextInt(1000);
                }

                sendExistingUsers();
                broadcast("🔵 " + username + " joined");

                String msg;

                while ((msg = in.readLine()) != null) {

                    if (msg.startsWith("@")) {
                        privateMessage(msg);
                    } else {
                        broadcast(format(username, msg));
                    }
                }

            } catch (IOException e) {
                System.out.println(username + " disconnected");
            } finally {
                disconnect();
            }
        }

        private void sendExistingUsers() {
            synchronized (clients) {
                for (ClientHandler c : clients) {
                    if (c != this && c.username != null) {
                        out.println("👤 Online: " + c.username);
                    }
                }
            }
        }

        private void privateMessage(String msg) {
            int space = msg.indexOf(" ");
            if (space == -1) return;

            String target = msg.substring(1, space);
            String message = msg.substring(space + 1);

            boolean found = false;

            synchronized (clients) {
                for (ClientHandler c : clients) {
                    if (c.username != null && c.username.equals(target)) {
                        c.out.println("📩 (Private) " +
                                format(username, message));
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                out.println("❌ User not found");
            }
        }

        private String format(String user, String msg) {
            DateTimeFormatter f =
                    DateTimeFormatter.ofPattern("HH:mm:ss");

            return "[" + LocalDateTime.now().format(f) + "] "
                    + user + ": " + msg;
        }

        private void disconnect() {
            try {
                if (username != null) {
                    broadcast("🔴 " + username + " left");
                }

                synchronized (clients) {
                    clients.remove(this);
                }

                socket.close();

            } catch (IOException e) {
                System.out.println("Error closing socket");
            }
        }
    }
}