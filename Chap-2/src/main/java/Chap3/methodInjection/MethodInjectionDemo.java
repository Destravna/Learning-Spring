package Chap3.methodInjection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Configuration
@ComponentScan
public class MethodInjectionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext apx = new AnnotationConfigApplicationContext(MethodInjectionDemo.class);
        AbstractLockOpenner abstractLockOpenner = apx.getBean(AbstractLockOpenner.class);
        StandardLockOpenner standardLockOpenner = apx.getBean(StandardLockOpenner.class);
        displayInfo("abstractLockOpenner", abstractLockOpenner);
        displayInfo("standardLockOpenner", standardLockOpenner);

    }

    public static void displayInfo(String beanName, LockOpenner lockOpener) {
        KeyHelper keyHelperOne = lockOpener.getMKeyHelper();
        KeyHelper keyHelperTwo = lockOpener.getMKeyHelper();
        System.out.println("[" + beanName + "]: KeyHelper Instances the Same?  " + (keyHelperOne == 
       keyHelperTwo));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("lookupDemo");
        for (int x = 0; x < 100_000; x++) {
        KeyHelper keyHelper = lockOpener.getMKeyHelper();
        keyHelper.open();
        }
        stopWatch.stop();
        System.out.println("100000 gets took " + stopWatch.getTotalTimeMillis() + " ms");
        }
}
