package com.vaibhav.chat;

import java.net.*;
import java.io.*;

// LAN Discovery service
class DiscoveryService{
	private DatagramSocket udpSocket;
	private static final int BUFFER_SIZE = 1024;
	private static final String DISCOVERY_MESSAGE = "Discover chat server";
	
	public DiscoveryService(DatagramSocket ds){
		this.udpSocket = ds;
	}
	
	// Client sends a discovery request
	public void sendDiscoveryRequest(InetAddress address, int port) throws IOException{		
		DatagramPacket packet = new DatagramPacket(DISCOVERY_MESSAGE.getBytes(), DISCOVERY_MESSAGE.length(), address, port);
		udpSocket.send(packet);
	}
	
	// Server receives a discovery request
	public DatagramPacket receivePacket() throws IOException{
		byte[] buffer = new byte[BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		udpSocket.receive(packet);
		return packet;
	}
	
	// Client receives the server reply
	public DatagramPacket receivePacket(int timeout) throws IOException{
		byte[] buffer = new byte[BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		udpSocket.setSoTimeout(timeout);
		udpSocket.receive(packet);
		return packet;
	}
	
	// Server sends the discovery reply back to client
	public void sendDiscoveryReply(DatagramPacket request, int tcpPort) throws IOException{
		String reply = Integer.toString(tcpPort);		
		DatagramPacket packet = new DatagramPacket(reply.getBytes(), reply.length(), request.getAddress(), request.getPort());
		udpSocket.send(packet);
	}
	
	public boolean isDiscoveryRequest(DatagramPacket packet){
		String message = new String(packet.getData(), 0, packet.getLength());
		return DISCOVERY_MESSAGE.equals(message);
	}
}