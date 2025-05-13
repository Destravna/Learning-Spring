package Chapter4.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


class MessageEvent extends ApplicationEvent {
    private String msg;
    
    public MessageEvent(Object source, String msg){
        super(source);
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

}

@Component
class MessageEventListener implements ApplicationListener<MessageEvent> {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageEventListener.class);

    @Override
    public void onApplicationEvent(MessageEvent event) {
        MessageEvent msgEvt = event;
        logger.info("recieved {}", msgEvt.getMsg());

    }
}


@Component
class Publisher implements ApplicationContextAware {
    private ApplicationContext ctx;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public void publish(String message){
        ctx.publishEvent(new MessageEvent(this, message));
    }
}


@Configuration
@ComponentScan
public class Events {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(Events.class);
        Publisher pub = ctx.getBean(Publisher.class);
        pub.publish("I send an SOS to the world");
        pub.publish("We are going to be attacked by alients");
        ctx.close();
    }

}
