package kodlama.io.E_Trade2.core.utilities.exceptions;

public class OrderCancellationException extends RuntimeException{
    public OrderCancellationException(String message){
        super(message);
    }
}
