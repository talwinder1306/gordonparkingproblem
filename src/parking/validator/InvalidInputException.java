package parking.validator;

public class InvalidInputException extends Exception {
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
