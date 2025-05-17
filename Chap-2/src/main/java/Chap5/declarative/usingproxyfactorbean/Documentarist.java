package Chap5.declarative.usingproxyfactorbean;

import Chap5.programmaticalyadvice.common.GrammyGuitarist;

public class Documentarist {
    private GrammyGuitarist grammyGuitarist;

    public void execute(){
        grammyGuitarist.sing();
        grammyGuitarist.talk();
    }

    public void setGrammyGuitarist(GrammyGuitarist grammyGuitarist) {
        this.grammyGuitarist = grammyGuitarist;
    }

}
