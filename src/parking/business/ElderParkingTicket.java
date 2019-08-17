package parking.business;

import parking.config.ParkingLot;
import parking.config.ParkingSlot;
import parking.validator.*;

public class ElderParkingTicket extends ParkingTicket {

    @Override
    public ParkingSlot findVacantSlot() {
        ParkingSlot parkingSlot = null;
        try {
            parkingSlot = ParkingLot.vacantParkingSlotsQueueByLevel.peek();
            if (parkingSlot.getLevel().getLevelNumber() != 0) {
                ParkingLot.vacantParkingSlotsQueueByLevel.add(parkingSlot);
                parkingSlot = null;
                throw new NoSlotsAvailableException("No slots available on lower level");
            }


        } catch(NoSlotsAvailableException e) {
            System.out.println(e.getMessage());
        }
        return parkingSlot;
    }
}
