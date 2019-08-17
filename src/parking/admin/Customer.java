package parking.admin;

public class Customer {

    private Vehicle vehicle;
    private CustomerType type;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public void setCustomerType(String customerType){
        customerType = customerType.toUpperCase();
        switch(customerType){
            case "ELDER" : this.setType(CustomerType.ELDER); break;
            case "ROYAL" : this.setType(CustomerType.ROYAL); break;
            default: this.setType(CustomerType.COMMON);
        }
    }
}
