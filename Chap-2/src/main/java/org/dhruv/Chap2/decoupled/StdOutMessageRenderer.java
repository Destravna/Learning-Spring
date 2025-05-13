package org.dhruv.Chap2.decoupled;

public class StdOutMessageRenderer implements MessageRenderer{

    private MessageProvider messageProvider;

    public StdOutMessageRenderer(){
        System.out.println("stdoutmessage renderer constructor called ");
    }

    @Override
    public void render() {
        System.out.println(messageProvider.getMessage());
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        System.out.println("-->StandardOutMessageRenderer : setting the provider");
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
       return this.messageProvider;
    }

}
