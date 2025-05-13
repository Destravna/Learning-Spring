package org.dhruv.Chap2.decoupled;

public class HelloWorldDecopuledWithFactory {
    public static void main(String[] args) {
        // MessageSupportFactory.getInstance();
        MessageRenderer mr = MessageSupportFactory.getMessageRenderer() .orElseThrow(() -> new IllegalArgumentException("service of type 'MessageRenderer' was not found"));
        MessageProvider mp = MessageSupportFactory.getMessageProvider() .orElseThrow(()  -> new IllegalArgumentException("service of type 'MessageProvider' was not found"));

        mr.setMessageProvider(mp);
        mr.render();
    }

}
