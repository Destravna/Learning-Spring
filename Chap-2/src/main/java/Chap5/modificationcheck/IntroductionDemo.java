package Chap5.modificationcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;

public class IntroductionDemo {
    private static final Logger logger = LoggerFactory.getLogger(IntroductionDemo.class);
    public static void main(String[] args) {
        Contact target = new Contact();
        target.setName("Dhruv");
        target.setEmail("dhruv@dmail.com");
        target.setPhoneNo(456127840L);

        IntroductionAdvisor advisor = new IsModifiedAdvisor();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setOptimize(true); //to use CGLIB. 

        Contact proxy = (Contact) pf.getProxy();
        IsModified proxyIneterface = (IsModified) proxy; //The advisor adds new behavior (and interface) to the Contact object, making it behave like it implements IsModified.
        logger.info("is contact -> {}", proxy instanceof Contact);
        logger.info("is isModified -> {}" , proxy instanceof IsModified);
        logger.info("has been modified -> {}", proxyIneterface.isModified());

        proxy.setName("dhruv sing");
        logger.info("has been modified -> {}", proxyIneterface.isModified());



    }
}



class Contact {
    private String name;
    private String email;
    private long phoneNo;

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public long getPhoneNo() {
        return phoneNo;
    }
}