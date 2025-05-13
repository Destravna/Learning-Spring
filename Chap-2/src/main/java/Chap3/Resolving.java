package Chap3;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component("gopher")
class Guitar {
    public void sing() {
        System.out.println("uwa uwa weee wee wee");
    }
}

@DependsOn("gopher")
@Component("sanjaythegreatestsinger")
class Singer implements ApplicationContextAware{
    
    private ApplicationContext ctx;
    
    private Guitar guitar;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public void sing(){
        guitar = (Guitar) ctx.getBean("gopher");
        guitar.sing();
    }
}

public class Resolving{
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class, Guitar.class);
        ctx.refresh();
        Singer sanjaySinghSinger = (Singer) ctx.getBean("sanjaythegreatestsinger");
        sanjaySinghSinger.sing();
    }
}