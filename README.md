# Java Chat Application

A console-based chat application built in Java using TCP sockets, multithreading, and Java I/O streams. The project is being developed incrementally with the goal of understanding low-level network programming, I/O streams and multithreading before moving to graphical user interfaces.

The application currently supports real-time two-way communication between a client and a server over TCP and includes the foundation for automatic server discovery using UDP.

---

## Features

### Implemented

* Full-duplex client-server communication using TCP sockets
* Independent sender and receiver threads
* Graceful connection termination
* Shared connection state between threads
* Character stream communication using `BufferedReader` and `BufferedWriter`
* Modular design with separate networking components
* Initial UDP-based server discovery implementation

### Planned

* Automatic LAN server discovery using UDP broadcast
* File transfer using Java I/O streams
* Progress indication for file transfers
* Improved error handling and connection recovery
* Support for communication across different networks
* Graphical user interface using Swing or JavaFX

---

## Project Structure

```text
Java-Chat-Application
│
├── Client.java
├── Server.java
├── Sender.java
├── Receiver.java
├── ConnectionState.java
└── DiscoveryService.java
```

| Class              | Responsibility                                           |
| ------------------ | -------------------------------------------------------- |
| `Client`           | Discovers the server and establishes the TCP connection  |
| `Server`           | Waits for client connections and starts the chat session |
| `Sender`           | Reads user input and sends messages                      |
| `Receiver`         | Receives incoming messages                               |
| `ConnectionState`  | Maintains shared connection status between threads       |
| `DiscoveryService` | Handles UDP-based server discovery                       |

---

## Technologies Used

* Java
* TCP Sockets
* UDP Sockets
* Multithreading
* Java Networking (`java.net`)
* Java I/O (`java.io`)

---

## How It Works

### Server

1. Creates a TCP server socket.
2. Waits for discovery requests.
3. Responds with its TCP port.
4. Accepts an incoming TCP connection.
5. Starts sender and receiver threads.

### Client

1. Sends a UDP discovery request.
2. Receives the server's response.
3. Establishes a TCP connection.
4. Starts sender and receiver threads.

---

## Running the Application

### Start the Server

```bash
javac *.java
java Server
```

### Start the Client

```bash
java Client
```

> **Note**
>
> The current implementation is intended for local testing while the automatic LAN discovery feature is under development.

---

## Current Limitations

* Supports one client at a time.
* Console-based user interface.
* File transfer is not yet implemented.
* Automatic LAN discovery is currently under development.
* Communication across different networks is not yet supported.

---

## Learning Objectives

This project is primarily intended as a learning exercise in Java network programming. It focuses on understanding:

* TCP socket communication
* UDP-based service discovery
* Java character streams
* Multithreading
* Thread coordination
* Graceful shutdown of network applications
* Client-server architecture

The emphasis is on understanding the underlying networking concepts before introducing higher-level frameworks or graphical user interfaces.

---

## License

This project is intended for educational purposes.
