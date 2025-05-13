package Chap5.programmaticalyadvice.flowpointcuts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;


class TestBean{
    private static final Logger logger = LoggerFactory.getLogger(TestBean.class);
    public void foo() {
        logger.info("FOO");    
    }
}

public class ControlFlowDemo {
    private static final Logger logger = LoggerFactory.getLogger(ControlFlowDemo.class);
    public static void main(String[] args) {
       ControlFlowDemo ex = new ControlFlowDemo();
       ex.run(); 
    }

    public void run(){
        TestBean target = new TestBean();
        Pointcut pc = new ControlFlowPointcut(ControlFlowDemo.class, "test");
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleBeforeAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);

        TestBean proxy = (TestBean) pf.getProxy();
        logger.info("normal invoke");
        proxy.foo();
        logger.info("trying under control flowdemo.test()");
        test(proxy);
    }

    public void test(TestBean bean){
        bean.foo();
    }
}
