package Chapter4.profilesDemo.kindergarten;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import Chapter4.profilesDemo.FoodProviderService;
import Chapter4.profilesDemo.highschool.FoodServiceImpl;

@Configuration
@Profile("highschool")
public class HighSchoolConfig {
    
    @Bean
    FoodProviderService foodProviderService(){
        return new FoodServiceImpl();
    }
}
