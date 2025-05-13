package Chap5.programmaticalyadvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.time.Duration.ofMillis;

public class Concert implements Peroformance {
    private static final Logger logger = LoggerFactory.getLogger(Concert.class);

    @Override
    public void execute() {
        logger.info("LALALALALA");
        try{
            Thread.sleep(ofMillis(2000).toMillis());
        }
        catch(InterruptedException e){

        }
    }
}
