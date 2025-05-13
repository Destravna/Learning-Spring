package Chap5.programmaticalyadvice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreatGuitarist implements Singer {
    private static final Logger logger = LoggerFactory.getLogger(GreatGuitarist.class);
    @Override
    public void sing() {
        logger.info("You've got my soul in your hand ");

    }
}
