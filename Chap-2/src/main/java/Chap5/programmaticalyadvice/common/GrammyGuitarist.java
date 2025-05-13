package Chap5.programmaticalyadvice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap5.programmaticalyadvice.AdviceRequired;


public class GrammyGuitarist implements Singer {
    private static final Logger logger = LoggerFactory.getLogger(GrammyGuitarist.class);

    class Guitar{
        public String play(){
            return "played guitar";
        }
    }
    
    @AdviceRequired
    @Override
    public void sing() {
        logger.info("Gravity is working against me\n And gravity wants me to bring me down");
    }

    public void sing(Guitar guitar){
        logger.info("play: " + guitar.play());
    }

    public void talk() {
        logger.info("talk");
    }

    public void rest() {
        logger.info("resting");
    }

    public void sing2(){
        logger.info("sing2");
    }
}
