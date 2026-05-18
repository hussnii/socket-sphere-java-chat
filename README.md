# socket-sphere-java-chat
# SocketSphere 🚀
### Real-Time Multi-Client TCP Chat Application in Java

SocketSphere is a Java-based real-time chat application built using:

- Java Socket Programming
- TCP/IP Communication
- Java Swing GUI
- Multithreading
- Private Messaging System

This project demonstrates how client-server communication works using TCP sockets in Java.

---

# Features ✨

✅ Multi-client chat support  
✅ Real-time message broadcasting  
✅ Private messaging using @username  
✅ Swing graphical user interface  
✅ Server-side broadcasting  
✅ Username system  
✅ Online user notifications  
✅ Join/leave notifications  
✅ Timestamped messages  
✅ Multithreaded client handling  

---

# Technologies Used 🛠️

- Java
- Java Swing
- TCP/IP
- Socket Programming
- Multithreading
- Collections Framework

---

# How It Works ⚙️

## Server
The server:
- Listens on port `5000`
- Accepts multiple client connections
- Creates a separate thread for each client
- Broadcasts messages to all connected users
- Supports private messaging

## Client
The client:
- Connects to the server
- Opens a GUI chat window
- Sends and receives messages in real time
- Supports private chat commands

---

# Private Messaging 📩

To send a private message:

```text
@username your_message
```

Example:

```text
@john Hello John!
```

---

# Project Structure 📁

```text
src/
└── TCP_IP_CONNECTION/
    ├── TCPServer1.java
    └── TCPClient1.java
```

---

# How to Run ▶️

## 1. Start the Server

Run:

```bash
TCPServer1.java
```

Console output:

```text
Server started...
```

---

## 2. Start Clients

Run:

```bash
TCPClient1.java
```

Enter a username when prompted.

---

# Example Chat 💬

```text
[12:30:15] Alex: Hello everyone!
[12:30:20] Sara: Hi Alex!
📩 (Private) [12:30:25] John: Secret message
```

---

# Concepts Demonstrated 📚

This project demonstrates:

- TCP Communication
- Client-Server Architecture
- Socket Programming
- Concurrent Programming
- GUI Development
- Event Handling
- Thread Synchronization

---

# Future Improvements 🚀

- File sharing
- Emoji support
- Voice chat
- Chat rooms
- User authentication
- Database integration
- End-to-end encryption

---

# Author 

Hussnia Mohammed

---

# License 📄

This project is open-source and free to use for educational purposes.
