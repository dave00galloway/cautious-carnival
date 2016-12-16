package iloveyouboss;


public class InsufficientFundsException extends Throwable {
    private final String message;

    InsufficientFundsException(String message) {

        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
