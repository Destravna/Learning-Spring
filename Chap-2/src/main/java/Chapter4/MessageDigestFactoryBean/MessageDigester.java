package Chapter4.MessageDigestFactoryBean;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDigester {
    private static final Logger log = LoggerFactory.getLogger(MessageDigester.class);
    private MessageDigest digest1;
    private MessageDigest digest2;

    public void setDigest1(MessageDigest digest1) {
        this.digest1 = digest1;
    }

    public void setDigest2(MessageDigest digest2) {
        this.digest2 = digest2;
    }

    public void digest(String msg){
        log.info("using digest 1");
        digest(msg, digest1);

        log.info("using digest 2");
        digest(msg, digest2);
    }


    private void digest(String msg, MessageDigest digest){
        log.info("using algorithm {}", digest.getAlgorithm());
        digest.reset();

        byte[] bytes = msg.getBytes();
        byte[] out = digest.digest(bytes);

        log.info("original message {}", bytes );
        log.info("digest {}", out);
        log.info("digest string {}", out.toString());

    }
}
