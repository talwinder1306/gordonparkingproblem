package parking.config;

import java.util.Comparator;

public class ParkingSlotSortByNearExit implements Comparator<ParkingSlot> {
    @Override
    public int compare(ParkingSlot t1, ParkingSlot parkingSlot) {
        if (t1.getNearExit() == parkingSlot.getNearExit()) {
            if (parkingSlot.getLevel().getLevelNumber() == t1.getLevel().getLevelNumber()) {
                return t1.getPositionX() - parkingSlot.getPositionX();
            }
            return parkingSlot.getLevel().getLevelNumber() - t1.getLevel().getLevelNumber();
        }
        return t1.getNearExit() - parkingSlot.getNearExit();
    }
}
