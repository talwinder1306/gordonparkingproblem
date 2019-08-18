package parking.business;

public enum Fare {

    COMMON(100),
    ELDER(50),
    BIKE(50),
    ROYAL(0);

    private int amount;

    private Fare(int amount){
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
