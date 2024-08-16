package tr.anil.questapp.exception;

public class FollowNotFoundException extends RuntimeException{

    public FollowNotFoundException() {
        super();
    }

    public FollowNotFoundException(String message) {
        super(message);
    }
}
