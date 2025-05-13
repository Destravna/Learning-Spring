package Chap3.FieldInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public class Singer {

    @Autowired
    Inspiration inspiration;

    public void sing(){
        System.out.println(inspiration.getLyric());
    }
}
