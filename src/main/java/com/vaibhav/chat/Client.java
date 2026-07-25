package com.vaibhav.chat;

import java.io.*;
import java.net.*;

// All individual connections to the server are made using Client program
class Client{
	public static void main(String[] s) throws IOException, InterruptedException{
		// Creating a new UDP socket using DatagramSocket
		DatagramSocket ds = new DatagramSocket();
		DiscoveryService discovery = new DiscoveryService(ds);
		
		// Broadcasting a UDP packet via the DatagramSocket for server discovery
		discovery.sendDiscoveryRequest(InetAddress.getLocalHost(), 5001);
		
		// Receiving a UDP packet as DatagramPacket from the server
		DatagramPacket reply = discovery.receivePacket(10000);
		
		// Opening a socket and establishing a TCP/IP connection with the server
		String message = new String(reply.getData(), 0, reply.getLength());
		int tcpPort = Integer.parseInt(message);
		Socket client = new Socket(reply.getAddress(), tcpPort);
		ds.close();
		
		// Socket output stream		
		OutputStream out = client.getOutputStream();
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		OutputStreamWriter osw = new OutputStreamWriter(out);
		BufferedWriter socketWriter = new BufferedWriter(osw);
		
		// Socket input stream		
		InputStream in = client.getInputStream();
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(in));

		ConnectionState state = new ConnectionState();
		
		Sender sender = new Sender(keyboard, socketWriter, state);
		Receiver receiver = new Receiver(socketReader, "Server", state);
		
		Thread senderThread = new Thread(sender);
		Thread receiverThread = new Thread(receiver);
		
		senderThread.start();
		receiverThread.start();
		
		senderThread.join();
		
		// Closes the socket and hence the input stream on which the local Receiver thread is listening and is blocked		
		client.close();
		
		receiverThread.join();		
	}
}
		
