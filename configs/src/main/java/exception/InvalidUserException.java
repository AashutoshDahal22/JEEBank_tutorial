package exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) { //when the object of this constructor is made in the service, the super will call the constrcutor of the parent class i.e. RuntimeException class which will store the message
        super(message);
    }
}
