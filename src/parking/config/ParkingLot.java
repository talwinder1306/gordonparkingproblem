package parking.config;

import parking.admin.Customer;
import parking.admin.CustomerType;
import parking.admin.Vehicle;
import parking.business.*;

import java.util.*;

public class ParkingLot {

    private static int numberOfFloors;
    public static Map<Vehicle, ParkingSlot> vehicleParkingSlotMap = new HashMap<>();
    public static Map<Vehicle, List<ParkingSlot>> vehicleParkingSlotBlockedForRoyaltyMap = new HashMap<>();
    public static PriorityQueue<ParkingSlot> vacantParkingSlotsQueueByNearExit = new PriorityQueue<>(new ParkingSlotSortByNearExit());
    public static PriorityQueue<ParkingSlot> vacantParkingSlotsQueueByLevel = new PriorityQueue<>(new ParkingSlotSortByLevel());
    private static List<Floor> floorList;

    public ParkingLot() {
    }


    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        initializeVacantParkingSlotQueues();
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public static void initializeVacantParkingSlotQueues() {
        /**
         * initialize the vacantParkingSlotQueue
         * Add parkingSlot for each Floor
         * This could be a static method called from ParkingController to initialize
         * the Queue
         */
        floorList = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++) {
            Floor floor = new Floor();
            List<ParkingSlot> parkingSlotList = floor.initializeFloor(i);
            vacantParkingSlotsQueueByNearExit.addAll(parkingSlotList);
            vacantParkingSlotsQueueByLevel.addAll(parkingSlotList);
            floorList.add(floor);
        }

        /*while(vacantParkingSlotsQueueByNearExit.size() != 0){
            ParkingSlot ps = vacantParkingSlotsQueueByNearExit.remove();
            System.out.print(ps.toString());
        }*/

        /*while(vacantParkingSlotsQueueByLevel.size() != 0){
            ParkingSlot ps = vacantParkingSlotsQueueByLevel.remove();
            System.out.print(ps.toString());
        }*/
    }

    public void clearSpot(Vehicle vehicle) {
        /**
         * Input: Vehicle
         * Validate input exists
         * Clear the spot
         * Delete from map
         * add to queue
         */
        if (vehicleParkingSlotMap.containsKey(vehicle) == false) {
            System.out.print("Incorrect Vehicle number");
        } else {
            System.out.println();
            System.out.println(vehicle.toString());
            ParkingSlot unassignedParkingSlot = vehicleParkingSlotMap.remove(vehicle);
            addToVacantParkingSlotQueues(unassignedParkingSlot);
            if(vehicleParkingSlotBlockedForRoyaltyMap.containsKey(vehicle)){
                for(ParkingSlot ps : vehicleParkingSlotBlockedForRoyaltyMap.get(vehicle)) {
                    addToVacantParkingSlotQueues(ps);
                }
                vehicleParkingSlotBlockedForRoyaltyMap.remove(vehicle);
            }
        }
    }

    private void addToVacantParkingSlotQueues(ParkingSlot unassignedParkingSlot) {
        unassignedParkingSlot.setState(ParkingSlotState.VACANT);
        System.out.print(unassignedParkingSlot.toString());
        vacantParkingSlotsQueueByNearExit.add(unassignedParkingSlot);
        vacantParkingSlotsQueueByLevel.add(unassignedParkingSlot);
    }

    public static void removeFromVacantParkingSlotQueues(ParkingSlot parkingSlot){
        parkingSlot.setState(ParkingSlotState.OCCUPIED);
        System.out.print(parkingSlot.toString());
        vacantParkingSlotsQueueByNearExit.remove(parkingSlot);
        vacantParkingSlotsQueueByLevel.remove(parkingSlot);
    }

    public void fillSpot(Vehicle vehicle, Customer customer) {
        /**
         * Input: Vehicle, Customer
         * Check if already added
         * Find the spot based on Customer (elderly or Royal or both)
         * if(elderly) fetch slot only where level is 0
         *
         * if(royal) then fetch slot where adjacents are empty too
         * So add isEmpty for ParkingSlot i.e if it is in vacantParkingSlotQueue or not in map
         *
         * add vehicle and parkingslot to map
         * delete from queue
         */
        if (vehicleParkingSlotMap.containsKey(vehicle)) {
            System.out.print("Vehicle already parked!");
        } else {
            System.out.println();
            System.out.println(vehicle.toString());
            ParkingSlot assignedParkingSlot = findVacantSpotByCustomerType(vehicle, customer);
            if(assignedParkingSlot != null) {
                removeFromVacantParkingSlotQueues(assignedParkingSlot);
                vehicleParkingSlotMap.put(vehicle, assignedParkingSlot);
            }
        }

    }

    private static ParkingSlot findVacantSpotByCustomerType(Vehicle vehicle, Customer customer) {
        ParkingSlot parkingSlot;
        ParkingTicket parkingTicket;
        if (customer.getType() == CustomerType.ELDER) {
            parkingTicket = new ElderParkingTicket();
            parkingTicket.setVehicle(vehicle);
            parkingSlot = parkingTicket.findVacantSlot();
        } else if (customer.getType() == CustomerType.ROYAL) {
            parkingTicket = new RoyalParkingTicket();
            parkingTicket.setVehicle(vehicle);
            parkingSlot = parkingTicket.findVacantSlot();
        } else {
            parkingTicket = new ParkingTicket();
            parkingTicket.setVehicle(vehicle);
            parkingSlot = parkingTicket.findVacantSlot();
        }
        return parkingSlot;
    }
}
