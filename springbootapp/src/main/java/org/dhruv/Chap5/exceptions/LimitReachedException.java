package org.dhruv.Chap5.exceptions;

public class LimitReachedException extends RuntimeException{
    public LimitReachedException(String msg){
        super(msg);
    }
}
