package Chapter4.MessageDigestFactoryBean;

import java.security.MessageDigest;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class MessageDigestFactoryBean implements FactoryBean<MessageDigest>, InitializingBean{

    private String algoName = "MD5";
    private MessageDigest messageDigest;
    
    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    @Override
    public MessageDigest getObject() throws Exception {
        return messageDigest;
    }

    @Override
    public Class<?> getObjectType() {
        return MessageDigest.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       messageDigest = MessageDigest.getInstance(algoName);
    }

    

}
