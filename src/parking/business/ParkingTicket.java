package parking.business;

import parking.admin.Vehicle;
import parking.config.ParkingLot;
import parking.config.ParkingSlot;

public class ParkingTicket {

    protected Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSlot findVacantSlot(){
        return ParkingLot.vacantParkingSlotsQueueByNearExit.remove();
    }
}
