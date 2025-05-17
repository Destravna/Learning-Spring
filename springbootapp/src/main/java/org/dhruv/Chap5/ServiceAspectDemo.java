package org.dhruv.Chap5;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.dhruv.Chap5.Controller.ProductController;
import org.dhruv.Chap5.aspects.TrackUsageAspect;
import org.dhruv.Chap5.service.BankService;
import org.dhruv.Chap5.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ServiceAspectDemo {

  
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ServiceAspectDemo.class, args);
        UserService userService = ctx.getBean(UserService.class);
        assertNotNull(userService);
        System.out.println(userService.getUserById(12389L));

        ProductController productController = ctx.getBean(ProductController.class);
        System.out.println(productController.getAllProducts());
        callMethods(ctx);
    //    securityAspectCheck(ctx);

    }


    public static void securityAspectCheck(ApplicationContext ctx){
        System.out.println("security check");
        var securityContext = ctx.getBean(SecurityContext.class);
        var bankService = ctx.getBean(BankService.class);
        securityContext.setUserRole("USER");
        System.out.println("role " + securityContext.getUserRole());
        assertNotNull(securityContext);
        assertNotNull(bankService);
        try{    
            bankService.viewBalance("10934084293");
            bankService.closeAccount("12328973");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void callMethods(ApplicationContext ctx) {
        try {
            UserService userService = ctx.getBean(UserService.class);
            ProductController productController = ctx.getBean(ProductController.class);
            int randomInt = ThreadLocalRandom.current().nextInt(1, 31);
            for (int i = 0; i < randomInt; i++) {
                boolean randomBool = ThreadLocalRandom.current().nextBoolean();
                if (randomBool)
                    productController.getAllProducts();
                else
                    userService.getUserById((long) i);
            }
            TrackUsageAspect tu = ctx.getBean(TrackUsageAspect.class);
            Map<String, Integer> mp = tu.getMethodCallCount();
            for (Map.Entry<String, Integer> entry : mp.entrySet()) {
                System.out.println("" + entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
