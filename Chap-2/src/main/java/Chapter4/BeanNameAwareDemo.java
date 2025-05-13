package Chapter4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class NamedSinger implements BeanNameAware {
    private static final Logger log = LoggerFactory.getLogger(NamedSinger.class);
    private String name;
    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    public void sing() {
        log.info("Singer {} sing", name);
    }
}

@Configuration
class AwareConfig {

    @Bean
    NamedSinger johnMayer(){
        return new NamedSinger();
    }
}




public class BeanNameAwareDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AwareConfig.class);
        ctx.refresh();
        NamedSinger singer = ctx.getBean(NamedSinger.class);
        singer.sing();
    }

}
