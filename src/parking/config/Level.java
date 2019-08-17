package parking.config;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Level {
    private int levelNumber;
    private List<ParkingSlot> parkingSlotList;
    private Floor floor;
    public static final int parkingSlotXCount = 5;
    public static final int parkingSlotYCount = 6;

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public List<ParkingSlot> getParkingSlotList() {
        return parkingSlotList;
    }

    public void setParkingSlotList(List<ParkingSlot> parkingSlotList) {
        this.parkingSlotList = parkingSlotList;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public List<ParkingSlot> initializeLevel(int levelNumber) {
        this.setLevelNumber(levelNumber);
        List<ParkingSlot> parkingSlotList = new ArrayList<>();
        for(int x = 0; x < parkingSlotXCount; x++){
            for(int y = 0; y < parkingSlotYCount; y++) {
                if(y == 1 || y == 4){
                    continue;
                }
                ParkingSlot slot = new ParkingSlot();
                slot.setPositionX(x);
                slot.setPositionY(y);
                slot.setState(ParkingSlotState.VACANT);
                slot.setNearExit(slot.computeNearExit(parkingSlotXCount));
                slot.setLevel(this);
                parkingSlotList.add(slot);
            }
        }
        this.setParkingSlotList(parkingSlotList);
        return parkingSlotList;
    }

    @Override
    public String toString() {
        return this.floor.toString() + " L-" + this.levelNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return levelNumber == level.levelNumber &&
                floor.equals(level.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelNumber, floor);
    }
}
