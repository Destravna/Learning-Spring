package Chapter4.profilesDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chapter4.profilesDemo.kindergarten.HighSchoolConfig;
import Chapter4.profilesDemo.kindergarten.KindergartenConfig;

public class ProfilesFeatureDemo {
    private static Logger logger = LoggerFactory.getLogger(ProfilesFeatureDemo.class);
    public static void main(String[] args) {
        var profile = System.getProperty("spring.profiles.active");
        var ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles(profile);
        ctx.register(HighSchoolConfig.class, KindergartenConfig.class);
        ctx.refresh();
        var foodProviderService = ctx.getBean(FoodProviderService.class);
        var lunchSet = foodProviderService.provideLunchSet();
        lunchSet.forEach(food -> logger.info("{}", food.toString()));
    }

}
