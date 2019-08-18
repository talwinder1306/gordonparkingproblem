package parking.admin;

import parking.config.BikeParkingSlot;
import parking.config.CarParkingSlot;
import parking.config.ParkingSlot;

import java.util.Objects;

public class Vehicle {
    private String licenseNumber;
    private VehicleType type;
    private boolean isCarpooled;
    private CustomerType customerType;
    private int visits;


    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public boolean isCarpooled() {
        return isCarpooled;
    }

    public void setCarpooled(boolean carpooled) {
        isCarpooled = carpooled;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return licenseNumber.equals(vehicle.licenseNumber) &&
                type == vehicle.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseNumber, type);
    }

    @Override
    public String toString() {
        return "Vehicle: "+ type + " License: " + licenseNumber;
    }

    public ParkingSlot getNewParkingSlotForVehicle(){
        if(this.type == VehicleType.BIKE){
            return new BikeParkingSlot();
        }
        return new CarParkingSlot();
    }

    public void setCustomerType(String customerType){
        customerType = customerType.toUpperCase();
        if(this.getType() == VehicleType.CAR) {
            switch (customerType) {
                case "ELDER":
                    this.setCustomerType(CustomerType.ELDER);
                    break;
                case "ROYAL":
                    this.setCustomerType(CustomerType.ROYAL);
                    break;
                default:
                    this.setCustomerType(CustomerType.COMMON);
            }
        } else {
            this.setCustomerType(CustomerType.COMMON);
        }
    }

    public boolean isFrequentCustomer(){
        if(this.visits >= 5){
            return true;
        }
        return false;
    }
}
