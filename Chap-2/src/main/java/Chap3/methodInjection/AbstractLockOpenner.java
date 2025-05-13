package Chap3.methodInjection;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractLockOpenner implements LockOpenner {
    @Override
    public void openLock() {
        getMKeyHelper().open();
        
    }

    @Lookup
    @Override
    public abstract KeyHelper getMKeyHelper();
}
