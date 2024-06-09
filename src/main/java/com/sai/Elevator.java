package com.sai;

import com.sai.model.Direction;

import java.util.PriorityQueue;

public class Elevator {
    // initial parameters.
    private int currentFloor;
    private Direction direction;
    private int lowestFloorNumber;
    private int highestFloorNumber;
    PriorityQueue<Integer> InternalRequestList = new PriorityQueue<>((a, b) ->a - b);


    public Elevator(int currentFloor,int lowestFloorNumber,int highestFloorNumber) {
        this.currentFloor = currentFloor;
        this.lowestFloorNumber = lowestFloorNumber;
        this.highestFloorNumber = highestFloorNumber;
    }




    public void startElevator() {

    }


    private void start (){

        while(true){


        }
    }


    public String addInternalRequest(int floorNumber){
        InternalRequestList.add(floorNumber);
        return "Pressed " + floorNumber + " inside the elevator";
    }
}
