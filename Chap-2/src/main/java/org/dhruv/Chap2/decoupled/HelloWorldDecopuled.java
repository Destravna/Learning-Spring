package org.dhruv.Chap2.decoupled;


// here the problem is if I want to change the message, again I need to change the code. 
// to avoid this we need to delegate the resposnibility of retrieving two implementation types and instantiating them to someone else. 
// . The most manual one is to create a simple factory class that reads the implementation class names from a properties file and instantiates them on behalf of the application 
public class HelloWorldDecopuled {
    public static void main(String[] args) {
        MessageProvider mp = new HelloWorldMessageProvider();
        MessageRenderer mr = new StdOutMessageRenderer();
        mr.setMessageProvider(mp);
        mr.render();
        
    }

}
