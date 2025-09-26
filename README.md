# Java Chat Application 💬

A simple **multi-client chat application** built using **Java Sockets**.  
This project demonstrates **network programming, client-server architecture, and message broadcasting**.  
It is part of **Level 3 – Task 1** of the Java Development Learning Path.

---

## 🚀 Features
- Multi-client chat using **TCP Sockets**
- Server handles multiple clients using **threads**
- Broadcasts messages to all connected clients
- Join/leave notifications
- Simple **console-based** client
- Command: `/quit` to exit chat

---

## 📂 Project Structure
```
chat-app/
│── ChatServer.java   # Server code
│── ChatClient.java   # Client code
README.md         # Documentation
```

---

## 🛠️ Prerequisites
- Install **Java JDK 8+**
- Basic knowledge of running Java programs from terminal

---

## ⚡ How to Run

### 1️⃣ Compile the code
```bash
javac ChatServer.java ChatClient.java
```

### 2️⃣ Start the Server
```bash
java ChatServer
```
Server starts on port **12345** by default.

### 3️⃣ Start Clients
In separate terminals, run:
```bash
java ChatClient
```
or specify host & port:
```bash
java ChatClient localhost 12345
```

---

## 🖥️ Example Usage

**Server Output**
```
Starting ChatServer on port 12345
New connection from /127.0.0.1:54321
Amit (/127.0.0.1:54321) joined.
Ria (/127.0.0.1:54322) joined.
```

**Client 1**
```
Enter your name: Amit
[Server] Amit has joined the chat.
You can now type messages. Type /quit to exit.
Hello everyone
```

**Client 2**
```
Enter your name: Ria
[Server] Amit has joined the chat.
[Server] Ria has joined the chat.
Hi Amit!
```

---

## 🧩 Future Enhancements
- ✅ Private messaging (`@username`)
- ✅ Chat history logging
- ✅ JavaFX/Swing GUI client
- ✅ Authentication system
- ✅ NIO-based server (non-blocking, scalable)

---

## 📚 Learning Outcomes
- Understand **TCP socket programming** in Java
- Implement a **multi-threaded server**
- Learn about **client-server message exchange**
- Strengthen knowledge of **network programming**

---

## 🤝 Contributing
1. Fork this repository  
2. Create a feature branch (`feature/my-new-feature`)  
3. Commit your changes (`git commit -m "Add new feature"`)  
4. Push to branch (`git push origin feature/my-new-feature`)  
5. Create a Pull Request 🚀
