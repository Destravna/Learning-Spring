package Chap5.programmaticalyadvice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodGuitarist implements Singer {
    private static final Logger logger = LoggerFactory.getLogger(Singer.class);
    
    @Override
    public void sing() {
        logger.info("Head on your heart, arms around me");
    }
}
