package parking.test;

import parking.request.ParkingRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            String input;
            while(true){
                String vehicleNo;
                String vehicleType;
                String customerType;
                String action;

                System.out.print("Enter in for incoming Vehicle or out for outgoing Vehicle: ");
                action = br.readLine();
                System.out.println();

                System.out.print("Enter Vehicle number: ");
                vehicleNo = br.readLine();
                System.out.println();

                System.out.print("Enter Vehicle type (bike/car): ");
                vehicleType = br.readLine();
                System.out.println();

                System.out.print("Enter Vehicle owner type (common/elder/royal): ");
                customerType = br.readLine();
                System.out.println();

                ParkingRequest pr = new ParkingRequest(vehicleNo, vehicleType, customerType, action);
                pr.sendRequest();

                System.out.print("Enter end to stop or enter to continue: ");
                input = br.readLine();
                if("END".equalsIgnoreCase(input)){
                    break;
                }
            }


        } catch (IOException io){
            io.printStackTrace();
        }
    }
    /* ParkingRequest pr1 = new ParkingRequest("123", "car", "elder", "in");
            ParkingRequest pr2 = new ParkingRequest("453", "car", "royal", "in");
            ParkingRequest pr3 = new ParkingRequest("1623", "car", "common", "in");
            ParkingRequest pr4 = new ParkingRequest("1253", "car", "common", "in");
            ParkingRequest pr5 = new ParkingRequest("1223", "car", "elder", "in");
            pr1.sendRequest();
            pr3.sendRequest();
            pr4.sendRequest();
            pr5.sendRequest();
            pr2.sendRequest();

            ParkingRequest pr1b = new ParkingRequest("123", "bike", "common", "in");
            ParkingRequest pr2b = new ParkingRequest("453", "bike", "common", "in");
            ParkingRequest pr3b = new ParkingRequest("1623", "bike", "common", "in");
            ParkingRequest pr4b = new ParkingRequest("1253", "bike", "common", "in");
            ParkingRequest pr5b = new ParkingRequest("1223", "bike", "common", "in");
            ParkingRequest pr6b = new ParkingRequest("1263", "bike", "common", "in");
            pr1b.sendRequest();
            pr3b.sendRequest();
            pr4b.sendRequest();
            pr5b.sendRequest();
            pr2b.sendRequest();
            pr6b.sendRequest();

            ParkingRequest pr1bo = new ParkingRequest("123", "bike", "common", "out");
            ParkingRequest pr2bo = new ParkingRequest("453", "bike", "common", "out");
            ParkingRequest pr3bo = new ParkingRequest("1623", "bike", "common", "out");
            ParkingRequest pr4bo = new ParkingRequest("1253", "bike", "common", "out");
            ParkingRequest pr5bo = new ParkingRequest("1223", "bike", "common", "out");
            ParkingRequest pr6bo = new ParkingRequest("1263", "bike", "common", "out");
            pr1bo.sendRequest();
            pr3bo.sendRequest();
            pr4bo.sendRequest();
            pr5bo.sendRequest();
            pr2bo.sendRequest();
            pr6bo.sendRequest();

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
*/
}
