package parking.config;

import parking.admin.CustomerType;
import parking.admin.Vehicle;
import parking.admin.VehicleType;
import parking.business.*;
import parking.validator.*;

import java.util.*;

public class ParkingLot {

    private static int numberOfFloors;
    public static Map<Vehicle, ParkingSlot> vehicleParkingSlotMap = new HashMap<>();
    public static Map<Vehicle, List<ParkingSlot>> vehicleParkingSlotBlockedForRoyaltyMap = new HashMap<>();
    public static Map<ParkingSlot, Integer> occupiedBikeParkingSlotsMap = new HashMap<>();
    public static Map<Vehicle, Integer> logCustomerVisits = new HashMap<>();
    public static Map<ParkingSlot, ParkingTicket> parkingSlotToTicketMap = new HashMap<>();

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
    /**
     * Input: Vehicle
     * Validate input exists
     * Clear the spot
     * Delete from map
     * add to queue
     */
    public void freeSpot(Vehicle vehicle) {
        try {
            if (vehicleParkingSlotMap.containsKey(vehicle) == false) {
               throw new InvalidRequestException("Incorrect Vehicle number");
            } else {
                //System.out.println();
                //System.out.println(vehicle.toString());
                ParkingSlot unassignedParkingSlot = vehicleParkingSlotMap.remove(vehicle);
                if (vehicle.getType() == VehicleType.BIKE) {
                    addToVacantBikeSlot(unassignedParkingSlot);
                } else {
                    addToVacantParkingSlotQueues(unassignedParkingSlot);
                    addBlockedDueRoyaltyToVacantSlots(vehicle);
                }
            }
        } catch (InvalidRequestException e){
            System.out.println(e.getMessage());
        }
        ParkingDashboard.printAllSlots(numberOfFloors);
    }

    private void addToVacantBikeSlot(ParkingSlot unassignedParkingSlot) {
        int numOfBikes = occupiedBikeParkingSlotsMap.get(unassignedParkingSlot);
        numOfBikes--;
        ParkingSlot unassignedParkingSlotOtherLevel = Floor.getParkingSlotAtOtherLevelAndPositionXY(unassignedParkingSlot, VehicleType.BIKE);
        int numOfBikesOfOtherLevel = occupiedBikeParkingSlotsMap.get(unassignedParkingSlotOtherLevel);

        if(numOfBikes == 0 && numOfBikesOfOtherLevel == 0){
            addToVacantParkingSlotQueues(unassignedParkingSlot);
            addToVacantParkingSlotQueues(unassignedParkingSlotOtherLevel);
            occupiedBikeParkingSlotsMap.remove(unassignedParkingSlot);
            occupiedBikeParkingSlotsMap.remove(unassignedParkingSlotOtherLevel);
        } else {
            unassignedParkingSlot.setState(ParkingSlotState.VACANT);
            //System.out.print(unassignedParkingSlot.toString());
            occupiedBikeParkingSlotsMap.put(unassignedParkingSlot, numOfBikes);
        }
    }

    private void addBlockedDueRoyaltyToVacantSlots(Vehicle vehicle) {
        if(vehicleParkingSlotBlockedForRoyaltyMap.containsKey(vehicle)){
            for(ParkingSlot ps : vehicleParkingSlotBlockedForRoyaltyMap.get(vehicle)) {
                addToVacantParkingSlotQueues(ps);
            }
            vehicleParkingSlotBlockedForRoyaltyMap.remove(vehicle);
        }
    }

    private void addToVacantParkingSlotQueues(ParkingSlot unassignedParkingSlot) {
        unassignedParkingSlot.setState(ParkingSlotState.VACANT);
        //System.out.print(unassignedParkingSlot.toString());
        vacantParkingSlotsQueueByNearExit.add(unassignedParkingSlot);
        vacantParkingSlotsQueueByLevel.add(unassignedParkingSlot);
    }

    public static void removeFromVacantParkingSlotQueues(ParkingSlot parkingSlot){
        parkingSlot.setState(ParkingSlotState.OCCUPIED);
        //System.out.print(parkingSlot.toString());
        vacantParkingSlotsQueueByNearExit.remove(parkingSlot);
        vacantParkingSlotsQueueByLevel.remove(parkingSlot);
    }

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
    public void assignSpot(Vehicle vehicle) {
        try {
            if (vehicleParkingSlotMap.containsKey(vehicle)) {
                throw new InvalidRequestException("Vehicle already parked!");
            } else {
                //System.out.println();
                //System.out.println(vehicle.toString());
                addToLog(vehicle);
                ParkingSlot assignedParkingSlot = findVacantSpotByCustomerType(vehicle);
                if (assignedParkingSlot != null) {
                    removeFromVacantParkingSlotQueues(assignedParkingSlot);
                    vehicleParkingSlotMap.put(vehicle, assignedParkingSlot);
                    addOrUpdateOccupiedBikeSlots(vehicle, assignedParkingSlot);
                    ParkingDashboard.printAssignedParkingSlot(assignedParkingSlot, vehicle);
                }
            }
        } catch(InvalidRequestException e){
            System.out.println(e.getMessage());
        }
        ParkingDashboard.printAllSlots(numberOfFloors);
    }

    private void addToLog(Vehicle vehicle){
        if(logCustomerVisits.containsKey(vehicle)){
            int visits = logCustomerVisits.get(vehicle);
            visits++;
            vehicle.setVisits(visits);
            logCustomerVisits.put(vehicle, visits);
        } else {
            vehicle.setVisits(1);
            logCustomerVisits.put(vehicle, 1);
        }
    }
    private void addOrUpdateOccupiedBikeSlots(Vehicle vehicle, ParkingSlot assignedParkingSlot) {
        if(vehicle.getType() == VehicleType.BIKE){
            if(occupiedBikeParkingSlotsMap.get(assignedParkingSlot) != null){
                int numberOfBikesOnSpot = occupiedBikeParkingSlotsMap.get(assignedParkingSlot);
                numberOfBikesOnSpot++;
                occupiedBikeParkingSlotsMap.put(assignedParkingSlot, numberOfBikesOnSpot);
            } else {
                occupiedBikeParkingSlotsMap.put(assignedParkingSlot, 1);
                ParkingSlot assignedParkingSlotOtherLevel = Floor.getParkingSlotAtOtherLevelAndPositionXY(assignedParkingSlot, vehicle.getType());
                assignedParkingSlotOtherLevel.setState(ParkingSlotState.OCCUPIED);
                //System.out.print(assignedParkingSlotOtherLevel.toString());
                occupiedBikeParkingSlotsMap.put(assignedParkingSlotOtherLevel, 0);
            }
        }
    }

    private static ParkingSlot findVacantSpotByCustomerType(Vehicle vehicle) {
        ParkingSlot parkingSlot;
        ParkingTicket parkingTicket;
        if (vehicle.getCustomerType() == CustomerType.ELDER) {
            parkingTicket = new ElderParkingTicket();
            parkingTicket.setVehicle(vehicle);
            parkingTicket.calculateTotalFare();
            parkingSlot = parkingTicket.findVacantSlot();
        } else if (vehicle.getCustomerType() == CustomerType.ROYAL) {
            parkingTicket = new RoyalParkingTicket();
            parkingTicket.setVehicle(vehicle);
            parkingTicket.calculateTotalFare();
            parkingSlot = parkingTicket.findVacantSlot();
        } else {
            parkingTicket = new ParkingTicket();
            parkingTicket.setVehicle(vehicle);
            parkingTicket.calculateTotalFare();
            parkingSlot = parkingTicket.findVacantSlot();
        }
        if(parkingSlot != null){
            parkingSlotToTicketMap.put(parkingSlot, parkingTicket);
        }
        return parkingSlot;
    }
}
