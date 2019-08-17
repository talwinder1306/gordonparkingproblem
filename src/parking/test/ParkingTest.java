package parking.test;

import parking.request.ParkingRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ParkingTest {

    public static void main(String args[]){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int numberOfFloors;
            System.out.print("Enter number of floors: ");
            numberOfFloors = Integer.parseInt(br.readLine());
            System.out.println();
            ParkingRequest parkingRequest = new ParkingRequest();
            parkingRequest.setNumberOfFloors(numberOfFloors);
            /*Add code to take input from command line*/
            ParkingRequest pr1 = new ParkingRequest("123", "car", "elder", "in");
            ParkingRequest pr2 = new ParkingRequest("453", "car", "royal", "in");
            ParkingRequest pr3 = new ParkingRequest("1623", "car", "common", "in");
            ParkingRequest pr4 = new ParkingRequest("1253", "car", "common", "in");
            ParkingRequest pr5 = new ParkingRequest("1223", "car", "elder", "in");
            pr1.sendRequest();
            pr2.sendRequest();
            pr3.sendRequest();
            pr4.sendRequest();
            pr5.sendRequest();

            ParkingRequest pr11 =  new ParkingRequest("123", "car", "elder", "out");
            ParkingRequest pr21 =  new ParkingRequest("453", "car", "royal", "out");
            ParkingRequest pr31 =  new ParkingRequest("1623", "car", "common", "out");
            ParkingRequest pr41 =  new ParkingRequest("1253", "car", "common", "out");
            ParkingRequest pr51 =  new ParkingRequest("1223", "car", "elder", "out");
            pr41.sendRequest();
            pr51.sendRequest();
            pr21.sendRequest();
            pr11.sendRequest();
            pr31.sendRequest();


        } catch (IOException io){
            io.printStackTrace();
        }
    }
}
