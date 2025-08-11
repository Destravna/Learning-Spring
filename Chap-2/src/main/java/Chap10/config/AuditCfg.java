package Chap10.config;

import java.security.SecureRandom;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class AuditCfg {
    @Bean
    public AuditorAware<String> auditorProvider() {
        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        return () -> {
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<4;i++){
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return Optional.of("user-" + sb.toString());
        };
    }

}
