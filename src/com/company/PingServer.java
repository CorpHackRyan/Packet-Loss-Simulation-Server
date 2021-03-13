// Ryan OConnor
// Computer Networks - Monday/Wednesday class
// Project 1 - UDP Ping Client/AServer program
// due: 3/13/21 @ 23:59

package com.company;
import java.net.*;
import java.util.*;


public class PingServer
{
    public static void main(String[] args) throws Exception
    {
        int my_port = 2014;
        DatagramSocket serverSocket = new DatagramSocket(my_port);
        byte[] receiveData = new byte[1024];

        // Processing loop.
        while (true) {

            // Create a random number generator for use in packet loss simulation
            Random random = new Random();

            // Create a datagram packet to hold incoming UDP packet.
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Get the client message (wait's for client to send packet)
            serverSocket.receive(receivePacket);


            String sentence = new String(receivePacket.getData(),0, receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int client_port = receivePacket.getPort();

            //Print out client's IP Address, port number and the message
            System.out.println("Client's port # = " + client_port);
            System.out.println("Client's IP address = " + IPAddress);
            System.out.println("Client's message = " + sentence);

            // Capitalize the message from the client
            String capitalizedSentence = sentence.toUpperCase();

            //Simulate the packet loss
            int rand = random.nextInt(10); // a random number in the range of 0-10
            System.out.print("Random number chosen was: " + rand);

            // If rand is less than 4, we consider that packet lost and do not reply
            if (rand < 4) {
                System.out.println("Reply not sent");
                continue;
            }

            //Otherwise, this server will respond - IPAddress is the client's host IP
            byte[] sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, client_port);
            serverSocket.send(sendPacket);
            System.out.println("\nReply to the client sent");



        }
    }
}