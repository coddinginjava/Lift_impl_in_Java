package com.sai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class SampleMain {

    public static void main(String[] args) throws IOException {


        PriorityBlockingQueue<Integer> internalRequest = new PriorityBlockingQueue<>(10, (a, b) -> a - b);
        internalRequest.add(2);
        internalRequest.add(5);



    }

}
