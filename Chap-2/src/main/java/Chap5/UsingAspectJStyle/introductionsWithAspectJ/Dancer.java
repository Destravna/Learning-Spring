package Chap5.UsingAspectJStyle.introductionsWithAspectJ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dancer implements Performer {
    private static final Logger logger = LoggerFactory.getLogger(Dancer.class);
    
    @Override
    public void perform() {
        logger.info("Shake it to the left, shake it to the right");
    }
    
}
