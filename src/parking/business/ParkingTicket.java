package parking.business;

import parking.admin.Vehicle;
import parking.admin.VehicleType;
import parking.config.BikeParkingSlot;
import parking.config.Floor;
import parking.config.ParkingLot;
import parking.config.ParkingSlot;

import java.math.BigDecimal;
import java.util.Map;

public class ParkingTicket {

    protected Vehicle vehicle;
    protected static Fare fare;
    protected boolean isOfferApplied;
    protected BigDecimal totalFare;
    private static int stdDiscountPct = 10;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Fare getFare() {
        if(vehicle.getType() == VehicleType.BIKE){
            return Fare.BIKE;
        }
        return Fare.COMMON;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public boolean isOfferApplied() {
        return isOfferApplied;
    }

    public void setOfferApplied(boolean offerApplied) {
        isOfferApplied = offerApplied;
    }

    public BigDecimal getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
    }

    public ParkingSlot findVacantSlot(){
        if(vehicle.getType() == VehicleType.BIKE){
            for(Map.Entry parkingSpotEntry: ParkingLot.occupiedBikeParkingSlotsMap.entrySet()){
                ParkingSlot parkingSlot = (ParkingSlot) parkingSpotEntry.getKey();
                ParkingSlot bikeParkingSlot = Floor.getParkingSlotAtLevelAndPositionXY(parkingSlot.getPositionX(), parkingSlot.getPositionY(),
                                                    parkingSlot.getLevel().getLevelNumber(), parkingSlot.getLevel().getFloor().getNumber(),
                                                    new BikeParkingSlot());
                if(bikeParkingSlot.isSlotFull() == false){
                    return parkingSlot;
                }
            }

            return BikeParkingSlot.getSlotAvailableForBikeParking();
        }

        return ParkingLot.vacantParkingSlotsQueueByNearExit.remove();
    }

    public void setOfferApplied(){
        if(vehicle.isCarpooled()){
            this.setOfferApplied(true);
        } else if(vehicle.isFrequentCustomer() && isVisitApplicableForDiscount(vehicle.getVisits())){
            this.setOfferApplied(true);
        } else {
            this.setOfferApplied(false);
        }
    }

    public void calculateTotalFare(){
        Fare f = this.getFare();
        setOfferApplied();
        BigDecimal amount = new BigDecimal(f.getAmount());
        BigDecimal offerAmount = new BigDecimal(((stdDiscountPct/100.0) * f.getAmount()));
        if(isOfferApplied){
            this.totalFare = amount.subtract(offerAmount);
        } else {
            this.totalFare = amount;
        }
    }

    private boolean isVisitApplicableForDiscount(int visits) {
        if((visits%5) == 0){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Fare: " + this.totalFare;
    }
}
