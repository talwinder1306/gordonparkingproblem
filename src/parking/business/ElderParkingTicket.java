package parking.business;

import parking.config.ParkingLot;
import parking.config.ParkingSlot;

public class ElderParkingTicket extends ParkingTicket {

    @Override
    public ParkingSlot findVacantSlot() {
        ParkingSlot parkingSlot = ParkingLot.vacantParkingSlotsQueueByLevel.peek();
        if (parkingSlot.getLevel().getLevelNumber() != 0) {
            System.out.println("No slots available on lower level");
            ParkingLot.vacantParkingSlotsQueueByLevel.add(parkingSlot);
            parkingSlot = null;
        }

        return parkingSlot;
    }
}
