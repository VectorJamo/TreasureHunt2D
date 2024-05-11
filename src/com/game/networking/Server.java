package com.game.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.game.entities.AnotherPlayer;
import com.game.states.GameState;

public class Server implements Runnable {
	
	public static final int PORT = 10000;
	private static DatagramSocket serverSocket;
	
	public static InetAddress clientAddress;
	public static int clientPort = -1;
	
	private Thread networkingThread;
	
	public static int clientX = 0, clientY = 0;
	public static String clientDirection = "D";

	public Server() {
		try {
			serverSocket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		networkingThread = new Thread(this);
		networkingThread.start();
		
		System.out.println("Networking thread started.");
		try {
			System.out.println("Server local ip: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("Server port: " + PORT);
	}
	
	public static void sendPositionInfoToClient(int worldX, int worldY, String direction) {
		String sendMessage = Integer.toString(worldX) + "," + Integer.toString(worldY) + "," + direction;
		byte[] sendDataBuffer = sendMessage.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendDataBuffer, sendDataBuffer.length, clientAddress, clientPort);
		
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendConnectionResponseMessage() {
		String sendMessage = "CON-RES";
		byte[] sendDataBuffer = sendMessage.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendDataBuffer, sendDataBuffer.length, clientAddress, clientPort);
		
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			byte[] dataBuffer = new byte[1024];
			DatagramPacket incomingPacket = new DatagramPacket(dataBuffer, dataBuffer.length);
			
			try {
				// Wait until a message comes through the server's socket
				serverSocket.receive(incomingPacket);
				
				// Extract information from the incoming packet
				String message = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
				clientAddress = incomingPacket.getAddress();
				clientPort = incomingPacket.getPort();
				
				if(message.equals("CON-REQ")) {
					System.out.println("New client connected.");
					System.out.println("Client IP: " + clientAddress.getHostAddress());
					System.out.println("Client Port: " + clientPort);
					System.out.println("Client message: " + message);
					
					GameState.connectionRequestArrived = true;
				}else {
					// Retrieve the position data coming from another player
					String[] positions = message.split(",");
					clientX = Integer.parseInt(positions[0]);
					clientY = Integer.parseInt(positions[1]);
					clientDirection = positions[2];
					
					System.out.println(clientX + ", " + clientY);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
