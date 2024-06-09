package com.sai;

import com.sai.model.Direction;
import com.sai.request.ExternalRequest;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class GetInputsFromUser {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12347); ;
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        Scanner sc = new Scanner(System.in);
        System.out.println(Thread.currentThread().getName());
        while (true) {
            String s = sc.nextLine();
            dos.writeUTF(s);
        }
    }
}
