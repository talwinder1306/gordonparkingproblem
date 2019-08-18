package parking.business;

import parking.validator.*;
import parking.admin.Vehicle;
import parking.config.Floor;
import parking.config.Level;
import parking.config.ParkingLot;
import parking.config.ParkingSlot;

import java.util.ArrayList;
import java.util.List;

public class RoyalParkingTicket extends ParkingTicket {

    @Override
    public Fare getFare() {
        return Fare.ROYAL;
    }

    @Override
    public ParkingSlot findVacantSlot() {
        ParkingSlot parkingSlot = null;
        try {
            List<ParkingSlot> notSuitableParkingSlotsList = new ArrayList<>();
            parkingSlot = ParkingLot.vacantParkingSlotsQueueByNearExit.remove();
            while (isSuitableParkingSlotForRoyalty(parkingSlot, notSuitableParkingSlotsList) == false && ParkingLot.vacantParkingSlotsQueueByNearExit.size() != 0) {
                parkingSlot = ParkingLot.vacantParkingSlotsQueueByNearExit.remove();
            }
            /*If all slots are checked this Queue will be empty so we need to set parkingSlot as null and display an error*/
            if (ParkingLot.vacantParkingSlotsQueueByNearExit.size() == 0) {
                parkingSlot = null;
                throw new NoSlotsAvailableException("No slots available with all vacant slots around it");
            }
            /*Add all unsuitable Parking Slots back to vacantParkingSlotsQueueByNearExit*/
            ParkingLot.vacantParkingSlotsQueueByNearExit.addAll(notSuitableParkingSlotsList);
            if (parkingSlot != null) {
                ParkingLot.vacantParkingSlotsQueueByNearExit.add(parkingSlot);
                blockAllSpotsAround(this.vehicle, parkingSlot);
            }
        } catch (NoSlotsAvailableException e){
            System.out.println(e.getMessage());
        }
        return parkingSlot;
    }

    private boolean isSuitableParkingSlotForRoyalty(ParkingSlot parkingSlot, List<ParkingSlot> notSuitableParkingSlotsList) {


        /*If parking slot above/below and to the right and left both above and below are empty it is suitable*/
        int currentFloor = parkingSlot.getLevel().getFloor().getNumber();
        int currentPositionX = parkingSlot.getPositionX();
        int currentPositionY = parkingSlot.getPositionY();

        /*If notSuitableParkingSlotsList already contains the level below/above the parking slot
        that means we do not need to check as this slot will also be unsuitable*/
        ParkingSlot parkingSlotOtherLevel = Floor.getParkingSlotAtOtherLevelAndPositionXY(parkingSlot, vehicle.getType());

        if(notSuitableParkingSlotsList.contains(parkingSlotOtherLevel)){
            notSuitableParkingSlotsList.add(parkingSlot);
            return false;
        }

        int leftPosition = currentPositionX - 1;
        int rightPosition = currentPositionX + 1;

        if(parkingSlotOtherLevel.isSlotFull()){
            notSuitableParkingSlotsList.add(parkingSlot);
            return false;
        }

        if (leftPosition >= 0) {
            if (Floor.isSlotEmptyForAllLevels(currentFloor, leftPosition, currentPositionY, vehicle.getType()) == false){
                notSuitableParkingSlotsList.add(parkingSlot);
                return false;
            }
        }

        if (rightPosition < Level.parkingSlotXCount) {
            if (Floor.isSlotEmptyForAllLevels(currentFloor, rightPosition, currentPositionY, vehicle.getType()) == false){
                notSuitableParkingSlotsList.add(parkingSlot);
                return false;
            }
        }

        return true;
    }

    private void blockAllSpotsAround(Vehicle vehicle, ParkingSlot parkingSlot){
        int currentFloor = parkingSlot.getLevel().getFloor().getNumber();
        int currentLevel = parkingSlot.getLevel().getLevelNumber();
        int currentPositionX = parkingSlot.getPositionX();
        int currentPositionY = parkingSlot.getPositionY();

        int leftPosition = currentPositionX - 1;
        int rightPosition = currentPositionX + 1;
        List<ParkingSlot> parkingSlotList = new ArrayList<>();

        ParkingSlot parkingSlotOtherLevel = Floor.getParkingSlotAtOtherLevelAndPositionXY(parkingSlot, vehicle.getType());
        ParkingLot.removeFromVacantParkingSlotQueues(parkingSlotOtherLevel);
        parkingSlotList.add(parkingSlotOtherLevel);

        if(leftPosition >= 0) {
            for (int levelNumber = 0; levelNumber < Floor.maxLevelNumber; levelNumber++) {
                ParkingSlot parkingSlotLeft = Floor.getParkingSlotAtLevelAndPositionXY(leftPosition, currentPositionY, levelNumber,
                                                currentFloor, vehicle.getNewParkingSlotForVehicle());
                ParkingLot.removeFromVacantParkingSlotQueues(parkingSlotLeft);
                parkingSlotList.add(parkingSlotLeft);
            }
        }

        if(rightPosition < Level.parkingSlotXCount) {
            for (int levelNumber = 0; levelNumber < Floor.maxLevelNumber; levelNumber++) {
                ParkingSlot parkingSlotRight = Floor.getParkingSlotAtLevelAndPositionXY(rightPosition, currentPositionY, levelNumber,
                                            currentFloor, vehicle.getNewParkingSlotForVehicle());
                ParkingLot.removeFromVacantParkingSlotQueues(parkingSlotRight);
                parkingSlotList.add(parkingSlotRight);
            }
        }

        ParkingLot.vehicleParkingSlotBlockedForRoyaltyMap.put(vehicle, parkingSlotList);
    }


}
