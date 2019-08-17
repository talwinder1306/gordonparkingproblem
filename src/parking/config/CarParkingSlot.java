package parking.config;

public class CarParkingSlot extends ParkingSlot {

    @Override
    public boolean isSlotEmpty() {
        if(ParkingLot.vacantParkingSlotsQueueByNearExit.contains(this)){
            return true;
        }
        return false;
    }
}
