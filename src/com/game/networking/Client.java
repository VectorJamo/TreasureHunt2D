package com.game.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.game.Game;
import com.game.states.GameState;

public class Client implements Runnable {
	
	private Game game;
	
	private static DatagramSocket clientSocket;
	
	public static InetAddress serverAddress;
	public static int serverPort = 10000;
	
	private Thread networkingThread;
	
	public static int serverX = 0, serverY = 0;
	public static String serverDirection = "D";
	
	public Client(Game game) {
		this.game = game;
		
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		try {
			serverAddress = InetAddress.getByName("68.148.244.189");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		networkingThread = new Thread(this);
		networkingThread.start();
		
		System.out.println("Networking thread started.");
	}
	
	public static void sendConnectionRequestMessage() {
		String sendMessage = "CON-REQ";
		byte[] sendDataBuffer = sendMessage.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendDataBuffer, sendDataBuffer.length, serverAddress, serverPort);
		
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendPositionInfoToServer(int worldX, int worldY, String direction) {
		String sendMessage = Integer.toString(worldX) + "," + Integer.toString(worldY) + "," + direction;
		byte[] sendDataBuffer = sendMessage.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendDataBuffer, sendDataBuffer.length, serverAddress, serverPort);
		
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true) {
			byte[] dataBuffer = new byte[1024];
			DatagramPacket incomingPacket = new DatagramPacket(dataBuffer, dataBuffer.length);
			
			try {
				// Wait until a message comes through the server's socket
				clientSocket.receive(incomingPacket);
				
				// Extract information from the incoming packet
				String message = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
				
				if(message.equals("CON-RES")) {
					System.out.println("Connection with remote server initiated.");
					System.out.println("Server message: " + message);
					
					// TODO: Change state from join state to game state
					GameState.connectionEstablishedWithServer = true;
					Game.setCurrentState(new GameState(game));
				}else {
					// Retrieve the position data coming from another player
					String[] positions = message.split(",");
					serverX = Integer.parseInt(positions[0]);
					serverY = Integer.parseInt(positions[1]);
					serverDirection = positions[2];
					
					System.out.println(serverX + ", " + serverY);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
}

