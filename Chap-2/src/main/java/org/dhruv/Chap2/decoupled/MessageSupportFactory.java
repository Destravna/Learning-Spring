package org.dhruv.Chap2.decoupled;

import java.util.Optional;
import java.util.Properties;

// basically we want to get the message provider and message renderer from a properties file, so that we can get it without changing the code and adding new properties 
public class MessageSupportFactory {

    private static MessageSupportFactory instance;
    private static Properties props;
    private static MessageProvider mp;
    private static MessageRenderer mr;

    private MessageSupportFactory() {
        props = new Properties();
        try {
            System.out.println("loading resources");
            props.load(this.getClass().getResourceAsStream("/msf.properties"));
            String renderClass = props.getProperty("renderer.class");
            String providerClass = props.getProperty("provider.class"); 

            mr = (MessageRenderer)Class.forName(renderClass).getDeclaredConstructor().newInstance();
            mp = (MessageProvider)Class.forName(providerClass).getDeclaredConstructor().newInstance();
        } 
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

    static {
        instance = new MessageSupportFactory();
    }

    public static MessageSupportFactory getInstance() {
        return instance;
        }

    public static Optional<MessageRenderer> getMessageRenderer(){
        return mr != null ? Optional.of(mr) : Optional.empty();
    }


    public static Optional<MessageProvider> getMessageProvider(){
        return mp != null ? Optional.of(mp) : Optional.empty();
    }
}
