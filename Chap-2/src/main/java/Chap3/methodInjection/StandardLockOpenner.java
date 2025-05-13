package Chap3.methodInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class StandardLockOpenner implements LockOpenner {

    private KeyHelper keyHelper;

    @Autowired
    @Qualifier("keyHelper")
    public void setKeyHelper(KeyHelper keyHelper) {
        this.keyHelper = keyHelper;
    }

    @Override
    public void openLock() {
        keyHelper.open();        
    }

    @Override
    public KeyHelper getMKeyHelper() {
        return keyHelper;
    }
}
