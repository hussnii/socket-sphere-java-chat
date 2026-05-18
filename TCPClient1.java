package TCP_IP_CONNECTION;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class TCPClient1 {

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter out;
    private BufferedReader in;

    public TCPClient1() {
        buildGUI();
        connectToServer();
    }

    private void buildGUI() {
        frame = new JFrame("Chat Client");
        frame.setSize(450, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(chatArea);

        inputField = new JTextField();
        JButton sendBtn = new JButton("Send");

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost",5000 );

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(
                    socket.getOutputStream(), true);

            // USERNAME INPUT
            String username = JOptionPane.showInputDialog(frame, "Enter username:");
            if (username == null || username.trim().isEmpty()) {
                username = "Guest";
            }
            out.println(username);

            // RECEIVE THREAD
            new Thread(() -> {
                try {
                    String msg;

                    while ((msg = in.readLine()) != null) {

                        String message = msg; // ✅ FIXED (final copy)

                        SwingUtilities.invokeLater(() -> {
                            chatArea.append(message + "\n");
                            chatArea.setCaretPosition(
                                    chatArea.getDocument().getLength());
                        });
                    }

                } catch (IOException e) {
                    SwingUtilities.invokeLater(() ->
                            chatArea.append("Disconnected\n"));
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "❌ Connection failed");
        }
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();

        if (!msg.isEmpty()) {
            out.println(msg);
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TCPClient1::new);
    }
}