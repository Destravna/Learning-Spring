package org.dhruv.Chap2.decoupled;

import java.util.ServiceLoader;


//here only one problem remains, we still need to provide MessageRenderer with an instance of Message Provider(line 16)
public class HelloWorldWithServiceLoader {
    public static void main(String[] args) {
        ServiceLoader<MessageRenderer> slr = ServiceLoader.load(MessageRenderer.class);
        ServiceLoader<MessageProvider> slp = ServiceLoader.load(MessageProvider.class);

        MessageRenderer mr = slr.findFirst().orElseThrow(() -> new IllegalArgumentException("Service of type 'MessageRenderer' was not found!"));
        MessageProvider mp = slp.findFirst().orElseThrow(() -> new IllegalArgumentException("Service of type 'MessageProvider' was not found!"));

        mr.setMessageProvider(mp);
        mr.render();;

    }

}
