package parking.config;

import java.util.Comparator;
import java.util.Objects;

public class ParkingSlot {
    private int positionX;
    private int positionY;
    private int nearExit;
    private ParkingSlotState state = ParkingSlotState.VACANT;
    private Level level;

    public int getNearExit() {
        return nearExit;
    }

    public void setNearExit(int nearExit) {
        this.nearExit = nearExit;
    }

    public ParkingSlotState getState() {
        return state;
    }

    public void setState(ParkingSlotState state) {
        this.state = state;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int computeNearExit(int maxX){
        int nearExit = 0;
        int minX = 0;

        int distanceOfXFromMaxX = Math.abs(maxX - 1 - this.positionX);
        int distanceOfXFromMinX = Math.abs(minX - this.positionX);

        if(distanceOfXFromMaxX < distanceOfXFromMinX){
            nearExit = distanceOfXFromMaxX;
        } else {
            nearExit = distanceOfXFromMinX;
        }

        return nearExit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ParkingSlot)) return false;
        ParkingSlot that = (ParkingSlot) o;
        return  positionX == that.positionX &&
                positionY == that.positionY &&
                level.equals(that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY, level);
    }

    @Override
    public String toString() {
        return  this.level.toString() +
                " P-" + this.positionX  +
                " " + this.positionY;
    }

    public boolean isSlotFull() {
        return this.getState() == ParkingSlotState.OCCUPIED;
    }


}
