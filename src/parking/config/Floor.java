package parking.config;

import parking.admin.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Floor {
    private int number;
    private List<Level> levelList;
    public static final int maxLevelNumber = 2;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Level> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Level> levelList) {
        this.levelList = levelList;
    }

    public List<ParkingSlot> initializeFloor(int number){
        this.setNumber(number);
        List<Level> levelList = new ArrayList<>();
        List<ParkingSlot> parkingSlotList = new ArrayList<>();
        for(int i = 0; i < maxLevelNumber; i++){
            Level level = new Level();
            level.setFloor(this);
            level.initializeLevel(i);
            parkingSlotList.addAll(level.getParkingSlotList());
            levelList.add(level);
        }
        this.setLevelList(levelList);
        return parkingSlotList;
    }

    @Override
    public String toString() {
        return "F - " + this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return number == floor.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public static boolean isSlotEmptyForAllLevels(int currentFloor, int x, int y, VehicleType vehicleType) {
        ParkingSlot parkingSlot;
        if(vehicleType == VehicleType.BIKE){
            parkingSlot = new BikeParkingSlot();
        } else {
            parkingSlot = new CarParkingSlot();
        }
        for (int i = 0; i < Floor.maxLevelNumber; i++) {
            parkingSlot = getParkingSlotAtLevelAndPositionXY(x, y, i, currentFloor, parkingSlot);
            if (parkingSlot.isSlotEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    public static ParkingSlot getParkingSlotAtLevelAndPositionXY(int x, int y, int levelNumber, int floorNo, ParkingSlot parkingSlot) {
        parkingSlot.setPositionY(y);
        parkingSlot.setPositionX(x);

        Level level = new Level();
        level.setLevelNumber(levelNumber);

        Floor floor = new Floor();
        floor.setNumber(floorNo);

        level.setFloor(floor);
        parkingSlot.setLevel(level);

        return parkingSlot;
    }

}
