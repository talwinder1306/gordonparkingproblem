package parking.request;

import parking.admin.CustomerType;
import parking.admin.Vehicle;
import parking.admin.VehicleType;
import parking.config.ParkingLot;
import parking.validator.*;

public class ParkingRequest {

    private static final String INCOMING = "IN";
    private static final String OUTGOING = "OUT";
    private static final String YES = "Y";
    private static final String NO = "N";

    private String vehicleNo;
    private String vehicleType;
    private String customerType;
    private String action;
    private String isCarpooled;
    private static ParkingLot parkingLot = new ParkingLot();

    public ParkingRequest(){}

    public ParkingRequest(String vehicleNo, String vehicleType, String customerType, String action, String isCarpooled) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.customerType = customerType;
        this.action = action;
        this.isCarpooled = isCarpooled;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public VehicleType getVehicleType() {
        if("BIKE".equalsIgnoreCase(vehicleType)){
            return VehicleType.BIKE;
        }
        return VehicleType.CAR;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getAction() {
        return action;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        parkingLot.setNumberOfFloors(numberOfFloors);
    }

    public void sendRequest(){
        if(validInput() == false){
            return;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setLicenseNumber(vehicleNo);
        vehicle.setType(getVehicleType());
        vehicle.setCustomerType(customerType);

        if(YES.equalsIgnoreCase(isCarpooled)){
            vehicle.setCarpooled(true);
        } else {
            vehicle.setCarpooled(false);
        }

        if(INCOMING.equalsIgnoreCase(action)){
            parkingLot.assignSpot(vehicle);
        } else if(OUTGOING.equalsIgnoreCase(action)){
            parkingLot.freeSpot(vehicle);
        }

    }

    public boolean validInput(){
        try {
            if (INCOMING.equalsIgnoreCase(action) == false && OUTGOING.equalsIgnoreCase(action) == false) {
               throw new InvalidInputException("Action can be either in or out");
            }
            if(customerType != null && CustomerType.ELDER.toString().equalsIgnoreCase(customerType) == false
                    && CustomerType.ROYAL.toString().equalsIgnoreCase(customerType) == false
                    && CustomerType.COMMON.toString().equalsIgnoreCase(customerType) == false){
                throw new InvalidInputException("Customer type can be either elder, royal or common");
            }
        } catch(InvalidInputException e){
            System.out.println(e.getMessage());
        }

        return true;
    }

}
