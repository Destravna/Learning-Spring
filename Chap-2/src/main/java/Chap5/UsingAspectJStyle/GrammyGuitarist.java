package Chap5.UsingAspectJStyle;

import org.springframework.stereotype.Component;
import Chap5.programmaticalyadvice.common.Singer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("johnMayer")
public class GrammyGuitarist implements Singer {
    private static final Logger logger = LoggerFactory.getLogger(GrammyGuitarist.class);

    @Override
    public void sing() {
        logger.info("I wanna be your sl");
    }

    public void sing(Guitar guitar) {
        logger.info("play: " + guitar.play());
    }

    public void talk() {
        logger.info("talk");
    }

    public void rest(){
        logger.info("resting");
    }

  
    
}
