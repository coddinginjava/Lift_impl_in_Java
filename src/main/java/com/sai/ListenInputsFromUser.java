package com.sai;

import com.sai.model.Direction;
import com.sai.request.ExternalRequest;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class ListenInputsFromUser implements Runnable{

    ServerSocket serverSocket = new ServerSocket(12347);
    Socket clientSocket = serverSocket.accept();
    InputStream inputStream = clientSocket.getInputStream();
    DataInputStream dis = new DataInputStream(inputStream);


    private PriorityBlockingQueue<ExternalRequest> externalRequests ;
    private PriorityBlockingQueue<Integer> internalRequest;

    public ListenInputsFromUser(Scanner sc, PriorityBlockingQueue<ExternalRequest> externalRequests, PriorityBlockingQueue<Integer> internalRequest) throws IOException {
        this.externalRequests = externalRequests;
        this.internalRequest = internalRequest;
    }

    @Override
    public void run() {
        System.out.println("Server is started to get inputs");

        while (true) {
            try {
                String s = dis.readUTF();

                if(s.split(" ").length == 2) {

                    int floorNumber  = Integer.parseInt(s.split(" ")[1]);
                    internalRequest.add(floorNumber);
                    System.out.println("Received Internal Request to go to : " + floorNumber);

                }else if (s.split(" ").length == 3) {

                    int floorNumber  = Integer.parseInt(s.split(" ")[1]);
                    Direction d = Direction.valueOf(s.split(" ")[2]);
                    externalRequests.add(new ExternalRequest(floorNumber,d));
                    System.out.println("Received External Request in " + floorNumber + " floor to go in " + d.toString());

                }else {
                    System.out.println("unknown input please try again");
                }


            } catch (Exception e) {
                System.err.println("Invalid Input or something went wrong => "+ e.getMessage());
            }

        }
    }
}
