package exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true) //when exception is thrown transaction should be rolled back automatically
public class InSufficientBalanceException extends RuntimeException {
    public InSufficientBalanceException(String message) {
        super(message);
    }
}
