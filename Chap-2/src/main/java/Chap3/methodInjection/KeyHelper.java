package Chap3.methodInjection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// non singleton class, need a new object everytime

@Component
@Scope("prototype")
public class KeyHelper {
    public void open(){
        
    }
}
