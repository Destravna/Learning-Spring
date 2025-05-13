package Chapter4.MessageDigestFactoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MessageDigestConfig {

    @Bean
    public MessageDigestFactoryBean shaDigest(){
        MessageDigestFactoryBean shaDigest = new MessageDigestFactoryBean();
        shaDigest.setAlgoName("SHA512");
        return shaDigest;
    }

    @Bean 
    public MessageDigestFactoryBean md5Digest(){
        MessageDigestFactoryBean md5Digest = new MessageDigestFactoryBean();
        md5Digest.setAlgoName("MD5");
        return md5Digest;
    }

    @Bean
    public MessageDigester digester() throws Exception {
        MessageDigester digester = new MessageDigester();
        digester.setDigest1(shaDigest().getObject());
        digester.setDigest2(md5Digest().getObject());
        return digester;
    }
}
