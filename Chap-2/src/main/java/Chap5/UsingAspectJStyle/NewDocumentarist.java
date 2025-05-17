package Chap5.UsingAspectJStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("documentarist")
public class NewDocumentarist {
    protected GrammyGuitarist guitarist;

    public void execute(){
        guitarist.sing();
        guitarist.sing(new Guitar("Yamaha"));
        guitarist.talk();
        guitarist.rest();
    }

    @Autowired
    @Qualifier("johnMayer")
    public void setGuitarist(GrammyGuitarist guitarist){
        this.guitarist = guitarist;
    }


}
