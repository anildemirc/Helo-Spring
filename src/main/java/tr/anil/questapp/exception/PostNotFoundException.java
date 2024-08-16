package tr.anil.questapp.exception;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(String message) {
        super(message);
    }

}
