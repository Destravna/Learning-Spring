package Chapter4.MessageDigestFactoryBean;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FactoryBeanDemo {
    private static final Logger log = LoggerFactory.getLogger(FactoryBeanDemo.class);
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MessageDigestConfig.class);
        ctx.refresh();
        String msg = "Dhruv";
        MessageDigester digester1 = ctx.getBean(MessageDigester.class);
        digester1.digest(msg);

        // _____________________another way_________________________________
        MessageDigestFactoryBean factoryBean = (MessageDigestFactoryBean) ctx.getBean("&shaDigest");
        MessageDigest shaDigest = factoryBean.getObject();
        log.info("Explicit use digest bean {}", shaDigest.digest("dhruv".getBytes()));

    }
}
