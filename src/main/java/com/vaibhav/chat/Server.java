package com.vaibhav.chat;

import java.net.*;
import java.io.*;

// All socket connections are created accepted using Server program 
class Server{
	public static void main(String[] s) throws IOException, InterruptedException{
		ServerSocket ss = new ServerSocket(5000);	
		
		// Creating a new UDP socket using DatagramSocket
		DatagramSocket ds = new DatagramSocket(5001);
		DiscoveryService discovery = new DiscoveryService(ds);
		
		// Receiving a UDP packet as DatagramPacket from the client
		DatagramPacket packet = discovery.receivePacket();
		
		// Sending a UDP packet as DatagramPacket to the client if the client packet is verified
		if(discovery.isDiscoveryRequest(packet))
			discovery.sendDiscoveryReply(packet, ss.getLocalPort());
		
		ds.close();		
		Socket server = ss.accept();
		
		// Socket input stream
		InputStream in = server.getInputStream();
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(in));
		
		// Socket output stream
		OutputStream out = server.getOutputStream();
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		OutputStreamWriter osw = new OutputStreamWriter(out);
		BufferedWriter socketWriter = new BufferedWriter(osw);
		
		ConnectionState state = new ConnectionState();
		
		Sender sender = new Sender(keyboard, socketWriter, state);
		Receiver receiver = new Receiver(socketReader, "Client", state);
		
		Thread senderThread = new Thread(sender);
		Thread receiverThread = new Thread(receiver);
		
		senderThread.start();
		receiverThread.start();
		
		senderThread.join();

		// Closes the socket and hence the input stream on which the local Receiver thread is listening and is blocked				
		server.close();
		
		receiverThread.join();		
		ss.close();
	}
}