package Chapter4.profilesDemo.kindergarten;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import Chapter4.profilesDemo.FoodProviderService;

@Configuration
@Profile("kindergarten")
public class KindergartenConfig {
    @Bean
    FoodProviderService foodProviderService(){
        return new FoodServiceImpl();
    }
}
