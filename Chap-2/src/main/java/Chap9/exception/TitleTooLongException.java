package Chap9.exception;

public class TitleTooLongException extends Exception {
    public TitleTooLongException(String message){
        super(message);
    }

    public TitleTooLongException(String message, Throwable t){
        super(message, t);
    }
}
