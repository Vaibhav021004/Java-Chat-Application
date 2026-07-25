package com.vaibhav.chat;

import java.io.*;

class Receiver implements Runnable{
	BufferedReader socketReader;
	String senderName;
	ConnectionState state;
	
	public Receiver(BufferedReader socketReader, String senderName, ConnectionState state){
		this.socketReader = socketReader;
		this.senderName = senderName;
		this.state = state;
	}
	
	public void run(){
		try{
			while(true){
				String message = socketReader.readLine();
				
				
				if(message == null || message.equalsIgnoreCase("exit")){
					state.disconnect();
					System.out.println("\n" + senderName + " disconnected.");
					break;
				}
				
				System.out.println("\n" + senderName + ": " + message);
			}
		}
		catch(IOException e){
			// Socket closed during shutdown			
		}
	}	
}
