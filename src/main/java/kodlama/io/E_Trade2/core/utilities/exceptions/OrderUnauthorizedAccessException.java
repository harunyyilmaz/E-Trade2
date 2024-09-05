package kodlama.io.E_Trade2.core.utilities.exceptions;

public class OrderUnauthorizedAccessException extends RuntimeException{
    public OrderUnauthorizedAccessException(String message){
        super(message);
    }
}
