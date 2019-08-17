package parking.config;

import java.util.Comparator;

public class ParkingSlotSortByLevel implements Comparator<ParkingSlot> {
    @Override
    public int compare(ParkingSlot t1, ParkingSlot parkingSlot) {
        if (t1.getLevel().getLevelNumber() == parkingSlot.getLevel().getLevelNumber()) {
            if(t1.getNearExit() == parkingSlot.getNearExit()) {
                return t1.getLevel().getFloor().getNumber() - parkingSlot.getLevel().getFloor().getNumber();
            }
            return t1.getNearExit() - parkingSlot.getNearExit();
        }
        return t1.getLevel().getLevelNumber() - parkingSlot.getLevel().getLevelNumber();
    }
}
