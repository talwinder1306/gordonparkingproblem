package parking.business;

import parking.admin.Vehicle;
import parking.admin.VehicleType;
import parking.config.BikeParkingSlot;
import parking.config.Floor;
import parking.config.ParkingLot;
import parking.config.ParkingSlot;

import java.util.Map;

public class ParkingTicket {

    protected Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSlot findVacantSlot(){
        if(vehicle.getType() == VehicleType.BIKE){
            for(Map.Entry parkingSpotEntry: ParkingLot.occupiedBikeParkingSlotsMap.entrySet()){
                ParkingSlot parkingSlot = (ParkingSlot) parkingSpotEntry.getKey();
                ParkingSlot bikeParkingSlot = Floor.getParkingSlotAtLevelAndPositionXY(parkingSlot.getPositionX(), parkingSlot.getPositionY(),
                                                    parkingSlot.getLevel().getLevelNumber(), parkingSlot.getLevel().getFloor().getNumber(),
                                                    new BikeParkingSlot());
                if(bikeParkingSlot.isSlotFull() == false){
                    return parkingSlot;
                }
            }

            return BikeParkingSlot.getSlotAvailableForBikeParking();
        }

        return ParkingLot.vacantParkingSlotsQueueByNearExit.remove();
    }
}
