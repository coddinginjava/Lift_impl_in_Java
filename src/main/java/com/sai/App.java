package com.sai;

import com.sai.model.Direction;
import com.sai.request.ExternalRequest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class App {
    static int cap = 100;

    static PriorityBlockingQueue<ExternalRequest> externalRequests = new PriorityBlockingQueue<>(cap, Comparator.comparing(ExternalRequest::getFloorNumber));
    static PriorityBlockingQueue<Integer> internalRequest = new PriorityBlockingQueue<>(cap, (a, b) -> a - b);
    static Direction currentDirection = Direction.UP;
    static int currentFloorNumber = 0;
    static int DELAY_FOR_ELEVATOR = 2 ; // in sec

    public static void main(String[] args) throws IOException {
        App app = new App();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("format for Internal Request is : I  {floor number}");
        System.out.println("format for External Request is : E {pressed on which floor number} {direction}");

        Scanner scanner = new Scanner(System.in);


        // creating separate thread to get the inputs simultaneously
        ListenInputsFromUser inputs = new ListenInputsFromUser(scanner, externalRequests, internalRequest);
        Thread thread = new Thread(inputs);
        thread.start();



        while (true) {

            if (InternalRequestIsNotEmpty(internalRequest)) {
                app.moveElevator();
            }
            if (ExternalRequestIsNotEmpty(externalRequests)) {

                System.out.println("External");
                System.out.println("Elevator Operations : External Request : " + externalRequests.poll());

            }
        }

    }

    public void moveElevator(){

        if(currentDirection == Direction.UP){
            while(InternalRequestIsNotEmpty(internalRequest)){
                Integer currentInternalRequest = internalRequest.poll();

                for(int i = currentFloorNumber ; i <= currentInternalRequest ; i ++){
                    createDelay();
                    printCurrentFloorNumber();

                    if(checkWhetherTheCurrentFloorHasAnyExternalRequestInSameDirection()
                            || currentFloorNumber == currentInternalRequest
                            || checkWhetherTheCurrentFloorMatchWithInternalRequest()){
                        elevatorStopped();
                    }
                    currentFloorNumber++;
                }
            }

        }











        else if(currentDirection == Direction.DOWN){

        }else {
            //lets see
        }


    }

    public static boolean checkWhetherTheCurrentFloorMatchWithInternalRequest(){
        Iterator<Integer> iterator = internalRequest.iterator();

        while(iterator.hasNext()){
            Integer currentInternalRequest = iterator.next();
            if(currentInternalRequest == currentFloorNumber){
                return true;
            }
        }
        return false;

    }

    public static boolean checkWhetherTheCurrentFloorHasAnyExternalRequestInSameDirection(){

        Iterator<ExternalRequest> iterator = externalRequests.iterator();
        while(iterator.hasNext()){
            ExternalRequest externalRequest = iterator.next();
            if(externalRequest.getFloorNumber() == currentFloorNumber && externalRequest.getDirection() == currentDirection){
                return true;
            }
        }
        return false;
    }


    public static void printMessage(){

    }

    public static void elevatorStopped(){
        System.out.println("Elevator stopped at Floor Number : " + currentFloorNumber + " , Please get down or get in the elevator");
    }

    public static void printCurrentFloorNumber(){
        System.out.println("The current floor number is : " + currentFloorNumber);
    }

    public static void createDelay(){
        try {
            Thread.sleep(DELAY_FOR_ELEVATOR * 1000);
        } catch (InterruptedException e) {
            System.out.println("Nothing happened");
        }
    }


//        System.exit(0);
//
//        Direction d = Direction.UP;
//        int currentFloor = 0;
//        int maxFloor = 3;
//        int minFloor = -1;
//
//
//
//
//
//
//
//
//
//        while(true){
//
//            if(d == Direction.UP){
//                if(currentFloor >= maxFloor){
//                    d = Direction.DOWN;
//                    changeToDownDirection();
//                }else {
//                    currentFloor ++;
//                }
//
//            }else if(d == Direction.DOWN){
//
//                if(currentFloor <= minFloor){
//                    d = Direction.UP;
//                    changeToUpDirection();
//                }else{
//                    currentFloor --;
//                }
//
//
//
//            }
//
//        }
//    }

    public static void changeToUpDirection(PriorityQueue<ExternalRequest> externalRequests, Direction currentDirection,
                                           PriorityQueue<Integer> internalRequest){
        List<ExternalRequest> temp = new ArrayList<>();
        while(!externalRequests.isEmpty()){
            temp.add(externalRequests.remove());
        }

        List<Integer> arryList = new ArrayList<>();
        while(!internalRequest.isEmpty()){
            arryList.add(internalRequest.remove());
        }
        externalRequests = new PriorityQueue<>(Comparator.comparing(ExternalRequest::getFloorNumber));
        internalRequest = new PriorityQueue<>(Comparator.comparing(Integer::intValue));

        for(ExternalRequest e: temp){
            externalRequests.add(e);
        }
        for(Integer i: arryList){
            internalRequest.add(i);
        }
    }

    public static void changeToDownDirection(PriorityQueue<ExternalRequest> externalRequests, Direction currentDirection,
                                             PriorityQueue<Integer> internalRequest){
        List<ExternalRequest> temp = new ArrayList<>();
        while(!externalRequests.isEmpty()){
            temp.add(externalRequests.remove());
        }

        List<Integer> arryList = new ArrayList<>();
        while(!internalRequest.isEmpty()){
            arryList.add(internalRequest.remove());
        }

        externalRequests = new PriorityQueue<>(Comparator.comparing(ExternalRequest::getFloorNumber).reversed());
        internalRequest = new PriorityQueue<>(Comparator.comparing(Integer::intValue).reversed());

        for(ExternalRequest e: temp){
            externalRequests.add(e);
        }
        for(Integer i: arryList){
            internalRequest.add(i);
        }
    }







    private static void onShutDown(Scanner sc) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shutting down ...");
                    sc.close();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
    }


    public static boolean InternalRequestIsNotEmpty(PriorityBlockingQueue<Integer> internalRequest){
        return !internalRequest.isEmpty();
    }

    public static boolean ExternalRequestIsNotEmpty(PriorityBlockingQueue<ExternalRequest> externalRequests){
        return !externalRequests.isEmpty();
    }



//        Scanner sc = new Scanner(System.in);
//        Queue<Integer> ls = new LinkedList<>();
//
//
//        onShutDown(sc);
//
//        while(true){
//            if(!ls.isEmpty()){
//                System.out.println(ls.poll());
//            }
//            ls.add(sc.nextInt());
//        }
}
