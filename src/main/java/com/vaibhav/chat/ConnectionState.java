package com.vaibhav.chat;

// Holds the connection state of the remote machine or user
class ConnectionState{
	private volatile boolean connected = true;
	
	public boolean isConnected(){
		return connected;
	}
	
	public void disconnect(){
		connected = false;
	}
}