package parking.admin;

import parking.config.BikeParkingSlot;
import parking.config.CarParkingSlot;
import parking.config.ParkingSlot;

import java.util.Objects;

public class Vehicle {
    private String licenseNumber;
    private VehicleType type;

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
}
