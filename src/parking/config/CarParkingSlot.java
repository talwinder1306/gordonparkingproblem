package parking.config;

public class CarParkingSlot extends ParkingSlot {

    @Override
    public boolean isSlotFull() {
        if(ParkingLot.vacantParkingSlotsQueueByNearExit.contains(this)){
            return false;
        }
        return true;
    }
}
