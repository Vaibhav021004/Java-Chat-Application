package com.vaibhav.chat;

import java.io.*;

class Sender implements Runnable{
	BufferedReader keyboard;
	BufferedWriter socketWriter;
	ConnectionState state;
	
	public Sender(BufferedReader keyboard, BufferedWriter socketWriter, ConnectionState state){
		this.keyboard = keyboard;
		this.socketWriter = socketWriter;
		this.state = state;
	}
	
	public void shutdown() throws IOException{
		keyboard.close();
		socketWriter.close();
	}
	
	public void run(){
		try{
			while(true){
				System.out.print("Your message: ");
				String message = keyboard.readLine();
				
				if(message.trim().isEmpty())
					continue;
				
				if(!state.isConnected()){
					if(message.equalsIgnoreCase("exit")){
						shutdown();
						break;
					}
					
					System.out.println("The other side has disconnected.");
					System.out.println("Type 'exit' to close this chat.");
					continue;
				}
				
				socketWriter.write(message);
				socketWriter.newLine();
				socketWriter.flush();
				
				if(message.equalsIgnoreCase("exit")){
					shutdown();
					break;
				}
			}
		}
		catch(IOException e){
			
		}
			
	}	
}
