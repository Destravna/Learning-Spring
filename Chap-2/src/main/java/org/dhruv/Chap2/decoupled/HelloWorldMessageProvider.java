package org.dhruv.Chap2.decoupled;

public class HelloWorldMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Hello World from provider";
    }
}
