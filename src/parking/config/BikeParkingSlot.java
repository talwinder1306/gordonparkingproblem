package parking.config;

import parking.admin.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class BikeParkingSlot extends ParkingSlot {

    @Override
    public boolean isSlotFull() {
        int noOfBikesParked = ParkingLot.occupiedBikeParkingSlotsMap.get(this);
        if(this.getLevel().getLevelNumber() == 1){
            if(noOfBikesParked == 3){
                return true;
            }
        } else {
            if(noOfBikesParked == 2){
                return true;
            }
        }
        return false;
    }

    public static ParkingSlot getSlotAvailableForBikeParking(){
        List<ParkingSlot> notAvailableForBike = new ArrayList<>();
        ParkingSlot parkingSlot = ParkingLot.vacantParkingSlotsQueueByLevel.remove();
        while(ParkingLot.vacantParkingSlotsQueueByLevel.size() != 0) {
            ParkingSlot parkingSlotOtherLevel= Floor.getParkingSlotAtOtherLevelAndPositionXY(parkingSlot, VehicleType.BIKE);
            if(ParkingLot.vacantParkingSlotsQueueByLevel.contains(parkingSlotOtherLevel)){
                ParkingLot.vacantParkingSlotsQueueByLevel.addAll(notAvailableForBike);
                ParkingLot.vacantParkingSlotsQueueByLevel.add(parkingSlot);
                return parkingSlot;
            }
            notAvailableForBike.add(parkingSlot);
            parkingSlot = ParkingLot.vacantParkingSlotsQueueByLevel.remove();
        }
        return null;
    }
}
