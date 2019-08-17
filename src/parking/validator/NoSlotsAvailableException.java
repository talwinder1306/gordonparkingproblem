package parking.validator;

public class NoSlotsAvailableException extends Exception{
    public NoSlotsAvailableException(String errorMessage){
        super(errorMessage);
    }
}
