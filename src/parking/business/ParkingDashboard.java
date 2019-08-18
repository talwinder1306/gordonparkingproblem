package parking.business;

import parking.admin.Vehicle;
import parking.config.Floor;
import parking.config.Level;
import parking.config.ParkingLot;
import parking.config.ParkingSlot;

public class ParkingDashboard {

    public static void printAllSlots(int numberOfFloors){
        for(int i = 0; i < numberOfFloors; i++){
            System.out.println("Floor " + i);
            for(int j = 0; j < Floor.maxLevelNumber; j++){
                System.out.println("Level " + j);
                for(int y = 0; y < Level.parkingSlotYCount; y++){
                    for(int x = 0; x < Level.parkingSlotXCount; x++){
                        if(y == 1 || y == 4) {
                            System.out.print("- - - - - - - - - \t\t");
                            continue;
                        }
                        ParkingSlot parkingSlot = Floor.getParkingSlotAtLevelAndPositionXY(x, y, j, i, new ParkingSlot());
                        if(ParkingLot.occupiedBikeParkingSlotsMap.containsKey(parkingSlot) && ParkingLot.occupiedBikeParkingSlotsMap.get(parkingSlot) > 0){
                            System.out.print(parkingSlot.toString() + ": BIKE");
                        } else if(ParkingLot.vacantParkingSlotsQueueByNearExit.contains(parkingSlot) == false){
                            System.out.print(parkingSlot.toString() + ": CAR");
                        } else {
                            System.out.print(parkingSlot.toString() + ": FREE");
                        }
                        System.out.print("\t\t");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }

    public static void printAssignedParkingSlot(ParkingSlot parkingSlot, Vehicle vehicle){
        ParkingTicket parkingTicket = ParkingLot.parkingSlotToTicketMap.get(parkingSlot);
        System.out.println(vehicle.toString() + " assigned to " + parkingSlot.toString() + " " + parkingTicket.toString());
    }
}
